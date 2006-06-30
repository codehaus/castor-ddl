/*
 * Copyright 2006 Le Duc Bao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.castor.ddl.mysql;

import java.util.Enumeration;
import java.util.Iterator;

import org.castor.ddl.AbstractGenerator;
import org.castor.ddl.BaseConfiguration;
import org.castor.ddl.Configuration;
import org.castor.ddl.GeneratorException;
import org.castor.ddl.TypeMapper;
import org.castor.ddl.TypeNotFoundException;
import org.castor.ddl.schemaobject.ForeignKey;
import org.castor.ddl.typeinfo.TypeInfo;
import org.exolab.castor.mapping.xml.ClassMapping;
import org.exolab.castor.mapping.xml.FieldMapping;
import org.exolab.castor.mapping.xml.MappingRoot;

/**
 * generator for MySQL 
 * Created on Jun 4, 2006 - 10:29:02 AM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public class MySQLGenerator extends AbstractGenerator {

    /**
     * 
     * Constructor for MySQLGenerator
     * @param conf
     */
    public MySQLGenerator(String globConf, String mySQLConf) throws GeneratorException{
        super();
        Configuration conf = new Configuration(globConf);
        conf.addProperties(mySQLConf);
        this.setConf(conf); 
        
        TypeMapper typeMapper = new MySQLTypeMapper(conf);
        this.setTypeMapper(typeMapper);
    }
    
	/**
     * @see org.castor.ddl.AbstractGenerator#generateTableDDL()
	 */
    public String generateTableDDL() throws GeneratorException {
        MappingRoot root = _mapping.getRoot();
        String ddl = "";
        Enumeration ec = root.enumerateClassMapping();
        
        while (ec.hasMoreElements()) {
            ClassMapping cm = (ClassMapping) ec.nextElement();
            ddl += createTable(cm);
        }        
        return ddl;
}

    /**
     * generate table
     * @param cm
     * @throws GeneratorException 
     */
    private String createTable(ClassMapping cm) throws GeneratorException {
        boolean isUseFieldIdentity = _mappingHelper.isUseFieldIdentity(cm);
        StringBuffer sb = new StringBuffer();
        
        if(_conf.getBoolValue(Configuration.GENERATE_DDL_FOR_DROP_KEY, false)) {
            sb.append(dropTable(cm.getMapTo().getTable()));
            sb.append(LINE_SEPARATOR);
        }
        sb.append(createTable(cm.getMapTo().getTable()));
        sb.append(LINE_SEPARATOR);
    
        Enumeration ef = cm.getClassChoice().enumerateFieldMapping();
        
        
        boolean isGenerateDDL = false;
        while (ef.hasMoreElements()) {
            FieldMapping fm = (FieldMapping) ef.nextElement();
            
            //if not used field identity, this variable indicates either this field is identity
            
            //modify direct the field _mapping for specify this's a identity
            if(!isUseFieldIdentity ) {
                fm.setIdentity(_mappingHelper.isIdentity(cm, fm));
            }
            
            //if <sql> tag is not defined, there is no _mapping to DB
            if(fm.getSql() == null) {
                isGenerateDDL = false;
                continue;
            }
            
            //checking for many-key, many-table definition
            if(fm.getSql().getManyKeyCount() > 0) {
                isGenerateDDL = false;
                if(fm.getSql().getManyTable() != null) {
                    //checking for many-table case just
                    // 1. generate DDL for creating relationship, with MySQL, not need
                    addManyManyForeignKey(fm, cm.getMapTo().getTable());
                    
                    // 2. generate resolving table for many-many relationship                    
                    addResolveField(fm, cm.getKeyGenerator());
                }else {
                    //checking for many-key case (not need generate DDL), 
                    // 1. generate DDL for creating relationship, with MySQL
//                    addOneManyForeignKey(fm, cm.getMapTo().getTable());
                }                
            }else {
                if(isGenerateDDL) {
                    sb.append("," + LINE_SEPARATOR);
                }
                isGenerateDDL = true;
                sb.append(generateField(fm,cm.getKeyGenerator(), cm.getMapTo().getTable()));
            }
        }
        
        //generate primary keys
        sb.append(createPrimaryKeys(_mappingHelper.getClassMappingIdentity(cm)));
        sb.append(LINE_SEPARATOR + ")");
        sb.append(createEngineStatement());
        sb.append(";" + LINE_SEPARATOR + LINE_SEPARATOR);

        return sb.toString();
    }

    /**
     * @return
     */
    private String createEngineStatement() {
        String engine = _conf.getStringValue(MySQLConfigurationKey.STORAGE_ENGINE, null);
        if(engine == null || "".equals(engine))
            return "";
        return " ENGINE = " + engine;
    }


    /**
     * generate DDL for field
     * @param fm
     * @param keyGen 
     * @throws TypeNotFoundException 
     */
    private String generateField(FieldMapping fm, String keyGen, String tableName) throws GeneratorException {
        StringBuffer sb = new StringBuffer();

        String[] sqlnames = fm.getSql().getName();;
        
        String sqltype = fm.getSql().getType();//field type
        TypeInfo typeInfo = null;//type info
        ClassMapping cm = null;//reference table
        String []referenceIds = null;//reference ids
        boolean isUseReferenceType = false;
        
        //get type info
        if(sqltype != null)
            typeInfo = _typeMapper.getType(sqltype);
        
        //if typeInfo is null, maybe this table has a reference to another one.
        if(typeInfo == null) {
            isUseReferenceType = true;
            cm = _mappingHelper.getClassMappingByName(fm.getType());
            // if cm is null, the reference class is not found
            if(cm == null) {
                throw new TypeNotFoundException("can not resolve type " + sqltype);
            }
            
            referenceIds = _mappingHelper.resolveTypeReferenceForIds(fm.getType());
            
            //if number of reference table's Id differs to number of field elements
            if(referenceIds.length != sqlnames.length)
                throw new TypeNotFoundException("number of reference table's Id differs to number of field elements " + fm.getName());                

            //create foreign key
            addOneOneForeignKey(fm, tableName);
        }
        
        for (int i = 0; i < sqlnames.length; i++) {
            sb.append("  ");
            //group_moderator mediumint(8) DEFAULT '0' NOT NULL,
            if(isUseReferenceType) {
                //each sqlname is correpondent to a identity of the reference table
                //so, it should get the original type of the reference field
                typeInfo = _typeMapper.getType(referenceIds[i]);
                if(typeInfo == null)
                    throw new TypeNotFoundException("can not find reference type " + referenceIds[i] + " of class " + cm.getName());
            }
                
            String s = sqlnames[i] + " " + getTypeInfoDDL(typeInfo);
            
            if(fm.getIdentity()) {
                if(keyGen != null) {
                    s += " " + createKeyGen(keyGen);
                }
                s += " NOT NULL";
            }

            sb.append(s);
            if(i != sqlnames.length - 1)
                sb.append("," + LINE_SEPARATOR);
        }
        return sb.toString();
    }

    /**
     * @param keyGen
     * @return
     */
    private String createKeyGen(String keyGen) {
        if(keyGen != null && keyGen.toUpperCase().equals("IDENTITY"))
            return "AUTO_INCREMENT";
        return "";
    }

    /**
     * @see org.castor.ddl.AbstractGenerator#generateResolvingTable()
     */
	public String generateResolvingTable() throws GeneratorException {
        String ddl = "";
        //generate _resolve Table
        Iterator i= _resolveTable.keySet().iterator();
        while (i.hasNext()) {
            ClassMapping cm = (ClassMapping)_resolveTable.get(i.next());
            ddl += createTable(cm);
        }
        return ddl;
	}

    /**
     * generate create table statement
     * @param tableName
     * @return
     */
    private String createTable(String tableName) {
        return "CREATE TABLE " + tableName + " ( ";
    }
    
    /**
     * generate drop table statement
     * @param tableName
     * @return
     */
    private String dropTable(String tableName) {
        return "DROP TABLE IF EXISTS " + tableName + ";";
    }

    /**
     * generate primary keys statement
     * @param keys
     * @return
     */

    private String createPrimaryKeys(String[] keys) {
        String stat = "," + LINE_SEPARATOR + "  PRIMARY KEY ( ";
        int n = keys.length;
        if(n <= 0) 
            return "";
        
        for(int i=0; i < n - 1; i++) {
            stat += keys[i] + ", ";
        }
        stat += keys[n-1] + " )";
        return stat;
    }
    
    /**
     * 
     * @param inf
     * @return a string represents the DDL format of type
     * @throws GeneratorException
     */
    private String getTypeInfoDDL(TypeInfo inf) throws GeneratorException{
        return inf.getDDLString();        
    }

    /* (non-Javadoc)
     * @see org.castor.ddl.AbstractGenerator#generateConstraint()
     */
    public String generateForeignKey() throws GeneratorException {
        String ddl = "";
        //generate constraint
        Iterator i= _foreignKey.keySet().iterator();
        while (i.hasNext()) {
            ForeignKey fk = (ForeignKey)_foreignKey.get(i.next());
            ddl += createForeignKey(fk);
        }
        return ddl;
//        return super.generateConstraint();
    }

    /**
     * <pre>ALTER TABLE `prod_group` 
     * ADD CONSTRAINT `FK_prod_group_1` 
     * FOREIGN KEY `FK_prod_group_1` (`id`, `name`)
     * REFERENCES `category` (`id`, `name`)
     * ON DELETE SET NULL
     * ON UPDATE CASCADE;
     * </pre>
     * @param fk
     * @return
     */
    private String createForeignKey(ForeignKey fk) {
        String r = LINE_SEPARATOR + "ALTER TABLE ";
        r += fk.getTableName() + " ";
        r += "ADD CONSTRAINT ";
        r += fk.getConstraintName() + " ";
        
        r += "FOREIGN KEY ";
        r += fk.getFkName() + " (";        
        int size = fk.getFkkeyList().length;
        for(int i = 0; i < size; i++) {
            r += fk.getFkkeyList()[i] + " ";
            if(i < size - 1) r += ", ";
        }
        r += ") " + LINE_SEPARATOR + "  REFERENCES ";
        r += fk.getReferenceTableName() + " (";
        size = fk.getReferenceKeyList().length;
        for(int i = 0; i < size; i++) {
            r += fk.getReferenceKeyList()[i] + " ";
            if(i < size - 1) r += ", ";
        }
        r += ") ";

        //on delete
        String opt = _conf.getStringValue(MySQLConfigurationKey.FOREIGN_KEY_ON_DELETE, null);
        if(opt != null && !"".equals(opt) )
            r += "ON DELETE " + opt;
        
        opt = _conf.getStringValue(MySQLConfigurationKey.FOREIGN_KEY_ON_UPDATE, null);
        if(opt != null && !"".equals(opt) )
            r += "ON UPDATE " + opt;
        
        r += ";";
        return r;
    }

    /* (non-Javadoc)
     * @see org.castor.ddl.AbstractGenerator#generateSchemaDDL()
     */
    public String generateSchemaDDL() throws GeneratorException {
        String schema = _conf.getStringValue(BaseConfiguration.SCHEMA_NAME_KEY, "");
        if(schema == null || "".equals(schema))
            return "";
        
        return "USE " + schema;
    }
}

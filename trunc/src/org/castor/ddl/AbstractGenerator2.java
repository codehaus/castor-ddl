/*
 * Copyright 2006 Le Duc Bao
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.castor.ddl;


import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.castor.ddl.schemaobject.Field;
import org.castor.ddl.schemaobject.ForeignKey;
import org.castor.ddl.schemaobject.KeyGenerator;
import org.castor.ddl.schemaobject.KeyRepository;
import org.castor.ddl.schemaobject.Schema;
import org.castor.ddl.schemaobject.Table;
import org.castor.ddl.typeinfo.TypeInfo;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.mapping.xml.ClassChoice;
import org.exolab.castor.mapping.xml.ClassMapping;
import org.exolab.castor.mapping.xml.FieldMapping;
import org.exolab.castor.mapping.xml.KeyGeneratorDef;
import org.exolab.castor.mapping.xml.MapTo;
import org.exolab.castor.mapping.xml.MappingRoot;
import org.exolab.castor.mapping.xml.Sql;

/**
 * this class is the base class for various DDL generator for specific DB
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 * 
 */
public abstract class AbstractGenerator2 implements Generator {

    /** hold all configurations (key, value) */
    private Configuration _conf;

    /** hold the writer for output */
    private java.io.PrintStream _printer;

    /** handle the _mapping document*/
    private Mapping _mapping;

    /** handle the typemapper*/
    private TypeMapper _typeMapper;

    /** handle the MappingHelper */
    private MappingHelper _mappingHelper;

    /** handle all resolving tables*/
    private Map _resolveTable;
    
    /** schema */    
    private Schema _schema;
    
    /**
     * Constructor for AbstractGenerator
     */
    public AbstractGenerator2() {
        super();
        _mappingHelper = new MappingHelper();
        _resolveTable = new HashMap();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ddl.mapper.DDLGenerator#generateDDL()
     */
    public void generateDDL(String mappingFile) throws GeneratorException, IOException, MappingException {
        _mapping = new Mapping();
        _mapping.loadMapping(mappingFile);
        
        _mappingHelper.setMapping(_mapping);
        _mappingHelper.setTypeMapper(_typeMapper);
        
        generateDDL();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ddl.mapper.DDLGenerator#generateDDL()
     */
    public void generateDDL(Mapping mappingDoc) throws GeneratorException {
        _mapping = mappingDoc;
        _mappingHelper.setMapping(mappingDoc);
        _mappingHelper.setTypeMapper(_typeMapper);
        
        generateDDL();
    }

    /**
     * generate ddl 
     *
     */
    private void generateDDL() throws GeneratorException {
        createSchema();
        write(generateHeader());
        write(generateSchema());
        write(generateDrop());
        write(generateCreate());
        write(generatePrimaryKey());
        write(generateForeignKey());
        write(generateKeyGenerator());
        write(generateIndex());
        write(generateTrigger());
    }
    
    /**
     * @return
     */
    private String generatePrimaryKey() {
        Vector tables = _schema.getTables();
        StringBuffer buff = new StringBuffer();
        
        for(Iterator i = tables.iterator(); i.hasNext(); ) {
            Table table = (Table)i.next();
            buff.append(Configuration.LINE_SEPARATOR);
            buff.append(createPrimaryKeyDDL(table));
            buff.append(Configuration.LINE_SEPARATOR);
        }
        return buff.toString();
    }



    /**
     * @return
     */
    private String generateKeyGenerator() {
        Map keys = _schema.getKeyRepository().getKeyGenerator();
        StringBuffer buff = new StringBuffer();
        
        for(Iterator i = keys.entrySet().iterator(); i.hasNext(); ) {
            KeyGenerator keygen = (KeyGenerator)keys.get(i.next());
            buff.append(Configuration.LINE_SEPARATOR);
            buff.append(createKeyGeneratorDDL(keygen));
            buff.append(Configuration.LINE_SEPARATOR);
        }
        return buff.toString();
    }


    /**
     * @return
     */
    protected String generateTrigger() {
        Vector tables = _schema.getTables();
        StringBuffer buff = new StringBuffer();
        
        for(Iterator i = tables.iterator(); i.hasNext(); ) {
            Table table = (Table)i.next();
            buff.append(Configuration.LINE_SEPARATOR);
            buff.append(createTriggerDDL(table));
            buff.append(Configuration.LINE_SEPARATOR);
        }
        return buff.toString();
    }

    /**
     * @return
     */
    protected String generateIndex() {
        Vector tables = _schema.getTables();
        StringBuffer buff = new StringBuffer();
        
        for(Iterator i = tables.iterator(); i.hasNext(); ) {
            Table table = (Table)i.next();
            buff.append(Configuration.LINE_SEPARATOR);
            buff.append(createIndexDDL(table));
            buff.append(Configuration.LINE_SEPARATOR);
        }
        return buff.toString();
    }

    /**
     * @return
     */
    protected String generateDrop() {
        Vector tables = _schema.getTables();
        StringBuffer buff = new StringBuffer();
        
        for(Iterator i = tables.iterator(); i.hasNext(); ) {
            Table table = (Table)i.next();
            buff.append(createDropDDL(table));            
            buff.append(Configuration.SQL_STAT_DELIMETER);
            buff.append(Configuration.LINE_SEPARATOR);
        }
        return buff.toString();
    }

    /**
     * @return
     */
    private String generateHeader() {
        String s = "";
        s += "# " + new java.util.Date() + "\n";
        s += "# Castor DDL Generator from _mapping\n";
        s += "# \n";
        return s;
    }

    /**
     * generate header, pre-create statement, setting up for DDL script
     * generateHeader
     * @return TODO
     * @throws GeneratorException
     */
    public String generateSchema() throws GeneratorException{
        String schema = _conf.getStringValue(BaseConfiguration.SCHEMA_NAME, "");
        if(schema == null || "".equals(schema))
            return "";
        
        return "USE " + schema + ";";
    }

    /**
     * generate create statement for DDL script
     * generateBody
     * @return TODO
     * @throws GeneratorException
     */
    public String generateCreate() throws GeneratorException{
        Vector tables = _schema.getTables();
        StringBuffer buff = new StringBuffer();
        
        for(Iterator i = tables.iterator(); i.hasNext(); ) {
            Table table = (Table)i.next();
            buff.append(Configuration.LINE_SEPARATOR);
            buff.append(createCreateDDL(table));
            buff.append(Configuration.LINE_SEPARATOR);
        }
        return buff.toString();
    }
    /**
     * generate constrains statement, cleaning up for DDL scritp,
     * for example: 
     * <pre>ALTER TABLE `prod_group` ADD CONSTRAINT `FK_prod_group_1` FOREIGN KEY `FK_prod_group_1` (`id`, `name`)
     * REFERENCES `category` (`id`, `name`)
     * ON DELETE SET NULL
     * ON UPDATE CASCADE;</pre> 
     * @return String
     * @throws GeneratorException
     */
    public String generateForeignKey() throws GeneratorException{
        Vector tables = _schema.getTables();
        StringBuffer buff = new StringBuffer();
        
        for(Iterator i = tables.iterator(); i.hasNext(); ) {
            Table table = (Table)i.next();
            buff.append(createForeignKeyDDL(table));
        }
        return buff.toString();
    }

    /**
     * @return Returns the conf.
     */
    public Configuration getConf() {
        return _conf;
    }

    /**
     * @param conf
     * The conf to set.
     */
    public void setConf(Configuration conf) {
        this._conf = conf;
    }

    /**
     * @param _mapping
     * The _mapping to set.
     */
    public void setMapping(Mapping mapping) {
        this._mapping = mapping;
        _mappingHelper.setMapping(_mapping);
    }

    /**
     * set Writer
     * @param printer
     */
    public final void setPrinter(PrintStream printer) {
        this._printer = printer;
    }

    /**
     * 
     * @return Returns the _mapping.
     */
    public final Mapping getMapping() {
        return _mapping;
    }

    /**
     * 
     * @return Returns the writer.
     */
    public final java.io.PrintStream getPrinter() {
        return _printer;
    }

    /**
     * Set the typeMapper by _typeMapper.
     * @param typeMapper 
     */
    public final void setTypeMapper(TypeMapper typeMapper) {
        _typeMapper = typeMapper;
        _mappingHelper.setTypeMapper(_typeMapper);
    }

    private void createSchema() throws GeneratorException {
//        _schema
        MappingRoot root = _mapping.getRoot();
        _schema = new Schema();

        //generate key generator definition
        Enumeration ekg = root.enumerateKeyGeneratorDef();
        while (ekg.hasMoreElements()) {
            KeyGeneratorDef kg = (KeyGeneratorDef) ekg.nextElement();
            KeyGenerator keygen = KeyRepository.createKey(kg);
            _schema.putKeyGenerator(keygen.getHashKey(), keygen);
        }        

        Enumeration ec = root.enumerateClassMapping();
        while (ec.hasMoreElements()) {
            ClassMapping cm = (ClassMapping) ec.nextElement();
            Table table = createTable(cm);
            _schema.addTable(table);
        }        
        //generate _resolve Table
        Iterator i= _resolveTable.keySet().iterator();
        while (i.hasNext()) {
            ClassMapping cm = (ClassMapping)_resolveTable.get(i.next());
            Table table = createTable(cm);
            _schema.addTable(table);
        }
    }

    /**
     * @param cm
     * @return
     * @throws GeneratorException 
     */
    private Table createTable(ClassMapping cm) throws GeneratorException {
        Table table = new Table(cm.getMapTo().getTable());
        boolean isUseFieldIdentity = _mappingHelper.isUseFieldIdentity(cm);
        Enumeration ef = cm.getClassChoice().enumerateFieldMapping();

        while (ef.hasMoreElements()) {
            FieldMapping fm = (FieldMapping) ef.nextElement();
            
            //if <sql> tag is not defined, there is no _mapping to DB, skip it
            if(fm.getSql() == null) {
                continue;
            }
                        
            //create a field
            boolean isFieldIdentity = fm.getIdentity();
            //if use class' identity, overwrite this one
            if(!isUseFieldIdentity ) {
                isFieldIdentity = _mappingHelper.isIdentity(cm, fm);
            }
            
            //checking for many-key, many-table definition
            if(fm.getSql().getManyKeyCount() > 0) {
                if(fm.getSql().getManyTable() != null) {
                    //checking for many-table case just
                    // 1. generate DDL for creating relationship, with MySQL, not need
//                    addManyManyForeignKey(table, fm);
                    
                    // 2. generate resolving table for many-many relationship                    
                    addResolveField(fm, cm.getKeyGenerator());
                }else {
                    //checking for many-key case (not need generate DDL), 
                    // 1. generate DDL for creating relationship, with MySQL
//                    addOneManyForeignKey(fm, cm.getMapTo().getTable());
                }
             //normal field
            }else {
                String[] sqlnames = fm.getSql().getName();;
                String sqltype = fm.getSql().getType();//field type

                TypeInfo typeInfo = null;//type info
                ClassMapping cmRef = null;//reference table
                String []refIdTypes = null;//reference ids
                boolean isUseReferenceType = false;
                
                //type resolving: get type info
                if(sqltype != null)
                    typeInfo = _typeMapper.getType(sqltype);
                
                //if typeInfo is null, this table has a reference to another one.
                if(typeInfo == null) {
                    isUseReferenceType = true;
                    cmRef = _mappingHelper.getClassMappingByName(fm.getType());
                    // if cmRef is null, the reference class is not found
                    if(cmRef == null) {
                        throw new TypeNotFoundException("can not resolve type " + fm.getType());
                    }
                    
                    refIdTypes = _mappingHelper.resolveTypeReferenceForIds(fm.getType());
                    
                    //if number of reference table's Id differs to number of field elements
                    if(refIdTypes.length != sqlnames.length)
                        throw new TypeNotFoundException("number of reference table's Id differs to number of field elements " + fm.getName());                

                    //create foreign key
                    addOneOneForeignKey(table, fm, cm.getMapTo().getTable());
                }

                //create fields
                for (int i = 0; i < sqlnames.length; i++) {
                    Field field = new Field();
                    table.addField(field);

                    //group_moderator mediumint(8) DEFAULT '0' NOT NULL,
                    if(isUseReferenceType) {
                        //each sqlname is correpondent to a identity of the reference table
                        //so, it should get the original type of the reference field
                        typeInfo = _typeMapper.getType(refIdTypes[i]);
                        if(typeInfo == null)
                            throw new TypeNotFoundException("can not find reference type " + refIdTypes[i] + " of class " + cm.getName());
                    }

                    field.setName(sqlnames[i]);
                    field.setType(typeInfo);
                    field.setIdentity(isFieldIdentity);
                    if(isFieldIdentity)
                        field.setKeyGenerator(cm.getKeyGenerator());
                }
            }
        }
        return table;
    }

    /**
     * @param fm
     * @param table
     * @throws GeneratorException 
     */
    protected void addManyManyForeignKey(Table table, FieldMapping fm) throws GeneratorException {
        ForeignKey fk = new ForeignKey();
        String s;
        
        String tableName = fm.getSql().getManyTable();
        fk.setTableName(tableName);
        s = table + "_" + fm.getName();
        fk.setConstraintName(s);
        fk.setFkName(s);
        fk.setFkkeyList(fm.getSql().getName());
        
        ClassMapping cm = _mappingHelper.getClassMappingByName(fm.getType());
        fk.setReferenceTableName(cm.getMapTo().getTable());
        fk.setReferenceKeyList(_mappingHelper.getClassMappingIdentity(cm));
        
        fk.setRelationshipType(ForeignKey.MANY_MANY);
        
//      _foreignKey.put(fk.getConstraintName(), fk);
        table.addForeignKey(fk);
    }

    /**
     * @param fm
     * @param table
     * @throws GeneratorException 
     */
    protected void addOneManyForeignKey(Table table, FieldMapping fm, String tableName) throws GeneratorException {
        ForeignKey fk = new ForeignKey();
        String s;
        
        ClassMapping cm = _mappingHelper.getClassMappingByName(fm.getType());
        fk.setTableName(cm.getMapTo().getTable());
        s = cm.getMapTo().getTable() + "_" + fm.getName();
        fk.setConstraintName(s);
        fk.setFkName(s);
        fk.setFkkeyList(_mappingHelper.getClassMappingIdentity(cm));
        
        fk.setReferenceTableName(tableName);
        fk.setReferenceKeyList(fm.getSql().getManyKey());
        
        fk.setRelationshipType(ForeignKey.ONE_MANY);
//      _foreignKey.put(fk.getConstraintName(), fk);
        table.addForeignKey(fk);
    }

    /**
     * add new foreign key into a creation list
     * @param fm
     * @param tableName
     * @throws GeneratorException
     */
    protected void addOneOneForeignKey(Table table, FieldMapping fm, String tableName) throws GeneratorException {
        ForeignKey fk = new ForeignKey();
        String s;
        
        fk.setTableName(tableName);
        s = tableName + "_" + fm.getName();
        fk.setConstraintName(s);
        fk.setFkName(s);
        fk.setFkkeyList(fm.getSql().getName());
        
        ClassMapping cm = _mappingHelper.getClassMappingByName(fm.getType());
        fk.setReferenceTableName(cm.getMapTo().getTable());
        fk.setReferenceKeyList(_mappingHelper.getClassMappingIdentity(cm));
        
        fk.setRelationshipType(ForeignKey.ONE_ONE);
//      _foreignKey.put(fk.getConstraintName(), fk);
        table.addForeignKey(fk);
    }

    /**
     * @param fm
     * @param keyGen 
     */
    protected void addResolveField(FieldMapping fm, String keyGen) {
        ClassMapping resolveCm = null;

        //get table, if not existe, create one
        if(_resolveTable.containsKey(fm.getSql().getManyTable())) {
            resolveCm =  (ClassMapping)_resolveTable.get(fm.getSql().getManyTable());
        }else {
            resolveCm = new ClassMapping();                        
            resolveCm.setName(fm.getSql().getManyTable());                        
            resolveCm.setKeyGenerator(keyGen);
            
            MapTo mapto = new MapTo();
            mapto.setTable(fm.getSql().getManyTable());
            resolveCm.setMapTo(mapto);                        
            _resolveTable.put(fm.getSql().getManyTable(), resolveCm);
        } 
                            
        FieldMapping resolveFm= new FieldMapping();
        resolveFm.setIdentity(true);
        resolveFm.setName(fm.getName());
        resolveFm.setType(fm.getType());
        
        ClassChoice cc = resolveCm.getClassChoice();
        if(cc == null) {
            cc = new ClassChoice();
            resolveCm.setClassChoice(cc);
        }
        cc.addFieldMapping(resolveFm);
                     
        Sql sql = new Sql();
        sql.setName(fm.getSql().getName());
        resolveFm.setSql(sql);
    }    
 
    /**
     * @param table
     * @return
     * @throws GeneratorException 
     */
    protected String createCreateDDL(Table table) throws GeneratorException {
        StringBuffer buff = new StringBuffer();
        
        //pre create table ddl
        buff.append(preCreateTable(table));
        
        Vector fields = table.getFields();
        for(Iterator i = fields.iterator(); i.hasNext();) {
            Field field = (Field)i.next();
            buff.append(Configuration.LINE_SEPARATOR);
            buff.append(Configuration.LINE_INDENT);
            buff.append(createFieldDDL(field));
            if(i.hasNext()) 
                buff.append(Configuration.SQL_FIELD_DELIMETER);
        }
        
        buff.append(Configuration.LINE_SEPARATOR);
        buff.append(postCreateTable(table));
        buff.append(Configuration.SQL_STAT_DELIMETER);
        return buff.toString();
    }

    /**
     * @param table
     * @return
     */
    protected String postCreateTable(Table table) {
        return ")";
    }

    /**
     * @param field
     * @return
     * @throws GeneratorException 
     */
    protected String createFieldDDL(Field field) throws GeneratorException {
        StringBuffer buff = new StringBuffer();
        buff.append(field.getName());
        buff.append(" ");
        buff.append(field.getType().getDDLString());
        if(field.isIdentity())
            buff.append(" NOT NULL ");
        
        return buff.toString();
    }

    /**
     * @param table
     * @return
     */
    private String preCreateTable(Table table) {
        return "CREATE TABLE " + table.getName() + " (";
    }

    /**
     * @param table
     * @return
     */
    protected String createDropDDL(Table table) {
        return "DROP " + table.getName() + " IF EXISTS";
    }

    
    /**
     * @param table
     * @return
     */
    protected String createForeignKeyDDL(Table table) {
       StringBuffer buff = new StringBuffer();

       for(Iterator i = table.getForeignKey().iterator(); i.hasNext();) {
           ForeignKey fk = (ForeignKey)i.next();
           buff.append(Configuration.LINE_SEPARATOR);
           buff.append(createForeignKeyDDL(fk));
           buff.append(Configuration.SQL_STAT_DELIMETER);
       }                  
       return buff.toString();
    }
        
    /** alter table category_prod 
     *      add constraint category_prod_categories 
     *      foreign key category_prod_categories \(category_id \) 
     *      references category \(id \) ;
     * @param fk
     * @return
     */
    protected String createForeignKeyDDL(ForeignKey fk) {
        StringBuffer buff = new StringBuffer();
        
        buff.append("ALTER TABLE ");
        buff.append(fk.getTableName());
        
        //constraint
        buff.append(Configuration.LINE_SEPARATOR);
        buff.append(Configuration.LINE_INDENT);        
        buff.append("ADD CONSTRAINT ");
        buff.append(fk.getConstraintName());
        
        //foreign key
        buff.append(Configuration.LINE_SEPARATOR);
        buff.append(Configuration.LINE_INDENT);        
        buff.append("FOREIGN KEY  ");
        buff.append(fk.getFkName());
        buff.append(makeListofParams(fk.getFkkeyList()));
        
        //references
        buff.append(Configuration.LINE_SEPARATOR);
        buff.append(Configuration.LINE_INDENT);        
        buff.append("REFERENCES  ");
        buff.append(fk.getReferenceTableName());
        buff.append(makeListofParams(fk.getReferenceKeyList()));
        
        return buff.toString();
    }

    /**
     * @param fkkeyList
     * @return
     */
    protected String makeListofParams(String[] list) {
        StringBuffer buff = new StringBuffer();
        
        boolean isFirstField = true;
        buff.append(" ( ");
        for(int i = 0; i < list.length; i++) {
            if(!isFirstField) {
                buff.append(Configuration.SQL_FIELD_DELIMETER);
                buff.append(" ");                    
            }
            isFirstField = false;
            buff.append(list[i]);
        }
        buff.append(" )");
        
        return buff.toString();
    }

    /**
     * @param table
     * @return
     */
    protected String createIndexDDL(Table table) {
        return "";
    }

    /**
     * @param table
     * @return
     */
    protected String createTriggerDDL(Table table) {
        return "";
    }
    /**
     * @param keygen
     * @return
     */
    protected String createKeyGeneratorDDL(KeyGenerator keygen) {
        return "";
    }

    /**
     * @param table
     * @return
     */
    protected String createPrimaryKeyDDL(Table table) {
        StringBuffer buff = new StringBuffer("ALTER TABLE ");
        buff.append(table.getName());
        buff.append(Configuration.LINE_SEPARATOR);
        buff.append(Configuration.LINE_INDENT);
        buff.append("ADD PRIMARY KEY ( ");
        
        Vector fields = table.getFields();
        boolean isFirstField = true;
        for(Iterator i = fields.iterator(); i.hasNext();) {
            Field field = (Field)i.next();
            if(field.isIdentity()) {
                if(!isFirstField) {
                    buff.append(Configuration.SQL_FIELD_DELIMETER);
                    buff.append(" ");                    
                }
                isFirstField = false;
                buff.append(field.getName());
            }
        }
        buff.append(" )");
        buff.append(Configuration.SQL_STAT_DELIMETER);
        
        return buff.toString();
    }

    /**
     * 
     * @return Returns the mappingHelper.
     */
    public final MappingHelper getMappingHelper() {
        return _mappingHelper;
    }

    /**
     * Set the mappingHelper by _mappingHelper.
     * @param mappingHelper 
     */
    public final void setMappingHelper(MappingHelper mappingHelper) {
        _mappingHelper = mappingHelper;
    }

    /**
     * 
     * @return Returns the schema.
     */
    public final Schema getSchema() {
        return _schema;
    }

    /**
     * Set the schema by _schema.
     * @param schema 
     */
    public final void setSchema(Schema schema) {
        _schema = schema;
    }

    /**
     * 
     * @return Returns the typeMapper.
     */
    public final TypeMapper getTypeMapper() {
        return _typeMapper;
    }
    
    /**
     * 
     * @param s
     * @return
     */
    protected String format(String s) {
        if(Configuration.DDL_FORMAT_CASE == Configuration.DDL_FORMAT_LOWERCASE) {
            return s.toLowerCase();
        }
        if(Configuration.DDL_FORMAT_CASE == Configuration.DDL_FORMAT_UPPERCASE) {
            return s.toUpperCase();
        }
        return s;
    }
    
    protected void write(String s) {
        _printer.println(format(s));
    }
}

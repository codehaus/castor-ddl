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

import org.castor.ddl.AbstractGenerator;
import org.castor.ddl.BaseConfiguration;
import org.castor.ddl.Configuration;
import org.castor.ddl.GeneratorException;
import org.castor.ddl.TypeMapper;
import org.castor.ddl.mysql.schemaobject.MySQLSchemaFactory;
import org.castor.ddl.schemaobject.Field;
import org.castor.ddl.schemaobject.ForeignKey;
import org.castor.ddl.schemaobject.KeyGenerator;
import org.castor.ddl.schemaobject.Table;

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
        setConf(conf); 
        
        TypeMapper typeMapper = new MySQLTypeMapper(conf);
        setTypeMapper(typeMapper);
        
        setSchemaFactory(new MySQLSchemaFactory());
    }
    
    /**
     * @return
     */
    private String createEngineStatement() {
        String engine = getConf().getStringValue(MySQLConfigurationKey.STORAGE_ENGINE, null);
        if(engine == null || "".equals(engine))
            return "";
        return " ENGINE = " + engine;
    }
    /**
     * @param table
     * @return
     */
    protected String postCreateTable(Table table) {
        return ")" + createEngineStatement();
    }
   
    /**
     * @param field
     * @return
     * @throws GeneratorException 
     */
    protected String createFieldDDL(Field field) throws GeneratorException {
        StringBuffer buff = new StringBuffer();
        buff.append(field.getName()).append(" ");
        buff.append(field.getType().toDDL(field));

        KeyGenerator keyGen = field.getKeyGenerator();
        if(keyGen != null) {
            if(KeyGenerator.IDENTITY_KEY.equalsIgnoreCase(keyGen.getName()))
                buff.append(" AUTO_INCREMENT");
        }
        
        if(field.isIdentity())
            buff.append(" NOT NULL");
        
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
        StringBuffer buff = new StringBuffer(Configuration.LINE_SEPARATOR);

        buff.append("ALTER TABLE ").append(fk.getTableName());
        
        //constraint
        buff.append(Configuration.LINE_SEPARATOR).append(Configuration.LINE_INDENT);
        buff.append("ADD CONSTRAINT ").append(fk.getConstraintName());
        
        //foreign key
        buff.append(Configuration.LINE_SEPARATOR).append(Configuration.LINE_INDENT);
        buff.append("FOREIGN KEY ").append(fk.getFkName());
        buff.append(makeListofParams(fk.getFkkeyList()));
        
        //references
        buff.append(Configuration.LINE_SEPARATOR).append(Configuration.LINE_INDENT);
        buff.append("REFERENCES ").append(fk.getReferenceTableName());
        buff.append(makeListofParams(fk.getReferenceKeyList()));        

        //on delete
        String opt = getConf().getStringValue(MySQLConfigurationKey.FOREIGN_KEY_ON_DELETE, null);
        if(opt != null && !"".equals(opt) ) {
            buff.append(Configuration.LINE_SEPARATOR).append(Configuration.LINE_INDENT);
            buff.append("ON DELETE ").append(opt);
        }
        
        opt = getConf().getStringValue(MySQLConfigurationKey.FOREIGN_KEY_ON_UPDATE, null);
        if(opt != null && !"".equals(opt) ) {
            buff.append(Configuration.LINE_SEPARATOR).append(Configuration.LINE_INDENT);
            buff.append("ON UPDATE ").append(opt);
        }

        buff.append(Configuration.SQL_STAT_DELIMETER);
        return buff.toString();
    }    

    /**
     * generateSchema
     */
    public String generateSchema() throws GeneratorException{
        if(!getConf().getBoolValue(Configuration.GENERATE_DDL_FOR_SCHEMA_KEY, true)) {
            return "";
        }

        String schema = getConf().getStringValue(BaseConfiguration.SCHEMA_NAME_KEY, "");
        if(schema == null || "".equals(schema))
            return "";
        
        return "USE " + schema + ";" ;
    }
}

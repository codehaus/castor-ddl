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

package org.castor.ddl.schemaobject;

import java.util.Iterator;
import java.util.Vector;

import org.castor.ddl.GeneratorException;
import org.castor.ddl.KeyGenNotSupportException;

/**
 * 
 * Created on Jun 23, 2006 - 5:43:29 PM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

/**
 * 
 * Created on Jun 23, 2006 - 6:22:33 PM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public class Table extends AbstractSchemaObject {
    /**table name*/
    private String _name = null;
    
    /** handle key generator */
    private KeyGenerator _keyGenerator;
    
    /**list of field*/
    private Vector _fields;
    
    /**indexes*/
    private Vector _indexes;
    
    /** foriegn key*/
    private Vector _foreignKey;
    
    /** schema key*/
    private Schema _schema;    
    
    /**
     * Constructor for Table
     */
    protected Table() {
        super();
        _fields = new Vector();
        _indexes = new Vector();
        _foreignKey = new Vector();
        _keyGenerator = null;
    }

    /**
     * 
     * @return Returns the fields.
     */
    public Vector getFields() {
        return _fields;
    }

    /**
     * Set the fields by _fields.
     * @param fields fields
     */
    public void setFields(final Vector fields) {
        _fields = fields;
    }

    /**
     * 
     * @param field field
     */
    public void addField(final Field field) {
        _fields.add(field);
    }
    
    /**
     * 
     * @param index index
     * @return field
     */
    public Field getField(final int index) {
        return (Field) _fields.get(index);
        
    }
    
    /**
     * 
     * @return field count
     */
    public int getFieldCount() {
        return _fields.size();
    }

    /**
     * 
     * @return Returns the indexes.
     */
    public Vector getIndexes() {
        return _indexes;
    }

    /**
     * Set the indexes by _indexes.
     * @param indexes index 
     */
    public void setIndexes(final Vector indexes) {
        _indexes = indexes;
    }

    /**
     * 
     * @param index index
     */
    public void setIndex(final Index index) {        
        _indexes.add(index);
    }
    
    /**
     * 
     * @param index index
     * @return Index
     */
    public Index getIndex(final int index) {
        return (Index) _indexes.get(index);        
    }
    
    /**
     * 
     * @return Returns the name.
     */
    public String getName() {
        return _name;
    }

    /**
     * Set the name by _name.
     * @param name name
     */
    public void setName(final String name) {
        _name = name;
    }

    /**
     * 
     * @return Returns the foreignKey.
     */
    public Vector getForeignKey() {
        return _foreignKey;
    }

    /**
     * Set the foreignKey by _foreignKey.
     * @param foreignKey foreign key
     */
    public void setForeignKey(final Vector foreignKey) {
        _foreignKey = foreignKey;
    }

    /**
     * 
     * @param fk foreign key
     */
    public void addForeignKey(final ForeignKey fk) {
       _foreignKey.add(fk);       
    }
    
    /**
     * 
     * @return foreign key count
     */
    public int getForeignKeyCount() {
        return _foreignKey.size();
    }

    /**
     * 
     * @return Returns the keyGenerator.
     */
    public final KeyGenerator getKeyGenerator() {
        return _keyGenerator;
    }

    /**
     * Set the keyGenerator by _keyGenerator.
     * @param keyGenerator key generator
     */
    public final void setKeyGenerator(final KeyGenerator keyGenerator) {
        _keyGenerator = keyGenerator;
    }

    /**
     * 
     * @return pre-create table
     */
    protected String preCreateTable() {
        return "CREATE TABLE " + _name + " (";
    }

    /**
     * @return DDL script for creating table
     */
    protected String postCreateTable() {
        return ")";
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
     * @param schema schema
     */
    public final void setSchema(final Schema schema) {
        _schema = schema;
    }

    /**
     * @return create creation ddl
     * @throws GeneratorException generator error
     */
    public String toCreateDDL() throws GeneratorException {
        StringBuffer buff = new StringBuffer(getConf().getLineSeparator());
        buff.append(getConf().getLineSeparator());
        
        //pre create table ddl
        buff.append(preCreateTable());
        
        for (Iterator i = _fields.iterator(); i.hasNext();) {
            Field field = (Field) i.next();
            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
            buff.append(field.toDDL());
            if (i.hasNext()) {
                buff.append(getConf().getSqlFieldDelimeter());
            }
        }
        
        buff.append(getConf().getLineSeparator());
        buff.append(postCreateTable());
        buff.append(getConf().getSqlStatDelimeter());
        
        return buff.toString();
    }

    /**
     * @return DDL script for dropping table 
     */
    public String toDropDDL() {
        StringBuffer buff = new StringBuffer(getConf().getLineSeparator());
        buff.append(getConf().getLineSeparator());

        buff.append("DROP TABLE ").append(_name);
        buff.append(getConf().getSqlStatDelimeter());

        return buff.toString();
    }

    /**
     * @return DDL script for creating primary key
     */
    public String toPrimaryKeyDDL() {
        boolean isHasPK = false;
        StringBuffer buff = new StringBuffer(getConf().getLineSeparator());
        buff.append(getConf().getLineSeparator());

        buff.append("ALTER TABLE ").append(_name);
        buff.append(getConf().getLineSeparator()).append(
                getConf().getLineIndent());
        buff.append("ADD PRIMARY KEY (");

        boolean isFirstField = true;
        for (Iterator i = _fields.iterator(); i.hasNext();) {
            Field field = (Field) i.next();
            if (field.isIdentity()) {
                isHasPK = true;
                if (!isFirstField) {
                    buff.append(getConf().getSqlFieldDelimeter());
                    buff.append(" ");
                }
                isFirstField = false;
                buff.append(field.getName());
            }
        }
        buff.append(")").append(getConf().getSqlStatDelimeter());

        //have no primary key
        if (!isHasPK) {
            return "";
        }
        return buff.toString();
    }

    /**
     * @return DDL script for creating primary key
     * @throws KeyGenNotSupportException exception
     */
    public String toKeyGeneratorDDL() throws KeyGenNotSupportException {
        if (_keyGenerator == null) {
            return "";
        }
        _keyGenerator.setTable(this);
        return _keyGenerator.toDDL();
    }
    
}

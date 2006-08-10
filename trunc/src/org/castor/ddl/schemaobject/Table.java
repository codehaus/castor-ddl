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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.castor.ddl.GeneratorException;
import org.castor.ddl.KeyGenNotSupportException;

/**
 * Table contains fields, foreignkeys, indexes and table's options
 * 
 * <br/>Created on Jun 23, 2006 - 6:22:33 PM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public class Table extends AbstractSchemaObject {

    /**logging*/
    private static final Log LOG = LogFactory.getLog(Table.class);

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
    public final Vector getFields() {
        return _fields;
    }

    /**
     * Set the fields by _fields.
     * @param fields fields
     */
    public final void setFields(final Vector fields) {
        _fields = fields;
    }

    /**
     * 
     * @param field field
     * @throws GeneratorException cannot merge field if it already exists in table
     */
    public final void addField(final Field field) throws GeneratorException {
        if (_fields.contains(field)) {
           Field f = (Field) _fields.get(_fields.indexOf(field));
           f.merge(field);
        } else {
            _fields.add(field);
        }
    }
    
    /**
     * 
     * @param index index
     * @return field field
     */
    public final Field getField(final int index) {
        return (Field) _fields.get(index);
        
    }
    
    /**
     * 
     * @param field field
     * @return field field which has the same name of field
     */
    public final Field getField(final Field field) {
        if (_fields.contains(field)) {
            return (Field) _fields.get(_fields.indexOf(field));
        }
        return null;
        
    }
    
    /**
     * 
     * @return field count
     */
    public final int getFieldCount() {
        return _fields.size();
    }

    /**
     * 
     * @return Returns the indexes.
     */
    public final Vector getIndexes() {
        return _indexes;
    }

    /**
     * Set the indexes by _indexes.
     * @param indexes index 
     */
    public final void setIndexes(final Vector indexes) {
        _indexes = indexes;
    }

//    /**
//     * 
//     * @param index index
//     */
//    public void setIndex(final Index index) {        
//        _indexes.put(index. index);
//    }
    
//    /**
//     * 
//     * @param index index
//     * @return Index
//     */
//    public Index getIndex(final int index) {
//        return (Index) _indexes.get(index);        
//    }
    
    /**
     * 
     * @return Returns the name.
     */
    public final String getName() {
        return _name;
    }

    /**
     * Set the name by _name.
     * @param name name
     */
    public final void setName(final String name) {
        _name = name;
    }

    /**
     * 
     * @return Returns the foreignKey.
     */
    public final Vector getForeignKey() {
        return _foreignKey;
    }

    /**
     * Set the foreignKey by _foreignKey.
     * @param foreignKey foreign key
     */
    public final void setForeignKey(final Vector foreignKey) {
        _foreignKey = foreignKey;
    }

    /**
     * 
     * @param fk foreign key
     * @throws GeneratorException if cannot be merged.
     */
    public final void addForeignKey(final ForeignKey fk) throws GeneratorException {
        if (_foreignKey.contains(fk)) {
            ForeignKey f = (ForeignKey) _foreignKey.get(_foreignKey.indexOf(fk));
            f.merge(fk);
        } else {
            _foreignKey.add(fk);       
        }
    }
    
    /**
     * 
     * @return foreign key count
     */
    public final int getForeignKeyCount() {
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

    /** create CREADDL
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
     * create unique index tc1x_pks_add_pk on tc1x_pks_address( id );
     * @return DDL script for creating index for primary key
     */
    public String toIndexDDL() {
//        boolean isHasPK = false;
//        StringBuffer buff = new StringBuffer(getConf().getLineSeparator());
//        buff.append(getConf().getLineSeparator());
//
//        buff.append("CREATE UNIQUE INDEX ").append(_name).append("_idx_pk");
//        buff.append(getConf().getLineSeparator()).append(
//                getConf().getLineIndent());
//        buff.append("ON ").append(_name).append("(");
//
//        boolean isFirstField = true;
//        for (Iterator i = _fields.iterator(); i.hasNext();) {
//            Field field = (Field) i.next();
//            if (field.isIdentity()) {
//                isHasPK = true;
//                if (!isFirstField) {
//                    buff.append(getConf().getSqlFieldDelimeter()).append(" ");
//                }
//                isFirstField = false;
//                buff.append(field.getName());
//            }
//        }
//        buff.append(")").append(getConf().getSqlStatDelimeter());
//
//        //have no primary key
//        if (!isHasPK) {
//            buff = new StringBuffer(getConf().getLineSeparator());
//            buff.append(getConf().getLineSeparator());
//        }
//        
//        for (Iterator i = getForeignKey().iterator(); i.hasNext();) {
//            ForeignKey fk = (ForeignKey) i.next();
//            buff.append(fk.toIndexDDL());
//        }
//        
//        return buff.toString();
        return "";
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
    
    /**
     * 
     * @param table table to be merged
     * @throws GeneratorException throw an exception if tables cannot be merged
     */
    public final void merge(final Table table) throws GeneratorException {
        if (table == null || table.getFieldCount() != getFieldCount()) {
            LOG.error("Merge table: table '" + _name + "' which is mapped to multiple"
                    + " table has difference the number of field");
            throw new GeneratorException("Merge table: table '" + _name + "' which is"
                    + "  mapped to multiple table has difference the number of field"
                    + table.getFieldCount() + ", " + getFieldCount());
        }
        
        if (_name != null && !_name.equalsIgnoreCase(table.getName())) {
            LOG.error("Merge table: table '" + _name + "' has difference names");
            throw new GeneratorException("Merge table: table '" + _name 
                    + "' has difference names");
        }
        
        
        for (int i = 0; i < getFieldCount(); i++) {
            Field field1 = getField(i);
            Field field2 = null;
            for (int j = 0; j < table.getFieldCount(); j++) {
                Field f = table.getField(j); 
                if (f != null && f.getName().equalsIgnoreCase(field1.getName())) {
                    field2 = f;
                    break;
                } 
            }
            field1.merge(field2);
        }
        
        for (int i = 0; i < getForeignKeyCount(); i++) {
            ForeignKey fk1 = getForeignKey(i);
            ForeignKey fk2 = table.getForeignKey(i);
            fk1.merge(fk2);
        }
        
        if (_keyGenerator != null) {
            _keyGenerator.merge(table.getKeyGenerator());
        }
        
    }

    /**
     * @param index index
     * @return foreign key
     */
    public final ForeignKey getForeignKey(final int index) {
        return (ForeignKey) _foreignKey.get(index);
    }

    /** (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     * {@inheritDoc}
     */
    public final boolean equals(final Object obj) {
        if (obj != null && obj instanceof Table) {
            Table t = (Table) obj;
            if (_name != null && _name.equalsIgnoreCase(t.getName())) {
                return true;
            }
        }
        return false;
    }

    /** (non-Javadoc)
     * @see java.lang.Object#hashCode()
     * {@inheritDoc}
     */
    public final int hashCode() {
        return super.hashCode();
    }
    
}

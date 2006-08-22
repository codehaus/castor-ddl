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

package org.castor.ddl.schemaobject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.castor.ddl.GeneratorException;
import org.castor.ddl.typeinfo.TypeInfo;

/**
 * Field handles informations for creating field's sql. For example, 
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class Field extends AbstractSchemaObject {
    /**LOGGING*/
    private static final Log LOG = LogFactory.getLog(Field.class);
        
    /** field name */
    private String _name;

    /** type infor */
    private TypeInfo _type;

    /** is identity */
    private boolean _isIdentity;

    /** handle key generator */
    private KeyGenerator _keyGenerator;
    
    /**is required*/
    private boolean _isRequired;
    
    /** handle to table in which contains this field */
    private Table _table;
    /**
     * Constructor for Field
     */
    protected Field() {
        super();
        _name = null;
        _type = null;
        _keyGenerator = null;
        _isRequired = false;
    }
    


    /**
     * 
     * @return Returns the isIdentity.
     */
    public final boolean isIdentity() {
        return _isIdentity;
    }

    /**
     * Set the isIdentity by _isIdentity.
     * 
     * @param isIdentity is identity
     */
    public final void setIdentity(final boolean isIdentity) {
        _isIdentity = isIdentity;
    }

    /**
     * 
     * @return Returns the name.
     */
    public final String getName() {
        return _name;
    }

    /**
     * Set the name by _name.
     * 
     * @param name field name
     */
    public final void setName(final String name) {
        _name = name;
    }

    /**
     * 
     * @return Returns the type.
     */
    public final TypeInfo getType() {
        return _type;
    }

    /**
     * Set the type by _type.
     * 
     * @param type type info
     */
    public final void setType(final TypeInfo type) {
        _type = type;
    }

    /**
     * 
     * @return Returns the keyGenerator.
     */
    public final KeyGenerator getKeyGenerator() {
        return _keyGenerator;
    }

    /**
     * 
     * @return Returns the table.
     */
    public final Table getTable() {
        return _table;
    }



    /**
     * Set the table by _table.
     * @param table table
     */
    public final void setTable(final Table table) {
        _table = table;
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
     * @return length
     */
    public final Integer getLength() { return null; }

    /**
     * 
     * @return precision
     */
    public final Integer getPrecision() { return null; }

    /**
     * 
     * @return decimals
     */
    public final Integer getDecimals() { return null; }

    /**
     * 
     * @return Returns the isRequired.
     */
    public final boolean isRequired() {
        return _isRequired;
    }



    /**
     * Set the isRequired by _isRequired.
     * @param isRequired is required
     */
    public final void setRequired(final boolean isRequired) {
        _isRequired = isRequired;
    }



    /**
     * Create DDL for field
     * @return ddl string
     * @throws GeneratorException exception
     */
    public String toDDL() throws GeneratorException {
        StringBuffer buff = new StringBuffer();
        buff.append(_name).append(" ");
        buff.append(_type.toDDL(this));
        if (_isIdentity || _isRequired) {
            buff.append(" NOT NULL");
        }

        if (_keyGenerator != null && isIdentity() 
                && KeyGenerator.IDENTITY_KEY.equalsIgnoreCase(_keyGenerator.getName())) {
            LOG.warn("Not support IDENTITY key-gen for this database");
//            throw new KeyGenNotSupportException("Not support IDENTITY key-gen for " 
//                    + "this database");
        }

        
        return buff.toString();
    }



    /**
     * 
     * @param field field to be merged
     * @throws GeneratorException throw an exception if fields cannot be merged
     */
    public final void merge(final Field field) throws GeneratorException {
        if (field == null || _name == null 
                || !_name.equalsIgnoreCase(field.getName())) {
            LOG.error("Merge table '" + _table.getName()  
                    + "': Field '" + _name + "' has difference name or not found");
           throw new GeneratorException("Merge table '" + _table.getName()  
                    + "': Field '" + _name + "' has difference name or not found"); 
        }
        
        if (_isIdentity != field._isIdentity) {
            LOG.warn("Merge table: Field 'identity' attributes are different");
        }
        
        if (_isRequired != field._isRequired) {
            LOG.warn("Merge table: Field 'required' attributes are different");
        }
        
        _type.merge(field._type);
    }



    /** (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     * {@inheritDoc}
     */
    public final boolean equals(final Object obj) {
        if (obj != null && obj instanceof Field) {
           Field f = (Field) obj;
           if (_name != null && _name.equalsIgnoreCase(f.getName())) {
               return true;
           }
        }
        return false;
    }



    /** (non-Javadoc)
     * @see java.lang.Object#hashCode()
     * {@inheritDoc}
     */
    public int hashCode() {
        return super.hashCode();
    }
    
}

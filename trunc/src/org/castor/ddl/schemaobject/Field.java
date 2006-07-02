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

import org.castor.ddl.typeinfo.TypeInfo;

/**
 * 
 * Created on Jun 23, 2006 - 5:43:48 PM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class Field implements SchemaObject {
    /** field name */
    private String _name;

    /** type infor */
    private TypeInfo _type;

    /** is identity */
    private boolean _isIdentity;

//    /** is autoincrement */
//    private boolean _isAutoIncrement;

    /** handle key generator keyword */
    private String _keyGenerator;
    
    /**
     * Constructor for Field
     */
    public Field() {
        super();
    }

    /**
     * Constructor for Field
     * @param name
     */
    public Field(String name) {
        super();
        // TODO Auto-generated constructor stub
        _name = name;
    }

    /**
     * Constructor for Field
     * 
     * @param name
     *            field'name
     * @param type
     *            type infor
     * @param isIdentity
     *            is identity
     */
    public Field(final String name, final TypeInfo type,
            final boolean isIdentity) {
        super();
        _name = name;
        _type = type;
        _isIdentity = isIdentity;
//        _isAutoIncrement = isAutoIncrement;
    }
//
//    /**
//     * 
//     * @return Returns the isAutoIncrement.
//     */
//    public boolean isAutoIncrement() {
//        return _isAutoIncrement;
//    }
//
//    /**
//     * Set the isAutoIncrement by _isAutoIncrement.
//     * 
//     * @param isAutoIncrement
//     */
//    public void setAutoIncrement(boolean isAutoIncrement) {
//        _isAutoIncrement = isAutoIncrement;
//    }
//
    /**
     * 
     * @return Returns the isIdentity.
     */
    public boolean isIdentity() {
        return _isIdentity;
    }

    /**
     * Set the isIdentity by _isIdentity.
     * 
     * @param isIdentity
     */
    public void setIdentity(boolean isIdentity) {
        _isIdentity = isIdentity;
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
     * 
     * @param name
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * 
     * @return Returns the type.
     */
    public TypeInfo getType() {
        return _type;
    }

    /**
     * Set the type by _type.
     * 
     * @param type
     */
    public void setType(TypeInfo type) {
        _type = type;
    }

    /**
     * 
     * @return Returns the keyGenerator.
     */
    public String getKeyGenerator() {
        return _keyGenerator;
    }

    /**
     * Set the keyGenerator by _keyGenerator.
     * @param keyGenerator 
     */
    public void setKeyGenerator(String keyGenerator) {
        _keyGenerator = keyGenerator;
    }
    
    public Integer getLength() { return null; }

    public Integer getPrecision() { return null; }

    public Integer getDecimals() { return null; }
}

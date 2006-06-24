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

package org.castor.ddl.typeinfo;

import org.castor.ddl.GeneratorException;

/**
 * 
 * Created on Jun 23, 2006 - 11:09:17 PM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class LengthDecimalType implements TypeInfo {

    /** SQL type*/
    private final String _sqlName;

    /** SQL default length. Undefined value is -1 */
    private final int _defaultLength;

    /** if the type requires specific length. */
    private final boolean _isRequireLength;

    /** SQL default decimal. Undefined value is -1*/
    private final int _defaultDecimal;

    /** if the type requires specific decimal. */
    private final boolean _isRequireDecimal;

    /**
     * Construct a new TypeInfo instance with given SQL type name, SQL type name, 
     * length, requireLength, decimal, requireDecimal.
     * @param name
     * @param length
     * @param requireLength
     * @param decimal
     * @param requireDecimal
     */
    public LengthDecimalType(String name, int length, boolean requireLength, int decimal, boolean requireDecimal) {
        _sqlName = name;
        _defaultLength = length;
        _isRequireLength = requireLength;
        _defaultDecimal = decimal;
        _isRequireDecimal = requireDecimal;
    }
    /**
     * @return Returns the _defaultDecimal.
     */
    public final int getDefaultDecimal() {
        return _defaultDecimal;
    }

    /**
     * @return Returns the _defaultLength.
     */
    public final int getDefaultLength() {
        return _defaultLength;
    }

    /**
     * @return Returns the _isRequireDecimal.
     */
    public final boolean isRequireDecimal() {
        return _isRequireDecimal;
    }

    /**
     * @return Returns the _isRequireLength.
     */
    public final boolean isRequireLength() {
        return _isRequireLength;
    }

    /**
     * @return Returns the _sqlName.
     */
    public final String getSqlName() {
        return _sqlName;
    }
    /* (non-Javadoc)
     * @see org.castor.ddl.typeinfo.TypeInfo#getDDLString()
     */
    public String getDDLString() throws GeneratorException{
        //group_moderator mediumint(8) DEFAULT '0' NOT NULL,
        if(isRequireLength() && getDefaultLength() == TypeInfo.UNDEFINED_LENGTH)
            throw new GeneratorException("type '" + getSqlName() + "' requires length but it is not defined" );

        if(isRequireDecimal() && getDefaultDecimal() == TypeInfo.UNDEFINED_LENGTH)
            throw new GeneratorException("type '" + getSqlName() + "' requires decimal but it is not defined" );

        boolean isUsedBracket = false;
        StringBuffer buf = new StringBuffer();
        
        buf.append(getSqlName());        
        if(getDefaultLength() != TypeInfo.UNDEFINED_LENGTH) {
            buf.append("(");
            buf.append(getDefaultLength());
            isUsedBracket = true;
        }
        if(isUsedBracket && getDefaultDecimal() != TypeInfo.UNDEFINED_DECIMAL) {
            buf.append(", ");
            buf.append(getDefaultDecimal());
            buf.append(")");
            isUsedBracket = false;
        }
        if(isUsedBracket)
            buf.append(")");
        
        return buf.toString();
    }
}

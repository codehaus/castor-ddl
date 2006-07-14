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

import org.castor.ddl.Configuration;
import org.castor.ddl.schemaobject.Field;

/**
 * TypeInfo for types having one optional length parameter, and sufixe
 * Fox example, CHAR (n) FOR BIT DATA
 * Created on Jul 8, 2006 - 12:22:45 AM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public final class PostfixOptionalLengthType extends OptionalLengthType {
    /**post fix of type*/
    private String _postfix;
    
    /**
     * Constructor for PostfixOptionalLengthType
     * @param jdbcType jdbc type
     * @param sqlType sql type
     * @param postfix postfix
     * @param conf conf
     */
    public PostfixOptionalLengthType(final String jdbcType, final String sqlType, 
            final String postfix, final Configuration conf) {
        super(jdbcType, sqlType, conf);
        _postfix = postfix;
    }

    /**
     * @see org.castor.ddl.typeinfo.OptionalLengthType#toDDL
     * (org.castor.ddl.schemaobject.Field)
     * {@inheritDoc}
     */
    public String toDDL(final Field field) {
        Integer length = field.getLength();
        if (length == null) { length = getDefaultLength(); }
        
        StringBuffer sb = new StringBuffer();
        sb.append(getSqlType());        
        if (length != null) {
            sb.append(" (").append(length).append(")");
        }
        if (_postfix != null) {
            sb.append(" ").append(_postfix);
        }
        return sb.toString();
    }

}

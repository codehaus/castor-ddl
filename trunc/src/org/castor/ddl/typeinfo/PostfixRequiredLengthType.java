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
import org.castor.ddl.GeneratorException;
import org.castor.ddl.schemaobject.Field;

/**
 * 
 * Created on Jul 8, 2006 - 12:26:38 AM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public final class PostfixRequiredLengthType extends RequiredLengthType {
    /**post fix of type*/
    private String _postfix;

    /**
     * Constructor for PostfixRequiredLengthType
     * @param jdbcType jdbc type
     * @param sqlType sql type
     * @param postfix  post fix
     * @param conf conf
     */
    public PostfixRequiredLengthType(final String jdbcType, final String sqlType, 
            final String postfix, final Configuration conf) {
        super(jdbcType, sqlType, conf);
        _postfix = postfix;
    }

    /**
     * @see org.castor.ddl.typeinfo.RequiredLengthType#toDDL
     * (org.castor.ddl.schemaobject.Field)
     * {@inheritDoc}
     */
    public String toDDL(final Field field) throws GeneratorException {
        Integer length = field.getLength();
        if (length == null) { length = getDefaultLength(); }
        if (length == null) {
            throw new GeneratorException(
                    "Reguired length attribute missing for field '" + field.getName()
                    + "' of type '" + getJdbcType() + "'");
        }
        
        StringBuffer sb = new StringBuffer();
        sb.append(getSqlType());        
        sb.append(" (").append(length).append(")");
        if (_postfix != null) {
            sb.append(" ").append(_postfix);
        }
        
        return sb.toString();
    }
    

    
}

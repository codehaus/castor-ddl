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
package org.castor.ddl.typeinfo;

import org.castor.ddl.schemaobject.Field;

/**
 * Final TypeInfo for types having no parameters.
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 * @version $Revision: 5951 $ $Date: 2006-04-25 16:09:10 -0600 (Tue, 25 Apr 2006) $
 */
public final class NoParamType extends AbstractType {
    /**
     * Construct a new TypeInfo instance with given Configuration, JDBC type and SQL type.
     * 
     * @param jdbcType The JDBC type.
     * @param sqlType The SQL type.
     */
    public NoParamType(final String jdbcType, final String sqlType) {
        super(jdbcType, sqlType);
    }

    /**
     * @see org.castor.ddl.typeinfo.TypeInfo#toDDL(org.castor.ddl.schemaobject.Field)
     * {@inheritDoc}
     */
    public String toDDL(final Field field) {
        return getSqlType();
    }
}

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
import org.castor.ddl.schemaobject.Field;

/**
 * NotSupportedType is used for type in which DB does not support. 
 * Created on Jul 8, 2006 - 4:35:21 PM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class NotSupportedType extends AbstractType {

    /**
     * Constructor for NotSupportedType
     * @param jdbcType jdbc type
     */
    public NotSupportedType(final String jdbcType) {
        super(jdbcType, null);
    }


    /**
     * @see org.castor.ddl.typeinfo.TypeInfo#toDDL(org.castor.ddl.schemaobject.Field)
     * {@inheritDoc}
     */
    public String toDDL(final Field field) throws GeneratorException {
        throw new GeneratorException("Do not support type '" + getJdbcType() 
                + "' for this database engine");
    }

}

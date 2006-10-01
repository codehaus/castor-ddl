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

package org.castor.ddl.engine.postgresql;

import org.castor.ddl.GeneratorException;
import org.castor.ddl.keygenerator.IdentityKey;
import org.castor.ddl.schemaobject.Field;

/**
 * Postgre Field
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public final class PostgresqlField extends Field {
    /**
     * Constructor for PostgresqlField
     */
    protected PostgresqlField() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public String toDDL() throws GeneratorException {
        StringBuffer buff = new StringBuffer();
        buff.append(getName()).append(" ");

        if (isIdentity() && (getKeyGenerator() instanceof IdentityKey)) {
            if ("integer".equalsIgnoreCase(getType().getSqlType())) {
                buff.append("SERIAL");
            } else {
                buff.append("BIGSERIAL");
            }
        } else {
            buff.append(getType().toDDL(this));
        }

        if (isIdentity() || isRequired()) {
            buff.append(" NOT NULL");
        }
        
        return buff.toString();
    }
}

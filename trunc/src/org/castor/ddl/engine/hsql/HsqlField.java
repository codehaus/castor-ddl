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

package org.castor.ddl.engine.hsql;

import org.castor.ddl.GeneratorException;
import org.castor.ddl.keygenerator.IdentityKey;
import org.castor.ddl.schemaobject.Field;

/**
 * Hsql Field
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public final class HsqlField extends Field {
    /**
     * Constructor for HsqlField
     */
    public HsqlField() {
        super();
    }

    /**
     * @see org.castor.ddl.schemaobject.Field#toDDL()
     * {@inheritDoc}
     */
    public String toDDL() throws GeneratorException {
        StringBuffer buff = new StringBuffer();
        buff.append(getName()).append(" ");
        buff.append(getType().toDDL(this));
        
        if (isIdentity() || isRequired()) {
            buff.append(" NOT NULL");
        }

        if (isIdentity() && (getKeyGenerator() instanceof IdentityKey)) {
            buff.append(" IDENTITY");
        }
        
        return buff.toString();
    }
}

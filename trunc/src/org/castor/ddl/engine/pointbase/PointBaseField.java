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

package org.castor.ddl.engine.pointbase;

import org.castor.ddl.GeneratorException;
import org.castor.ddl.keygenerator.IdentityKeyGenerator;
import org.castor.ddl.schemaobject.Field;

/**
 * PointBaseField
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public final class PointBaseField extends Field {
    /**
     * Constructor for PointBaseField
     */
    public PointBaseField() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public String toCreateDDL() throws GeneratorException {
        StringBuffer buff = new StringBuffer();
        buff.append(getName()).append(" ");
        buff.append(getType().toDDL(this));
        
        if (isIdentity() || isRequired()) {
            buff.append(" NOT NULL");
        }

        if (isIdentity() && (getKeyGenerator() instanceof IdentityKeyGenerator)) {
            buff.append(" IDENTITY(1,1)");
        }
        
        return buff.toString();
    }
}

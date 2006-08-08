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

package org.castor.ddl.mysql.schemaobject;

import org.castor.ddl.GeneratorException;
import org.castor.ddl.schemaobject.Field;
import org.castor.ddl.schemaobject.KeyGenerator;

/**
 * MySql Field
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class MysqlField extends Field {

    /**
     * Constructor for MysqlField
     */
    protected MysqlField() {
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

        KeyGenerator keyGen = getKeyGenerator();
        if (keyGen != null && isIdentity()) {
            if (KeyGenerator.IDENTITY_KEY.equalsIgnoreCase(keyGen.getName())) {
                buff.append(" AUTO_INCREMENT");
            }
        }
        
        if (isIdentity() || isRequired()) {
            buff.append(" NOT NULL");
        }
        
        return buff.toString();
    }

}

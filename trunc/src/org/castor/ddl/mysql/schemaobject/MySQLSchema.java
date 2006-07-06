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

package org.castor.ddl.mysql.schemaobject;

import org.castor.ddl.BaseConfiguration;
import org.castor.ddl.schemaobject.Schema;

/**
 * 
 * Created on Jul 5, 2006 - 2:21:46 PM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public final class MySQLSchema extends Schema {

    /**
     * Constructor for MySQLSchema
     */
    protected MySQLSchema() {
        super();
    }

    /**
     * @see org.castor.ddl.schemaobject.Schema#toDDL() 
     * {@inheritDoc}
     */
    public String toDDL() {
        if (!getConf().getBoolValue(
                BaseConfiguration.GENERATE_DDL_FOR_SCHEMA_KEY, true)) {
            return "";
        }

        String schema = getConf().getStringValue(
                BaseConfiguration.SCHEMA_NAME_KEY, "");
        if (schema == null || "".equals(schema)) {
            return "";
        }

        return "USE " + schema + ";";
    }

}

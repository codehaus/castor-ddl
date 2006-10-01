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

package org.castor.ddl.engine.mysql;

import org.castor.ddl.BaseConfiguration;
import org.castor.ddl.schemaobject.Schema;

/**
 * My SQL Schema
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public final class MysqlSchema extends Schema {

    /**
     * Constructor for MysqlSchema
     */
    protected MysqlSchema() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public String toDDL() {
        if (!getConfiguration().getBoolValue(
                BaseConfiguration.GENERATE_DDL_FOR_SCHEMA_KEY, true)) {
            return "";
        }

        String schema = getConfiguration().getStringValue(
                BaseConfiguration.SCHEMA_NAME_KEY, "");
        if (schema == null || "".equals(schema)) {
            return "";
        }

        return "USE " + schema + ";";
    }

}

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

import org.castor.ddl.mysql.MysqlConfigurationKey;
import org.castor.ddl.schemaobject.Table;

/**
 * 
 * <br/>Created on Jul 3, 2006 - 5:32:53 PM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class MysqlTable extends Table {

    /**
     * Constructor for MysqlTable
     */
    protected MysqlTable() {
        super();
    }

    /**
     * 
     * @return engine creation statement
     */
    private String createEngineStatement() {
        String engine = getConf().getStringValue(
                MysqlConfigurationKey.STORAGE_ENGINE, null);
        if (engine == null || "".equals(engine)) {
            return "";
        }
        return " ENGINE=" + engine;
    }

    /** 
     * @see org.castor.ddl.schemaobject.Table#postCreateTable()
     * {@inheritDoc}
     */
    protected String postCreateTable() {
        return ")" + createEngineStatement();
    }

    /** 
     * @see org.castor.ddl.schemaobject.Table#toDropDDL()
     * {@inheritDoc}
     */
    public String toDropDDL() {
        StringBuffer buff = new StringBuffer(getConf().getLineSeparator());

        buff.append("DROP TABLE IF EXISTS ").append(getName());
        buff.append(getConf().getSqlStatDelimeter());

        return buff.toString();
    }

}

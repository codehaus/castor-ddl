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

import java.util.Iterator;

import org.castor.ddl.schemaobject.PrimaryKey;

/**
 * Final HSQL Primary key class
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class HsqlPrimaryKey extends PrimaryKey {

    /**
     * @see org.castor.ddl.schemaobject.PrimaryKey#toCreateDdl()
     * {@inheritDoc}
     */
    public String toCreateDdl() {
        if (getPrimaryKeyColumnCount() <= 0) { return ""; }
        
        StringBuffer buff = new StringBuffer(getConf().getLineSeparator());
        buff.append(getConf().getLineSeparator());

        buff.append("ALTER TABLE ").append(getTable().getName());
        buff.append(getConf().getLineSeparator()).append(
                getConf().getLineIndent());
        buff.append("ADD CONSTRAINT ").append(getName());
        buff.append(getConf().getLineSeparator()).append(
                getConf().getLineIndent());
        buff.append("PRIMARY KEY (");

        boolean isFirstField = true;
        for (Iterator i = getPrimaryKeyColumns().iterator(); i.hasNext();) {
            String columnname = (String) i.next();
                if (!isFirstField) {
                    buff.append(getConf().getSqlFieldDelimeter());
                    buff.append(" ");
                }
                isFirstField = false;
                buff.append(columnname);
        }
        buff.append(")").append(getConf().getSqlStatDelimeter());

        return buff.toString();
    }

}

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

package org.castor.ddl.pointbase.schemaobject;

import java.util.Iterator;

import org.castor.ddl.schemaobject.Field;
import org.castor.ddl.schemaobject.Table;

/**
 * 
 * <br/>Created on Jul 11, 2006 - 5:26:14 PM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class PointBaseTable extends Table {

    /**
     * Constructor for PointBaseTable
     */
    public PointBaseTable() {
        super();
    }

    /**
     * @see org.castor.ddl.schemaobject.Table#toPrimaryKeyDDL()
     * {@inheritDoc}
     */
    public String toPrimaryKeyDDL() {
        boolean isHasPK = false;
        StringBuffer buff = new StringBuffer(getConf().getLineSeparator());
        buff.append(getConf().getLineSeparator());

        buff.append("ALTER TABLE ").append(getName());
        buff.append(getConf().getLineSeparator()).append(
                getConf().getLineIndent());
        buff.append("ADD CONSTRAINT pk_").append(getName());
        buff.append(getConf().getLineSeparator()).append(
                getConf().getLineIndent());
        buff.append("PRIMARY KEY (");

        boolean isFirstField = true;
        for (Iterator i = getFields().iterator(); i.hasNext();) {
            Field field = (Field) i.next();
            if (field.isIdentity()) {
                isHasPK = true;
                if (!isFirstField) {
                    buff.append(getConf().getSqlFieldDelimeter());
                    buff.append(" ");
                }
                isFirstField = false;
                buff.append(field.getName());
            }
        }
        buff.append(")").append(getConf().getSqlStatDelimeter());

        //have no primary key
        if (!isHasPK) {
            return "";
        }
        return buff.toString();
    }

}

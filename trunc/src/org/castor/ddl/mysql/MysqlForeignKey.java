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

package org.castor.ddl.mysql;

import org.castor.ddl.schemaobject.ForeignKey;

/**
 * 
 * <br/>Created on Jul 5, 2006 - 9:25:57 AM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class MysqlForeignKey extends ForeignKey {

    /**
     * Constructor for MysqlForeignKey
     */
    protected MysqlForeignKey() {
        super();
    }

    /**
     * @see org.castor.ddl.schemaobject.ForeignKey#toDDL() 
     * {@inheritDoc}
     */
    public String toDDL() {
        StringBuffer buff = new StringBuffer(getConf().getLineSeparator());

        buff.append("ALTER TABLE ").append(getTable().getName());

        // constraint
        buff.append(getConf().getLineSeparator()).append(
                getConf().getLineIndent());
        buff.append("ADD CONSTRAINT ").append(getConstraintName());

        // foreign key
        buff.append(getConf().getLineSeparator()).append(
                getConf().getLineIndent());
        buff.append("FOREIGN KEY ").append(getFkName()).append(" ");
        buff.append(makeListofParams(getFkkeyList()));

        // references
        buff.append(getConf().getLineSeparator()).append(
                getConf().getLineIndent());
        buff.append("REFERENCES ").append(getReferenceTableName()).append(" ");
        buff.append(makeListofParams(getReferenceKeyList()));

        // on delete
        String opt = getConf().getStringValue(
                MysqlConfigurationKey.FOREIGN_KEY_ON_DELETE, null);
        if (opt != null && !"".equals(opt)) {
            buff.append(getConf().getLineSeparator()).append(
                    getConf().getLineIndent());
            buff.append("ON DELETE ").append(opt);
        }

        opt = getConf().getStringValue(
                MysqlConfigurationKey.FOREIGN_KEY_ON_UPDATE, null);
        if (opt != null && !"".equals(opt)) {
            buff.append(getConf().getLineSeparator()).append(
                    getConf().getLineIndent());
            buff.append("ON UPDATE ").append(opt);
        }

        buff.append(getConf().getSqlStatDelimeter());
        return buff.toString();
    }

}

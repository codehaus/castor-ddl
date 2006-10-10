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
import org.castor.ddl.schemaobject.Table;

/**
 * HSQL table.
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public final class HsqlTable extends Table {
    /**
     * {@inheritDoc}
     */
    public String toCreateDDL() throws GeneratorException {
        String separator = getConfiguration().getLineSeparator();
        String delimiter = getConfiguration().getSqlStatDelimeter();

        StringBuffer sb = new StringBuffer();
        sb.append(separator).append(separator);
        sb.append("CREATE TABLE ").append(getName()).append(" (");
        sb.append(separator);
        sb.append(fields());
        sb.append(separator);
        sb.append(')');
        sb.append(delimiter);
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    public String toDropDDL() {
        String separator = getConfiguration().getLineSeparator();
        String delimiter = getConfiguration().getSqlStatDelimeter();

        StringBuffer sb = new StringBuffer();
        sb.append(separator).append(separator);
        sb.append("DROP TABLE IF EXISTS ").append(getName());
        sb.append(delimiter);
        return sb.toString();
    }
}

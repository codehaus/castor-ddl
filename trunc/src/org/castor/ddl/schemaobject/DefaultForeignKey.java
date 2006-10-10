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
package org.castor.ddl.schemaobject;

/**
 * Default foreign key.
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public final class DefaultForeignKey extends ForeignKey  {
    /**
     * {@inheritDoc}
     */
    public String toCreateDDL() {
        String separator = getConfiguration().getLineSeparator();
        String delimiter = getConfiguration().getSqlStatDelimeter();

        StringBuffer sb = new StringBuffer();
        sb.append(separator).append(separator);
        sb.append("ALTER TABLE ").append(getTable().getName());
        sb.append(separator);
        sb.append("ADD CONSTRAINT ").append(getName());
        sb.append(separator);
        sb.append("FOREIGN KEY (").append(fieldNames()).append(')');
        sb.append(separator);
        sb.append("REFERENCES ").append(getReferenceTable().getName());
        sb.append(" (").append(referencedFieldNames()).append(')');
        sb.append(delimiter);
        return sb.toString();
    }
}

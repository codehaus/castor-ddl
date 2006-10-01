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
package org.castor.ddl.schemaobject;

import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.castor.ddl.GeneratorException;

/**
 * Schema contains a set of table and schema options.
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public class Schema extends AbstractSchemaObject {
    /**logging*/
    private static final Log LOG = LogFactory.getLog(Schema.class);

    /** schema name */
    private String _name = null;

    /** table list */
    private Vector _tables;

    /**
     * 
     * @return Returns the name.
     */
    public final String getName() {
        return _name;
    }

    /**
     * Constructor for Schema
     */
    public Schema() {
        super();
        _tables = new Vector();
    }

    /**
     * Set the name by _name.
     * 
     * @param name
     *            name
     */
    public final void setName(final String name) {
        _name = name;
    }

    /**
     * 
     * @return Returns the tables.
     */
    public final Vector getTables() {
        return _tables;
    }

    /**
     * Set the tables by _tables.
     * 
     * @param tables
     *            tables
     */
    public final void setTables(final Vector tables) {
        _tables = tables;
    }

    /**
     * 
     * @param table
     *            table
     * @throws GeneratorException generator exception
     */
    public final void addTable(final Table table) throws GeneratorException {
        if (_tables.contains(table)) {
            Table oldTable = (Table) _tables.get(_tables.indexOf(table));
            LOG.warn("merge table which is defined in two or more classes");
            oldTable.merge(table);
        } else {
            _tables.add(table);
        }
    }

    /**
     * 
     * @return table count
     */
    public final int getTableCount() {
        return _tables.size();
    }

    /**
     * Create DDL for schema
     * @return ddl string
     */

    public String toDDL() {
        return "";
    }
}

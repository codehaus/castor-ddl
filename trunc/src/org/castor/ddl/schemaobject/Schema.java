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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.castor.ddl.GeneratorException;


/**
 * Schema contains a set of table and schema options.
 * <br/>Created on Jun 23, 2006 - 5:55:43 PM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class Schema extends AbstractSchemaObject {
    /**logging*/
    private static final Log LOG = LogFactory.getLog(Schema.class);

    /** schema name */
    private String _name = null;

    /** table list */
    private Map _tables;

    /** key repositoty */
    KeyRepository _keyRepository;

    /**
     * 
     * @return Returns the name.
     */
    public String getName() {
        return _name;
    }

    /**
     * Constructor for Schema
     */
    protected Schema() {
        super();
        _tables = new HashMap();
        _keyRepository = new KeyRepository();
        addDefaultKey();
    }

    /**
     * Set the name by _name.
     * 
     * @param name
     *            name
     */
    public void setName(final String name) {
        _name = name;
    }

    /**
     * 
     * @return Returns the tables.
     */
    public Map getTables() {
        return _tables;
    }

    /**
     * Set the tables by _tables.
     * 
     * @param tables
     *            tables
     */
    public void setTables(final Map tables) {
        _tables = tables;
    }

    /**
     * 
     * @param table
     *            table
     * @throws GeneratorException generator exception
     */
    public void addTable(final Table table) throws GeneratorException {
        if (_tables.containsKey(table.getName())) {
            Table oldTable = (Table) _tables.get(table.getName());
            LOG.warn("merge table which is defined in two or more classes");
            oldTable.merge(table);
        } else {
            _tables.put(table.getName(), table);
        }
    }

    /**
     * 
     * @return table count
     */
    public int getTableCount() {
        return _tables.size();
    }

    /**
     * 
     * @return Returns the keyrep.
     */
    public KeyRepository getKeyRepository() {
        return _keyRepository;
    }

    /**
     * Set the keyrep by _keyrep.
     * 
     * @param keyrep
     *            key repository
     */
    public void setKeyRepository(final KeyRepository keyrep) {
        _keyRepository = keyrep;
    }

    /**
     * 
     * @param key
     *            key
     * @param value
     *            value
     */
    public void putKeyGenerator(final String key, final KeyGenerator value) {
        _keyRepository.putKeyGenerator(key, value);
    }

    /**
     * add default key
     * 
     */
    private void addDefaultKey() {
        _keyRepository.putKeyGenerator(KeyGenerator.IDENTITY_KEY,
                new IdentityKey(KeyGenerator.IDENTITY_KEY, null));
        _keyRepository.putKeyGenerator(KeyGenerator.MAX_KEY, new MaxKey(
                KeyGenerator.MAX_KEY, null));
        _keyRepository.putKeyGenerator(KeyGenerator.UUID_KEY, new UUIDKey(
                KeyGenerator.UUID_KEY, null));

    }

    /**
     * Create DDL for schema
     * @return ddl string
     */

    public String toDDL() {
//        String schema = getConf().getStringValue(BaseConfiguration.SCHEMA_NAME_KEY,
//                "");
//        if (schema == null || "".equals(schema)) {
//            return "";
//        }
//
//        return "CREATE SCHEMA " + schema + ";";
        return "";
    }
}

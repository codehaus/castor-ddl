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

import java.util.Vector;

/**
 * 
 * Created on Jun 23, 2006 - 5:55:43 PM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class Schema extends AbstractSchemaObject {

    /**schema name*/
    private String _name = null;

    /**table list*/
    private Vector _tables;

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
    public Schema() {
        super();
        _tables = new Vector();
        _keyRepository = new KeyRepository();
        addDefaultKey();
    }

    /**
     * Set the name by _name.
     * @param name 
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * 
     * @return Returns the tables.
     */
    public Vector getTables() {
        return _tables;
    }

    /**
     * Set the tables by _tables.
     * @param tables 
     */
    public void setTables(Vector tables) {
        _tables = tables;
    }
    
    /**
     * 
     * @param table
     */
    public void addTable(Table table) {
        _tables.add(table);
    }
    
    /**
     * 
     * @return
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
     * @param keyrep 
     */
    public void setKeyRepository(KeyRepository keyrep) {
        _keyRepository = keyrep;
    }
    
    public void putKeyGenerator(String key, KeyGenerator value) {
        _keyRepository.putKeyGenerator(key, value);
    }
    
    private void addDefaultKey() {
        _keyRepository.putKeyGenerator(KeyGenerator.IDENTITY_KEY, new IdentityKey(KeyGenerator.IDENTITY_KEY, null));
        _keyRepository.putKeyGenerator(KeyGenerator.MAX_KEY, new MaxKey(KeyGenerator.MAX_KEY, null));
        _keyRepository.putKeyGenerator(KeyGenerator.UUID_KEY, new UUIDKey(KeyGenerator.UUID_KEY, null));
        
    }

    /* (non-Javadoc)
     * @see org.castor.ddl.schemaobject.SchemaObject#toDDL()
     */
    public String toDDL() {
        // TODO Auto-generated method stub
        return null;
    }
}

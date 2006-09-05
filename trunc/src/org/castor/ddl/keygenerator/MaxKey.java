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

package org.castor.ddl.keygenerator;

import org.castor.ddl.schemaobject.KeyGenerator;
import org.castor.ddl.schemaobject.Table;
import org.exolab.castor.mapping.xml.KeyGeneratorDef;

/**
 * MaxKey is not used for DDL Generator
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public final class MaxKey extends KeyGenerator {
    /** sequence name*/
    private String _name = "IDENTITY";
    
    /** alias*/
    private String _alias;
    
    /**
     * Constructor for MaxKey
     * @param name name
     * @param alias alias
     */
    public MaxKey(final String name, final String alias) {
        super();
        _name = name;
        _alias = alias;
    }

    /**
     * 
     * Constructor for MaxKey
     * @param keyGenDef key gen definition
     */
    public MaxKey(final KeyGeneratorDef keyGenDef) {
        _alias = keyGenDef.getAlias();
        _name = keyGenDef.getName();
    }
    
    /**
     * 
     * @return Returns the alias.
     */
    public String getAlias() {
        return _alias;
    }

    /**
     * Set the alias by _alias.
     * @param alias alias
     */
    public void setAlias(final String alias) {
        _alias = alias;
    }

    /**
     * @see org.castor.ddl.schemaobject.KeyGenerator#getHashKey()
     * {@inheritDoc}
     */
    public String getHashKey() {
        if (_alias == null) {
            return _name;
        }
        return _alias;
    }

    /**
     * 
     * @return Returns the name.
     */
    public String getName() {
        return _name;
    }

    /**
     * Set the name by _name.
     * @param name name
     */
    public void setName(final String name) {
        _name = name;
    }

    /**
     * @see org.castor.ddl.schemaobject.KeyGenerator#toDDL()
     * {@inheritDoc}
     */
    public String toDDL() {
        return "";
    }

    /**
     * @see org.castor.ddl.schemaobject.KeyGenerator#getTable()
     * {@inheritDoc}
     */
    public Table getTable() {
        return null;
    }

    /**
     * @see org.castor.ddl.schemaobject.KeyGenerator#setTable
     * (org.castor.ddl.schemaobject.Table)
     * {@inheritDoc}
     */
    public void setTable(final Table table) { }
}

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

import org.exolab.castor.mapping.xml.KeyGeneratorDef;

/**
 * 
 * Created on Jun 24, 2006 - 2:05:13 AM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class UUIDKey extends KeyGenerator {
    /** sequence name*/
    public String _name;
    
    /** alias*/
    private String _alias;
    
    /**
     * Constructor for UUIDKey
     * @param name name
     * @param alias alias
     */
    protected UUIDKey(final String name, final String alias) {
        super();
        _name = name;
        _alias = alias;
    }


    /**
     * 
     * Constructor for UUIDKey
     * @param keyGenDef key generator def
     */
    protected UUIDKey(final KeyGeneratorDef keyGenDef) {
        _alias = keyGenDef.getAlias();
        _name = keyGenDef.getName();
    }
    
    /**
     * 
     * @return Returns the alias.
     */
    public final String getAlias() {
        return _alias;
    }

    /**
     * Set the alias by _alias.
     * @param alias alias
     */
    public final void setAlias(final String alias) {
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
    public final String getName() {
        return _name;
    }

    /**
     * Set the name by _name.
     * @param name name
     */
    public final void setName(final String name) {
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
    public void setTable(final Table table) {
    }

}

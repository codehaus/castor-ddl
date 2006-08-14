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

package org.castor.ddl;

import java.util.HashMap;
import java.util.Map;

import org.castor.ddl.schemaobject.KeyGenerator;

/**
 * KeyRepository handles KeyGenerators
 * <br/>Created on Jun 24, 2006 - 2:10:05 AM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public final class KeyRepository {

    /**table list*/
    private Map _keyGenerator;

    /**table list*/
    private Map _nameAlias;
    
    /**
     * Constructor for KeyRepository
     */
    public KeyRepository() {
        super();
        _keyGenerator = new HashMap();
        _nameAlias = new HashMap();
    }

    /**
     * 
     * @return Returns the keygens.
     */
    public Map getKeyGenerator() {
        return _keyGenerator;
    }

    /**
     * Set the keygens by _keygens.
     * @param keygens key generator
     */
    public void setKeyGenerator(final Map keygens) {
        _keyGenerator = keygens;
    }

    /**
     * 
     * @param key key
     * @param kg key generator
     */
    public void putKeyGenerator(final String key, final KeyGenerator kg) {
        _keyGenerator.put(key, kg);
        _nameAlias.put(kg.getName(), kg.getAlias());
    }
    
    /**
     * 
     * @param key key
     * @return key generator associates to key
     */
    public KeyGenerator getKeyGenerator(final String key) {
        KeyGenerator kg = (KeyGenerator) _keyGenerator.get(key);
        if (kg == null) {
            String alias = (String) _nameAlias.get(key);
            kg = (KeyGenerator) _keyGenerator.get(alias);
        }
        return kg;
    }    
}

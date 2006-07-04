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

import java.util.HashMap;
import java.util.Map;

import org.castor.ddl.GeneratorException;
import org.exolab.castor.mapping.xml.KeyGeneratorDef;

/**
 * 
 * Created on Jun 24, 2006 - 2:10:05 AM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public final class KeyRepository {

    /**table list*/
    private Map _keyGenerator;

    
    /**
     * Constructor for KeyRepository
     */
    public KeyRepository() {
        super();
        _keyGenerator = new HashMap();
        // TODO Auto-generated constructor stub
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
     * @param keygens 
     */
    public void setKeyGenerator(Map keygens) {
        _keyGenerator = keygens;
    }

    /**
     * 
     * @param key
     * @param kg
     */
    public void putKeyGenerator(String key, KeyGenerator kg) {
        _keyGenerator.put(key, kg);
    }
    
    /**
     * 
     * @param key
     * @return
     */
    public KeyGenerator getKeyGenerator(String key) {
        return (KeyGenerator)_keyGenerator.get(key);
    }
    
    public static KeyGenerator createKey(KeyGeneratorDef kg) throws GeneratorException {
        String name = kg.getName();
        
        if(KeyGenerator.MAX_KEY.equalsIgnoreCase(name))
            return new MaxKey(kg);

        if(KeyGenerator.HIGH_LOW_KEY.equalsIgnoreCase(name))
            return new HighLowKey(kg);

        if(KeyGenerator.UUID_KEY.equalsIgnoreCase(name))
            return new UUIDKey(kg);
        
        if(KeyGenerator.IDENTITY_KEY.equalsIgnoreCase(name))
            return new IdentityKey(kg);
        
        if(KeyGenerator.SEQUENCE_KEY.equalsIgnoreCase(name))
            return new SequenceKey(kg);
                
        return null; 
    }    
}

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

/**
 * 
 * Created on Jun 24, 2006 - 2:01:26 AM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class SequenceKey implements KeyGenerator {

    /** name*/
    private String _name;
    
    /** alias*/
    private String _alias;
    
    /** list of parameters*/
    private Map _params;

    /**
     * Constructor for KeyGenerator
     * @param name
     * @param alias
     */
    public SequenceKey(String name, String alias) {
        super();
        // TODO Auto-generated constructor stub
        _name = name;
        _alias = alias;
        _params = new HashMap();
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
     * @param alias 
     */
    public void setAlias(String alias) {
        _alias = alias;
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
     * @param name 
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * 
     * @return Returns the params.
     */
    public Map getParams() {
        return _params;
    }

    /**
     * Set the params by _params.
     * @param params 
     */
    public void setParams(Map params) {
        _params = params;
    }
    
    /**
     * 
     * @param key
     * @return
     */
    public String getParam(String key) {
        return (String)_params.get(key);
       
    }
    
    /**
     * 
     * @param key
     * @param value
     */
    public void putParam(String key, String value) {
        _params.put(key, value);
    }

    /* (non-Javadoc)
     * @see org.castor.ddl.schemaobject.KeyGenerator#getHashKey()
     */
    public String getHashKey() {
        // TODO Auto-generated method stub
        return null;
    }
}

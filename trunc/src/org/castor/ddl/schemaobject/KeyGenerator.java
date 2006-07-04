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
 * 
 * Created on Jun 23, 2006 - 5:44:13 PM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public abstract class KeyGenerator extends AbstractSchemaObject {
    /**
     * Constructor for KeyGenerator
     * @param conf
     */
    public KeyGenerator() {
        super();
    }

    /**max key*/
    public static String MAX_KEY = "MAX";

    /**high-low key*/
    public static String HIGH_LOW_KEY = "HIGH-LOW";

    /**uuid key*/
    public static String UUID_KEY = "UUID";

    /**identity key*/
    public static String IDENTITY_KEY = "IDENTITY";

    /**sequence key*/
    public static String SEQUENCE_KEY = "SEQUENCE";

    /**
     * 
     * @return hashkey
     */
    public abstract String getHashKey();
    
    /**
     * 
     * @return name
     */
    public abstract String getName();
    
    /**
     * 
     * @return alias
     */
    public abstract String getAlias();
}

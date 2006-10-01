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

import org.castor.ddl.Configuration;

/**
 * AbstractSchemaObject is the base class for Table, Field, ForeignKey, 
 * KeyGenerator...
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public abstract class AbstractSchemaObject implements SchemaObject {
    /**configuration */
    private Configuration _conf;
    
    /**
     * {@inheritDoc}
     */
    public final void setConfiguration(final Configuration conf) {
        _conf = conf;
        
    }

    /**
     * {@inheritDoc}
     */
    public final Configuration getConfiguration() {
        return _conf;
    }
}

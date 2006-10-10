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
 * Abstract base class for all schema objects.
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public abstract class AbstractSchemaObject implements SchemaObject {
    //--------------------------------------------------------------------------
    
    /** Factor for calculation of hash code. */
    protected static final int HASHFACTOR = 17;

    /** Configuration of the schema object. */
    private Configuration _config;
    
    /** Name of the schema object. */
    private String _name;
    
    //--------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public final void setConfiguration(final Configuration config) {
        _config = config;
    }

    /**
     * {@inheritDoc}
     */
    public final Configuration getConfiguration() {
        return _config;
    }
    
    /**
     * {@inheritDoc}
     */
    public final void setName(final String name) {
        _name = name;
    }
    
    /**
     * {@inheritDoc}
     */
    public final String getName() {
        return _name;
    }

    //--------------------------------------------------------------------------
    
    /**
     * Check the 2 given objects for equality by taking into account that one or both of
     * them may be <code>null</code>.
     * 
     * @param obj1 First object.
     * @param obj2 Second object.
     * @return <code>true</code> if both objects are null or equal as defined by
     *         equals method of object. <code>false</code> if only one of the objects
     *         is null or if they are not equal.
     */
    protected final boolean equals(final Object obj1, final Object obj2) {
        if ((obj1 == null) && (obj2 == null)) { return true; }
        if ((obj1 == null) || (obj2 == null)) { return false; }
        return obj1.equals(obj2);
    }
    
    //--------------------------------------------------------------------------
}

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Abstract base class for all key generators.
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public abstract class KeyGenerator extends AbstractSchemaObject {
    //--------------------------------------------------------------------------

    /** The <a href="http://jakarta.apache.org/commons/logging/">Jakarta Commons
     *  Logging </a> instance used for all logging. */
    private static final Log LOG = LogFactory.getLog(KeyGenerator.class);
    
    /** Name of the key generator algorithm. */
    private final String _name;
    
    /** Alias of the key generator. */
    private final String _alias;

    /** Table the key generator creates keys for. */
    private Table _table;
    
    //--------------------------------------------------------------------------
    
    /**
     * Construct key generator with given name and alias.
     * 
     * @param name Name of the key generator algorithm.
     * @param alias Alias of the key generator.
     */
    protected KeyGenerator(final String name, final String alias) {
        super();
        
        _name = name;
        _alias = (alias != null) ? alias : name;
    }

    //--------------------------------------------------------------------------

    /**
     * Get name of the key generator algorithm.
     * 
     * @return Name of the key generator algorithm.
     */
    public final String getName() { return _name; }

    /**
     * Get alias of the key generator.
     * 
     * @return Alias of the key generator.
     */
    public final String getAlias() { return _alias; }
    
    /**
     * Set table the key generator creates keys for.
     * 
     * @param table Table the key generator creates keys for.
     */
    public final void setTable(final Table table) {
        _table = table;
    }
    
    /**
     * Get table the key generator creates keys for.
     * 
     * @return Table the key generator creates keys for.
     */
    public final Table getTable() {
        return _table;
    }

    /**
     * Generate DDL for key generator.
     * 
     * @return DDL script.
     */
    public abstract String toDDL();

    /**
     * Check wether this key generator is compatible with the given one to allow merge
     * of table definitions.
     * 
     * @param keygenerator Key generator to merge.
     */
    public final void merge(final KeyGenerator keygenerator) {
        if (keygenerator == null) {
            String msg = "Merge table has no key generator";
            LOG.warn(msg);
        } else {
            String alias = getAlias();
            if ((alias == null) || !alias.equalsIgnoreCase(keygenerator.getAlias())) {
                String msg = "Merge table has different key generator: "
                    + alias + " / " + keygenerator.getAlias();
                LOG.warn(msg);
            }
        }
    }

    //--------------------------------------------------------------------------
}

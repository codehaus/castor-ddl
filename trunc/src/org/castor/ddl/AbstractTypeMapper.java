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

import org.castor.ddl.typeinfo.TypeInfo;

/**
 * AbstractTypeMapper is the base class for mapping JDBC supported type and 
 * RDBMS data type.
 * <p/>The initialize(Configuration) is used to defined the mapping table in which
 * the configuration is handled parameters for TypeInfo. Please refer to 
 * org.castor.ddl.typeinfo for TypeInfo definitions  
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 * @version $ $Date: 2006-04-25 16:09:10 -0600 (Tue, 25 Apr 2006) $
 */
public abstract class AbstractTypeMapper implements TypeMapper {
    /** Map of known types which associates JDBC type to corresponding TypeInfo. */
    private final Map _types = new HashMap();
    
    /**
     * Construct an abstract TypeMapper using given configuration to get default
     * parameters for parameterized types.
     * 
     * @param conf The configuration to get default parameter values from.
     */
    public AbstractTypeMapper(final Configuration conf) {
        initialize(conf);
    }
    
    /**
     * Initialize map of known types using given configuration to get default parameters
     * for parameterized types.
     * 
     * @param conf The configuration to get default parameter values from.
     */
    protected abstract void initialize(final Configuration conf);
    
    /**
     * Add TypeInfo to map of known types.
     * 
     * @param type The TypeInfo to add.
     */
    protected final void add(final TypeInfo type) {
        _types.put(type.getJdbcType(), type);
    }
    
    /**
     * @see org.castor.ddl.TypeMapper#getType(java.lang.String)
     * {@inheritDoc}
     */
    public final TypeInfo getType(final String jdcbType) {
        String ddlJdbcType = jdcbType.toLowerCase();
        
        if (ddlJdbcType.matches(".*\\Q[\\E.*")) {
            ddlJdbcType = ddlJdbcType.substring(0, ddlJdbcType.indexOf("["));
        }
        return (TypeInfo) _types.get(ddlJdbcType);
    }
}

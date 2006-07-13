/*
 * Copyright 2006 Ralf Joachim
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.castor.ddl.typeinfo;

/**
 * Abstract TypeInfo with common properties of all implementations.
 * 
 * @author <a href="mailto:ralf DOT joachim AT syscon-world DOT de">Ralf Joachim</a>
 * @version $Revision: 5951 $ $Date: 2006-04-25 16:09:10 -0600 (Tue, 25 Apr 2006) $
 */
public abstract class AbstractType implements TypeInfo {
    /** Prefix of all parameters for types in ddl.properties file. */
    protected static final String PARAM_PREFIX = "default_";
    
    /** Postfix of length parameters for types in ddl.properties file. */
    protected static final String PARAM_POSTFIX_LENGTH = "_length";
    
    /** Postfix of precision parameters for types in ddl.properties file. */
    protected static final String PARAM_POSTFIX_PRECISION = "_precision";
    
    /** Postfix of decimals parameters for types in ddl.properties file. */
    protected static final String PARAM_POSTFIX_DECIMALS = "_decimals";
    
    /** JDBC type. */
    private final String _jdbcType;

    /** SQL type. */
    private final String _sqlType;

    /**
     * Construct a new TypeInfo instance with given Configuration, JDBC type and SQL type.
     * 
     * @param jdbcType The JDBC type.
     * @param sqlType The SQL type.
     */
    public AbstractType(final String jdbcType, final String sqlType) {
        _jdbcType = jdbcType;
        _sqlType = sqlType;
    }

    /**
     * @see org.castor.ddl.typeinfo.TypeInfo#getJdbcType()
     * {@inheritDoc}
     */
    public final String getJdbcType() { return _jdbcType; }
    
    /**
     * @see org.castor.ddl.typeinfo.TypeInfo#getSqlType()
     * {@inheritDoc}
     */
    public final String getSqlType() { return _sqlType; }
}

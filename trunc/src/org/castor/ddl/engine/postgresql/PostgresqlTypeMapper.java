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

package org.castor.ddl.engine.postgresql;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.castor.ddl.AbstractTypeMapper;
import org.castor.ddl.Configuration;
import org.castor.ddl.typeinfo.NoParamType;
import org.castor.ddl.typeinfo.OptionalLengthType;
import org.castor.ddl.typeinfo.OptionalPrecisionDecimalsType;
import org.castor.ddl.typeinfo.OptionalPrecisionType;

/**
 * Final TypeMapper for MySQL database.
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 * @version $Revision: 5951 $ $Date: 2006-04-25 16:09:10 -0600 (Tue, 25 Apr 2006) $
 */
public final class PostgresqlTypeMapper extends AbstractTypeMapper {
    /**logging*/
    private static final Log LOG = LogFactory.getLog(PostgresqlTypeMapper.class);

    /**
     * Construct a TypeMapper for MySQL database using given configuration to get default
     * parameters for parameterized types.
     * 
     * @param conf The configuration to get default parameter values from.
     */
    public PostgresqlTypeMapper(final Configuration conf) {
        super(conf);
    }

    /**
     * {@inheritDoc}
     */
    protected void initialize(final Configuration conf) {
        // numeric types
        this.add(new NoParamType("bit", "BOOLEAN"));
        LOG.warn("PostgreSQL does not support 'TINYINT' type, use SMALLINT instead.");
        this.add(new NoParamType("tinyint", "SMALLINT"));
        this.add(new NoParamType("smallint", "SMALLINT"));
        this.add(new NoParamType("integer", "INTEGER"));
        this.add(new NoParamType("int", "INTEGER"));
        this.add(new NoParamType("bigint", "BIGINT"));
        
        this.add(new NoParamType("float", "DOUBLE PRECISION"));
        this.add(new NoParamType("double", "DOUBLE PRECISION"));
        this.add(new NoParamType("real", "REAL"));
        this.add(new OptionalPrecisionDecimalsType("numeric", "NUMERIC", conf));
        this.add(new OptionalPrecisionDecimalsType("decimal", "DECIMAL", conf));

        // character types
        this.add(new OptionalLengthType("char", "CHAR", conf));
        this.add(new OptionalLengthType("varchar", "VARCHAR", conf));
        LOG.warn("PostgreSQL does not support 'LONGVARCHAR' type, use VARCHAR instead.");
        this.add(new OptionalLengthType("longvarchar", "VARCHAR", conf));
        
        // date and time types
        this.add(new NoParamType("date", "DATE"));
        this.add(new OptionalPrecisionType("time", "TIME", conf));
        this.add(new OptionalPrecisionType("timestamp", "TIMESTAMP", conf));
        
        // other types
        LOG.warn("PostgreSQL does not support 'BINARY' type, use BYTEA instead.");
        this.add(new NoParamType("binary", "BYTEA"));
        LOG.warn("PostgreSQL does not support 'VARBINARY' type, use BYTEA instead.");
        this.add(new NoParamType("varbinary", "BYTEA"));
        LOG.warn("PostgreSQL does not support 'LONGVARBINARY' type, use BYTEA instead.");
        this.add(new NoParamType("longvarbinary", "BYTEA"));
        
        this.add(new NoParamType("other", "BYTEA"));
        this.add(new NoParamType("javaobject", "BYTEA"));
        this.add(new NoParamType("blob", "BYTEA"));
        this.add(new NoParamType("clob", "TEXT"));
    }
}

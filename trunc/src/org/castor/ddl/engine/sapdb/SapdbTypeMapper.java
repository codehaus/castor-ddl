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

package org.castor.ddl.engine.sapdb;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.castor.ddl.AbstractTypeMapper;
import org.castor.ddl.Configuration;
import org.castor.ddl.typeinfo.NoParamType;
import org.castor.ddl.typeinfo.OptionalLengthType;
import org.castor.ddl.typeinfo.OptionalPrecisionDecimalsType;
import org.castor.ddl.typeinfo.RequiredLengthType;

/**
 * Final TypeMapper for SapDB database.
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 * @version $Revision: 5951 $ $Date: 2006-04-25 16:09:10 -0600 (Tue, 25 Apr 2006) $
 */
public final class SapdbTypeMapper extends AbstractTypeMapper {
    /**logging*/
    private static final Log LOG = LogFactory.getLog(SapdbTypeMapper.class);
    /**
     * Construct a TypeMapper for Sapdb database using given configuration to get default
     * parameters for parameterized types.
     * 
     * @param conf The configuration to get default parameter values from.
     */
    public SapdbTypeMapper(final Configuration conf) {
        super(conf);
    }

    /**
     * {@inheritDoc}
     */
    protected void initialize(final Configuration conf) {
        // numeric types
        this.add(new NoParamType("bit", "BOOLEAN"));
        LOG.warn("SapDB does not support 'TINYINT' type, use SMALLINT instead.");
        this.add(new NoParamType("tinyint", "SMALLINT"));
        this.add(new NoParamType("smallint", "SMALLINT"));
        this.add(new NoParamType("integer", "INTEGER"));
        this.add(new NoParamType("int", "INTEGER"));
        LOG.warn("SapDB does not support 'BIGINT' type, use NUMERIC instead.");
        this.add(new NoParamType("bigint", "NUMERIC"));
        
        this.add(new OptionalLengthType("float", "FLOAT", conf));
        this.add(new NoParamType("double", "DOUBLE PRECISION"));
        this.add(new NoParamType("real", "DOUBLE PRECISION"));
        this.add(new OptionalPrecisionDecimalsType("numeric", "NUMERIC", conf));
        this.add(new OptionalPrecisionDecimalsType("decimal", "DECIMAL", conf));

        // character types
        this.add(new OptionalLengthType("char", "CHAR", conf));
        this.add(new RequiredLengthType("varchar", "VARCHAR", conf));
        LOG.warn("SapDB does not support 'LONGVARCHAR' type, use LONG instead.");
        this.add(new RequiredLengthType("longvarchar", "LONG", conf));
        
        // date and time types
        this.add(new NoParamType("date", "DATE"));
        this.add(new NoParamType("time", "TIME"));
        this.add(new NoParamType("timestamp", "TIMESTAMP"));
        
        // other types
        LOG.warn("SapDB does not support 'BINARY' type, use BLOB instead.");
        this.add(new NoParamType("binary", "BLOB"));
        LOG.warn("SapDB does not support 'VARBINARY' type, use BLOB instead.");
        this.add(new NoParamType("varbinary", "BLOB"));
        LOG.warn("SapDB does not support 'LONGVARBINARY' type, use BLOB instead.");
        this.add(new NoParamType("longvarbinary", "BLOB"));
        
        this.add(new NoParamType("other", "BLOB"));
        this.add(new NoParamType("javaobject", "BLOB"));
        this.add(new NoParamType("blob", "BLOB"));
        this.add(new NoParamType("clob", "CLOB"));
    }
}

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

package org.castor.ddl.mysql;

import org.castor.ddl.AbstractTypeMapper;
import org.castor.ddl.Configuration;
import org.castor.ddl.typeinfo.NoParamType;
import org.castor.ddl.typeinfo.OptionalLengthType;
import org.castor.ddl.typeinfo.OptionalPrecisionDecimalsType;
import org.castor.ddl.typeinfo.OptionalPrecisionType;
import org.castor.ddl.typeinfo.RequiredLengthType;

/**
 * Final TypeMapper for MySQL database.
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 * @version $Revision: 5951 $ $Date: 2006-04-25 16:09:10 -0600 (Tue, 25 Apr 2006) $
 */
public final class MySQLTypeMapper extends AbstractTypeMapper {
    /**
     * Construct a TypeMapper for MySQL database using given configuration to get default
     * parameters for parameterized types.
     * 
     * @param conf The configuration to get default parameter values from.
     */
	public MySQLTypeMapper(Configuration conf) {
		super(conf);
	}

	/**
	 * @see org.castor.ddl.AbstractTypeMapper#initialize(org.castor.ddl.Configuration)
     * {@inheritDoc}
	 */
	protected void initialize(final Configuration conf) {
        // numeric types
		this.add(new NoParamType("bit", "TINYINT(1)"));
		this.add(new OptionalPrecisionType("tinyint", "TINYINT", conf));
		this.add(new OptionalPrecisionType("smallint", "SMALLINT", conf));
		this.add(new OptionalPrecisionType("integer", "INTEGER", conf));
		this.add(new OptionalPrecisionType("bigint", "BIGINT", conf));
        
		this.add(new OptionalPrecisionDecimalsType("float", "FLOAT", conf));
		this.add(new OptionalPrecisionDecimalsType("double", "DOUBLE", conf));
		this.add(new OptionalPrecisionDecimalsType("real", "REAL", conf));
		this.add(new OptionalPrecisionDecimalsType("numeric", "NUMERIC", conf));
		this.add(new OptionalPrecisionDecimalsType("decimal", "DECIMAL", conf));

        // character types
		this.add(new OptionalLengthType("char", "CHAR", conf));
		this.add(new OptionalLengthType("varchar", "VARCHAR", conf));
		this.add(new OptionalLengthType("longvarchar", "VARCHAR", conf));
        
        // date and time types
		this.add(new NoParamType("date", "DATE"));
		this.add(new NoParamType("time", "TIME"));
		this.add(new OptionalPrecisionType("timestamp", "TIMESTAMP", conf));
        
        // other types
		this.add(new RequiredLengthType("binary", "BINARY", conf));
		this.add(new RequiredLengthType("varbinary", "VARBINARY", conf));
		this.add(new RequiredLengthType("longvarbinary", "VARBINARY", conf));
		
		this.add(new NoParamType("other", "BLOB"));
		this.add(new NoParamType("javaobject", "BLOB"));
		this.add(new NoParamType("blob", "BLOB"));
		this.add(new NoParamType("clob", "TEXT"));
	}
}

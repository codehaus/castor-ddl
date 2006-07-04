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

package org.castor.ddl.oracle;

import org.castor.ddl.AbstractTypeMapper;
import org.castor.ddl.Configuration;
import org.castor.ddl.typeinfo.NoParamType;
import org.castor.ddl.typeinfo.OptionalLengthType;
import org.castor.ddl.typeinfo.OptionalPrecisionDecimalsType;
import org.castor.ddl.typeinfo.OptionalPrecisionType;
import org.castor.ddl.typeinfo.RequiredLengthType;

/**
 * Final TypeMapper for Oracle database.
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 * @version $Revision: 5951 $ $Date: 2006-04-25 16:09:10 -0600 (Tue, 25 Apr 2006) $
 */
public final class OracleTypeMapper extends AbstractTypeMapper {
    /**
     * Construct a TypeMapper for Oracle database using given configuration to get default
     * parameters for parameterized types.
     * 
     * @param conf The configuration to get default parameter values from.
     */
	public OracleTypeMapper(Configuration conf) {
		super(conf);
	}

	/**
	 * @see org.castor.ddl.AbstractTypeMapper#initialize(org.castor.ddl.Configuration)
     * {@inheritDoc}
	 */
	protected void initialize(final Configuration conf) {
        // numeric types
		this.add(new NoParamType("bit", "BOOLEAN"));
		this.add(new NoParamType("tinyint", "SMALLINT"));
		this.add(new NoParamType("smallint", "SMALLINT"));
		this.add(new NoParamType("integer", "INTEGER"));
		this.add(new NoParamType("bigint", "INTEGER"));
        
		this.add(new OptionalPrecisionDecimalsType("float", "FLOAT", conf));
		this.add(new OptionalPrecisionDecimalsType("double", "DOUBLE PRECISION", conf));
		this.add(new NoParamType("real", "REAL"));
		this.add(new OptionalPrecisionDecimalsType("numeric", "NUMERIC", conf));
		this.add(new OptionalPrecisionDecimalsType("decimal", "DECIMAL", conf));

        // character types
		this.add(new OptionalLengthType("char", "CHAR", conf));
		this.add(new RequiredLengthType("varchar", "VARCHAR2", conf));
		this.add(new OptionalLengthType("longvarchar", "VARCHAR2", conf));
        
        // date and time types
		this.add(new NoParamType("date", "DATE"));
		this.add(new NoParamType("time", "DATE"));
		this.add(new OptionalPrecisionType("timestamp", "TIMESTAMP", conf));
        
        // other types
		this.add(new RequiredLengthType("binary", "RAW", conf));
		this.add(new NoParamType("varbinary", "LONG RAW"));
		this.add(new NoParamType("longvarbinary", "LONG RAW"));
		
		this.add(new NoParamType("other", "BLOB"));
		this.add(new NoParamType("javaobject", "BLOB"));
		this.add(new NoParamType("blob", "BLOB"));
		this.add(new NoParamType("clob", "CLOB"));
	}
}

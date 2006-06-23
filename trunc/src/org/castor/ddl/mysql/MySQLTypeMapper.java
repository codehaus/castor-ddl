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
import org.castor.ddl.TypeInfo;


/**
 * TypeMapper for MySQL
 * Created on Jun 4, 2006 - 10:32:57 AM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public class MySQLTypeMapper extends AbstractTypeMapper {

	/**
	 * 
	 */
	public MySQLTypeMapper(Configuration conf) {
		super(conf);
		doMap();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ddl.mapper.AbstractTypeMapper#doMap()
	 */
	public void doMap() {
		this.setType("bit", new TypeInfo("TINYINT(1)"));
		this.setType("tinyint", new TypeInfo("TINYINT", conf.getIntegerValue(
				DEFAULT_TINYINT_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), false));
		this.setType("smallint", new TypeInfo("SMALLINT", conf.getIntegerValue(
				DEFAULT_SMALLINT_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), false));
		this.setType("integer", new TypeInfo("INTEGER", conf.getIntegerValue(
				DEFAULT_INTEGER_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), false));
		this.setType("bigint", new TypeInfo("BIGINT", conf.getIntegerValue(
				DEFAULT_BIGINT_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), false));
		this.setType("float", new TypeInfo("FLOAT", conf.getIntegerValue(
				DEFAULT_FLOAT_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), false, conf.getIntegerValue(
				DEFAULT_FLOAT_DECIMAL_KEY, TypeInfo.UNDEFINED_DECIMAL), false));
		this.setType("double", new TypeInfo("DOUBLE", conf.getIntegerValue(
				DEFAULT_DOUBLE_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), false, conf.getIntegerValue(
						DEFAULT_DOUBLE_DECIMAL_KEY, TypeInfo.UNDEFINED_DECIMAL), false));
		this.setType("real", new TypeInfo("REAL", conf.getIntegerValue(
				DEFAULT_REAL_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), false, conf.getIntegerValue(
						DEFAULT_REAL_DECIMAL_KEY, TypeInfo.UNDEFINED_DECIMAL), false));
		this.setType("numeric", new TypeInfo("NUMERIC", conf.getIntegerValue(
				DEFAULT_NUMERIC_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), false, conf.getIntegerValue(
						DEFAULT_NUMERIC_DECIMAL_KEY, TypeInfo.UNDEFINED_DECIMAL), false));
		this.setType("decimal", new TypeInfo("DECIMAL", conf.getIntegerValue(
				DEFAULT_DECIMAL_LENGTH_KEY, -1), false, conf.getIntegerValue(
						DEFAULT_DECIMAL_DECIMAL_KEY, TypeInfo.UNDEFINED_LENGTH), false));
		this.setType("char", new TypeInfo("CHAR", conf.getIntegerValue(
				DEFAULT_CHAR_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), false));
		this.setType("varchar", new TypeInfo("VARCHAR", conf.getIntegerValue(
				DEFAULT_VARCHAR_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), false));
		this.setType("longvarchar", new TypeInfo("VARCHAR", conf.getIntegerValue(
				DEFAULT_LONGVARCHAR_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), false));
		this.setType("date", new TypeInfo("DATE"));
		this.setType("time", new TypeInfo("TIME"));
		this.setType("timestamp", new TypeInfo("TIMESTAMP", conf.getIntegerValue(
				DEFAULT_TIMESTAMP_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), true));
		this.setType("binary", new TypeInfo("BINARY", conf.getIntegerValue(
				DEFAULT_BINARY_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), true));
		this.setType("varbinary", new TypeInfo("VARBINARY", conf.getIntegerValue(
				DEFAULT_VARBINARY_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), true));
		this.setType("longvarbinary", new TypeInfo("VARBINARY", conf.getIntegerValue(
				DEFAULT_LONGVARBINARY_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), true));
		
		// TODO review later 
		this.setType("other", new TypeInfo("BLOB"));
		this.setType("javaobject", new TypeInfo("BLOB"));
		this.setType("blob", new TypeInfo("BLOB"));
		this.setType("clob", new TypeInfo("TEXT"));
	}

}

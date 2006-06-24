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
import org.castor.ddl.typeinfo.LengthDecimalType;
import org.castor.ddl.typeinfo.LengthType;
import org.castor.ddl.typeinfo.NoParamType;
import org.castor.ddl.typeinfo.TypeInfo;


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
		this.addTypeInfo("bit", new NoParamType("TINYINT(1)"));
		this.addTypeInfo("tinyint", new LengthType("TINYINT", conf.getIntegerValue(
				DEFAULT_TINYINT_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), false));
		this.addTypeInfo("smallint", new LengthType("SMALLINT", conf.getIntegerValue(
				DEFAULT_SMALLINT_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), false));
		this.addTypeInfo("integer", new LengthType("INTEGER", conf.getIntegerValue(
				DEFAULT_INTEGER_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), false));
		this.addTypeInfo("bigint", new LengthType("BIGINT", conf.getIntegerValue(
				DEFAULT_BIGINT_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), false));
		this.addTypeInfo("float", new LengthDecimalType("FLOAT", conf.getIntegerValue(
				DEFAULT_FLOAT_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), false, conf.getIntegerValue(
				DEFAULT_FLOAT_DECIMAL_KEY, TypeInfo.UNDEFINED_DECIMAL), false));
		this.addTypeInfo("double", new LengthDecimalType("DOUBLE", conf.getIntegerValue(
				DEFAULT_DOUBLE_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), false, conf.getIntegerValue(
						DEFAULT_DOUBLE_DECIMAL_KEY, TypeInfo.UNDEFINED_DECIMAL), false));
		this.addTypeInfo("real", new LengthDecimalType("REAL", conf.getIntegerValue(
				DEFAULT_REAL_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), false, conf.getIntegerValue(
						DEFAULT_REAL_DECIMAL_KEY, TypeInfo.UNDEFINED_DECIMAL), false));
		this.addTypeInfo("numeric", new LengthDecimalType("NUMERIC", conf.getIntegerValue(
				DEFAULT_NUMERIC_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), false, conf.getIntegerValue(
						DEFAULT_NUMERIC_DECIMAL_KEY, TypeInfo.UNDEFINED_DECIMAL), false));
		this.addTypeInfo("decimal", new LengthDecimalType("DECIMAL", conf.getIntegerValue(
				DEFAULT_DECIMAL_LENGTH_KEY, -1), false, conf.getIntegerValue(
						DEFAULT_DECIMAL_DECIMAL_KEY, TypeInfo.UNDEFINED_LENGTH), false));
		this.addTypeInfo("char", new LengthType("CHAR", conf.getIntegerValue(
				DEFAULT_CHAR_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), false));
		this.addTypeInfo("varchar", new LengthType("VARCHAR", conf.getIntegerValue(
				DEFAULT_VARCHAR_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), false));
		this.addTypeInfo("longvarchar", new LengthType("VARCHAR", conf.getIntegerValue(
				DEFAULT_LONGVARCHAR_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), false));
		this.addTypeInfo("date", new NoParamType("DATE"));
		this.addTypeInfo("time", new NoParamType("TIME"));
		this.addTypeInfo("timestamp", new LengthType("TIMESTAMP", conf.getIntegerValue(
				DEFAULT_TIMESTAMP_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), true));
		this.addTypeInfo("binary", new LengthType("BINARY", conf.getIntegerValue(
				DEFAULT_BINARY_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), true));
		this.addTypeInfo("varbinary", new LengthType("VARBINARY", conf.getIntegerValue(
				DEFAULT_VARBINARY_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), true));
		this.addTypeInfo("longvarbinary", new LengthType("VARBINARY", conf.getIntegerValue(
				DEFAULT_LONGVARBINARY_LENGTH_KEY, TypeInfo.UNDEFINED_LENGTH), true));
		
		// TODO review later 
		this.addTypeInfo("other", new NoParamType("BLOB"));
		this.addTypeInfo("javaobject", new NoParamType("BLOB"));
		this.addTypeInfo("blob", new NoParamType("BLOB"));
		this.addTypeInfo("clob", new NoParamType("TEXT"));
	}

}

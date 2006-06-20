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

import java.util.Map;


/**
 * this class is the base class for various TypeMapper for various DB
 * Created on Jun 4, 2006 - 10:32:41 AM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public abstract class AbstractTypeMapper implements TypeMapper {
	/** define the property's key for default length of bit */
	public static final String DEFAULT_BIT_LENGTH_KEY = "default_bit_length";

	/** define the property's key for default length of tinyint */
	public static final String DEFAULT_TINYINT_LENGTH_KEY = "default_tinyint_length";

	/** define the property's key for default length of smallint */
	public static final String DEFAULT_SMALLINT_LENGTH_KEY = "default_smallint_length";

	/** define the property's key for default length of integer */
	public static final String DEFAULT_INTEGER_LENGTH_KEY = "default_integer_length";

	/** define the property's key for default length of bitint */
	public static final String DEFAULT_BIGINT_LENGTH_KEY = "default_bigint_length";

	/** define the property's key for default length of float */
	public static final String DEFAULT_FLOAT_LENGTH_KEY = "default_float_length";

	/** define the property's key for default decimal of float */
	public static final String DEFAULT_FLOAT_DECIMAL_KEY = "default_float_decimal";
	

	/** define the property's key for default length of double */
	public static final String DEFAULT_DOUBLE_LENGTH_KEY = "default_double_length";

	/** define the property's key for default decimal of double */
	public static final String DEFAULT_DOUBLE_DECIMAL_KEY = "default_double_decimal";


	/** define the property's key for default length of real */
	public static final String DEFAULT_REAL_LENGTH_KEY = "default_real_length";

	/** define the property's key for default decimal of real */
	public static final String DEFAULT_REAL_DECIMAL_KEY = "default_real_decimal";


	/** define the property's key for default length of numeric */
	public static final String DEFAULT_NUMERIC_LENGTH_KEY = "default_numeric_length";

	/** define the property's key for default decimal of numeric */
	public static final String DEFAULT_NUMERIC_DECIMAL_KEY = "default_numeric_decimal";


	/** define the property's key for default length of decimal */
	public static final String DEFAULT_DECIMAL_LENGTH_KEY = "default_decimal_length";

	/** define the property's key for default decimal of decimal */
	public static final String DEFAULT_DECIMAL_DECIMAL_KEY = "default_decimal_decimal";


	/** define the property's key for default length of char */
	public static final String DEFAULT_CHAR_LENGTH_KEY = "default_char_length";

	/** define the property's key for default length of varchar */
	public static final String DEFAULT_VARCHAR_LENGTH_KEY = "default_varchar_length";

	/** define the property's key for default length of longvarchar */
	public static final String DEFAULT_LONGVARCHAR_LENGTH_KEY = "default_longvarchar_length";

	/** define the property's key for default length of date */
	public static final String DEFAULT_DATE_LENGTH_KEY = "default_date_length";

	/** define the property's key for default length of time */
	public static final String DEFAULT_TIME_LENGTH_KEY = "default_time_length";

	/** define the property's key for default length of timestamp */
	public static final String DEFAULT_TIMESTAMP_LENGTH_KEY = "default_timestamp_length";

	/** define the property's key for default length of binary */
	public static final String DEFAULT_BINARY_LENGTH_KEY = "default_binary_length";

	/** define the property's key for default length of varbinary */
	public static final String DEFAULT_VARBINARY_LENGTH_KEY = "default_varbinary_length";

	/** define the property's key for default length of longvarbinary */
	public static final String DEFAULT_LONGVARBINARY_LENGTH_KEY = "default_longvarbinary_length";

	/** define the property's key for default length of other */
	public static final String DEFAULT_OTHER_LENGTH_KEY = "default_other_length";

	/** define the property's key for default length of javaobject */
	public static final String DEFAULT_JAVAOBJECT_LENGTH_KEY = "default_javaobject_length";

	/** define the property's key for default length of blob */
	public static final String DEFAULT_BLOB_LENGTH_KEY = "default_blob_length";

	/** define the property's key for default length of clob */
	public static final String DEFAULT_CLOB_LENGTH_KEY = "default_clob_length";

	/**
	 * define type _mapping (key, value)
	 */
	protected Map typeMap;
	
	/**
	 * configuration properties for DDL generator
	 */
	protected Configuration conf;
	
	public AbstractTypeMapper(Configuration _conf){
		typeMap = new java.util.Properties();
		this.conf = _conf;
	}
	
	public TypeInfo getType(String key){
		
		TypeInfo value = (TypeInfo)typeMap.get(key.toLowerCase());
		return value;
	}
	
	/**
	 * Define the _mapping between JDO type and SQL type
	 *
	 */
	abstract public void doMap();
	
    /**
     * set type information for key
     * setType
     * @param key
     * @param value
     */
	protected void setType(String key, TypeInfo value){
		typeMap.put(key, value);
	}
}

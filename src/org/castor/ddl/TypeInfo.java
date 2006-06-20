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

/**
 * Class that assoiziates SQL type and its attributes.
 * Created on Jun 4, 2006 - 10:31:12 AM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public class TypeInfo {
    
    /**
     * undefined length value in the configuration file
     */
    public static final int UNDEFINED_LENGTH = -1;
    
    /**
     * undefined decimal value in the configuration file
     */
    public static final int UNDEFINED_DECIMAL = -1;
    /** SQL type*/
    private final String _sqlName;

    /** SQL default length. Undefined value is -1 */
    private final int _defaultLength;

    /** if the type requires specific length. */
    private final boolean _isRequireLength;

    /** SQL default decimal. Undefined value is -1*/
    private final int _defaultDecimal;

    /** if the type requires specific decimal. */
    private final boolean _isRequireDecimal;

    /**
     * Construct a new TypeInfo instance with given SQL type name, SQL type name, 
     * length, requireLength, decimal, requireDecimal.
     * @param name
     * @param length
     * @param requireLength
     * @param decimal
     * @param requireDecimal
     */
    public TypeInfo(String name, int length, boolean requireLength, int decimal, boolean requireDecimal) {
		super();
		_sqlName = name;
		_defaultLength = length;
		_isRequireLength = requireLength;
		_defaultDecimal = decimal;
		_isRequireDecimal = requireDecimal;
	}
	
	/**
    * Construct a new TypeInfo instance with given SQL type name, SQL type name, 
    * length, requireLength, default decimal=-1, requireDecimal=false.
	 * @param name
	 * @param length
	 * @param requireLength
	 */
	public TypeInfo(String name, int length, boolean requireLength) {
		super();
		_sqlName = name;
		_defaultLength = length;
		_isRequireLength = requireLength;
		_defaultDecimal = -1;
		_isRequireDecimal = false;
	}

	/**
	    * Construct a new TypeInfo instance with given SQL type name, SQL type name, 
	    * default length=-1, requireLength=false, decimal=-1, requireDecimal=false.
		 * @param name
		 */
		public TypeInfo(String name) {
			super();
			_sqlName = name;
			_defaultLength = -1;
			_isRequireLength = false;
			_defaultDecimal = -1;
			_isRequireDecimal = false;
		}

	/**
	 * @return Returns the _defaultDecimal.
	 */
	public final int getDefaultDecimal() {
		return _defaultDecimal;
	}

	/**
	 * @return Returns the _defaultLength.
	 */
	public final int getDefaultLength() {
		return _defaultLength;
	}

	/**
	 * @return Returns the _isRequireDecimal.
	 */
	public final boolean isRequireDecimal() {
		return _isRequireDecimal;
	}

	/**
	 * @return Returns the _isRequireLength.
	 */
	public final boolean isRequireLength() {
		return _isRequireLength;
	}

	/**
	 * @return Returns the _sqlName.
	 */
	public final String getSqlName() {
		return _sqlName;
	}
    
//    public String getDDLString() throws GeneratorException{
//        String s = ""
//        
//    }
  
    
    
}

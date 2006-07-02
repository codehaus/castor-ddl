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

import java.util.Enumeration;
import java.util.Properties;

/**
 * this class handle the configuration for DDL generator
 * Created on Jun 4, 2006 - 10:27:35 AM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public class Configuration extends BaseConfiguration{
    /**
     * handle configuration
     */
    Properties conf;
    

	/**
	 * @throws GeneratorException 
	 * 
	 */
	public Configuration(String fileName) throws GeneratorException {
		super();
		conf = new Properties();
		try{
            addProperties(System.getProperties());
			conf.load(new java.io.FileInputStream(fileName));
            loadPredefineConfiguration();
			
		}catch(java.io.IOException ioe){
            ioe.printStackTrace();
			throw new GeneratorException(ioe.getMessage(), ioe);
		}
	}

    /**
     * get boolean value associated with key in the configuration files.
     * @param key
     * @return return value associated with key. If not exists, throw an exception
     * @throws WrongFormatException
     * @throws KeyNotFoundException
     */
    public boolean getBoolValue(String key) throws WrongFormatException, KeyNotFoundException{
        String value = this.getStringValue(key);

        if(value == null)
            throw new KeyNotFoundException("can not found key " + key);
        
        if(TRUE.equals(value))
            return true;
        
        if(FALSE.equals(value))
            return false;
        
        throw new WrongFormatException("require boolean (true/false), receive " + value + " for key=" + key);
    }
    
    /**
     * get boolean value associated with key in the configuration files.
     * @param key
     * @return return value associated with key. If not exists, throw an exception
     * @throws WrongFormatException
     * @throws KeyNotFoundException
     */
    public boolean getBoolValue(String key, boolean defaultValue){
        String value = null;
        try {
            value = this.getStringValue(key);
        } catch (KeyNotFoundException e) {
            return defaultValue;
        }

        if(value == null)
           return defaultValue;
        
        if(TRUE.equals(value))
            return true;
        
        if(FALSE.equals(value))
            return false;
        
        return defaultValue;
    }
    
	
    /**
     * get integer value associated with key in the configuration files.
     * @param key
     * @return return value associated with key. If not exists, throw an exception
     * @throws WrongFormatException
     * @throws KeyNotFoundException
     */
    public int getIntegerValue(String name)
    throws WrongFormatException, KeyNotFoundException{
        String value = conf.getProperty(name);
        try{
            return Integer.parseInt(value);
        }catch(NumberFormatException nfe){
            throw new WrongFormatException("require an integer, receive " + value);
        }
    }

    /**
     * Get property with given name as int value. If property is not available or can
     * not be interpreted as integer the given default int value will be returned. 
     * 
     * @param name Name of the property.
     * @param defaultValue  Default int value to return if property is not available or
     *                      can not be interpreted as integer.
     * @return The configured int property or the default int value if property is
     *         not available or can not be interpreted as integer.
     */
    public int getIntegerValue(final String name, final int defaultValue) {
        String value = conf.getProperty(name);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    /**
     * Get property with given name as Integer value. If property is not available or can
     * not be interpreted as integer null will be returned. 
     * 
     * @param name Name of the property.
     * @return The configured Integer property or null if property is not available or
     *         can not be interpreted as integer.
     */
    public Integer getInteger(final String name) {
        String value = conf.getProperty(name);
        if (value == null) { return null; }
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

	/**
	 * get String value associated with key in the configuration files.
	 * @param key
	 * @return return value associated with key. If not exists, throw an exception
	 * @throws KeyNotFoundException
	 */	
	public String getStringValue(String key) throws KeyNotFoundException{
		String value = (String)conf.get(key);
		if(value == null || "".equals(value)) 
			throw new KeyNotFoundException("Can not find value correspondence to " + key);
		return value;
	}
	
	/**
	 * get String value associated with key in the configuration files.
	 * @param key
	 * @param defaultValue
	 * @return return value associated with key. If not exists, return default value
	 */
	public String getStringValue(String key, String defaultValue){
		String value = (String)conf.get(key);
		if(value == null || "".equals(value)) 
			value = defaultValue;
		return value;		
	}
	/**
     * add properties (key, value) for configuration, the duplicated item will be overwrite
     * addProperties
     * @param props
	 */
	public void addProperties(Properties props){
        if(props != null) {            
            Object key, value;
            for(Enumeration e = props.keys(); e.hasMoreElements();) {
                key = e.nextElement();
                value = props.get(key);
                conf.put(key, value);
            }
        }
        loadPredefineConfiguration();
	}

    /**
     * add properties (key, value) for configuration, the duplicated item will be overwrite
     * addProperties
     * @param fileName a properties file
     */
	public void addProperties(String fileName) throws GeneratorException{
        Properties props = new Properties();
        try{
            props.load(new java.io.FileInputStream(fileName));
            
            this.addProperties(props);
        }catch(java.io.IOException ioe){
            ioe.printStackTrace();
            throw new GeneratorException(ioe.getMessage(), ioe);
        }
        loadPredefineConfiguration();
	}
    
    /**
     * set property value, this will overwrite the loaded value
     * @param key
     * @param value
     */
    public void setProperty(String key, String value) {
        conf.put(key, value);
        loadPredefineConfiguration();
    }

    protected void loadPredefineConfiguration() {
        /**field delimeter*/
        SQL_FIELD_DELIMETER = getStringValue(SQL_FIELD_DELIMETER_KEY, SQL_FIELD_DELIMETER);
        
        /**statement delimeter*/
        SQL_STAT_DELIMETER = getStringValue(SQL_STAT_DELIMETER_KEY, SQL_STAT_DELIMETER);
        
        /**line separator*/
        LINE_SEPARATOR = getStringValue(LINE_SEPARATOR_KEY, LINE_SEPARATOR);
        
        /**Line indent*/
        LINE_INDENT = getStringValue(LINE_INDENT_KEY, LINE_INDENT);
        
        String s = getStringValue(DDL_FORMAT_CASE_KEY, DDL_FORMAT_CASESENSITIVE_STRING);
        if(DDL_FORMAT_LOWERCASE_STRING.equalsIgnoreCase(s)) {
            DDL_FORMAT_CASE = DDL_FORMAT_LOWERCASE;
        }else if(DDL_FORMAT_UPPERCASE_STRING.equalsIgnoreCase(s)) {
            DDL_FORMAT_CASE = DDL_FORMAT_UPPERCASE;            
        }else {
            DDL_FORMAT_CASE = DDL_FORMAT_CASESENSITIVE;                        
        }
    }
}

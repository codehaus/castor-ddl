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
	 * 
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
			conf.load(new java.io.FileInputStream(fileName));
			
		}catch(java.io.IOException ioe){
			throw new GeneratorException(ioe.getMessage());
		}
	}

	/**
	 * get integer value associated with key in the configuration files.
	 * @param key
	 * @return return value associated with key. If not exists, throw an exception
	 * @throws WrongFormatException
	 * @throws KeyNotFoundException
	 */
	public int getIntegerValue(String key) throws WrongFormatException, KeyNotFoundException{
		String value = this.getStringValue(key);
		int ivalue = 0;
		try{
			ivalue = Integer.parseInt(value);
		}catch(NumberFormatException nfe){
			throw new WrongFormatException("require an integer, receive " + value);
		}
		return ivalue;
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
	 * get integer value associated with key in the  configuration file
	 * @param key
	 * @param defaultValue
	 * @return return value associated with key. If not exists, return default value
	 */
	public int getIntegerValue(String key, int defaultValue){
		int ivalue = defaultValue;
		try{
			ivalue = this.getIntegerValue(key);
		}catch(GeneratorException dge){
			//do not thing, return default value
		}
		return ivalue;
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
	}

    /**
     * add properties (key, value) for configuration, the duplicated item will be overwrite
     * addProperties
     * @param fileName
     */
	public void addProperties(String fileName) throws GeneratorException{
        Properties props = new Properties();
        try{
            props.load(new java.io.FileInputStream(fileName));
            
            this.addProperties(props);
        }catch(java.io.IOException ioe){
            throw new GeneratorException(ioe.getMessage());
        }
	}
    
    /**
     * set property value, this will overwrite the loaded value
     * @param key
     * @param value
     */
    public void setProperty(String key, String value) {
        conf.put(key, value);
    }
}

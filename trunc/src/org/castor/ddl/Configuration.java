/*
 * Copyright 2006 Le Duc Bao
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.castor.ddl;

import java.util.Enumeration;
import java.util.Properties;

/** 
 * Configuration handle the configuration for DDL generator inclidung
 * load configuration files, manage configuration values
 * 
 * @version Created on Jun 4, 2006 - 10:27:35 AM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public class Configuration extends BaseConfiguration {
    /** 
     * handle configuration
     */
    Properties _conf;

    /**
     * @param fileName
     *            file name
     * @throws GeneratorException
     *             generator error
     * 
     */
    public Configuration(final String fileName) throws GeneratorException {
        super();
        _conf = new Properties();
        try {
            addProperties(System.getProperties());
            _conf.load(new java.io.FileInputStream(fileName));
            loadPredefineConfiguration();

        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
            throw new GeneratorException(ioe.getMessage(), ioe);
        }
    }

    /**
     * get boolean value associated with key in the configuration files.
     * 
     * @param key
     *            key
     * @return return value associated with key. If not exists, throw an
     *         exception
     * @throws WrongFormatException
     *             format error
     * @throws KeyNotFoundException
     *             key error
     */
    public boolean getBoolValue(final String key) throws WrongFormatException,
            KeyNotFoundException {
        String value = this.getStringValue(key);

        if (value == null) {
            throw new KeyNotFoundException("can not found key " + key);
        }

        if (TRUE.equals(value)) {
            return true;
        }

        if (FALSE.equals(value)) {
            return false;
        }

        throw new WrongFormatException("require boolean (true/false), receive "
                + value + " for key=" + key);
    }

    /**
     * get boolean value associated with key in the configuration files.
     * 
     * @param key key
     * @param defaultValue default value
     * @return return value associated with key. If not exists, throw an
     *         exception
     */
    public boolean getBoolValue(final String key, final boolean defaultValue) {
        String value = null;
        try {
            value = this.getStringValue(key);
        } catch (KeyNotFoundException e) {
            return defaultValue;
        }

        if (value == null) {
            return defaultValue;
        }

        if (TRUE.equals(value)) {
            return true;
        }

        if (FALSE.equals(value)) {
            return false;
        }

        return defaultValue;
    }

    /**
     * Get property with given name as Integer value. If property is not
     * available or can not be interpreted as integer null will be returned.
     * 
     * @param name
     *            Name of the property.
     * @return The configured Integer property or null if property is not
     *         available or can not be interpreted as integer.
     */
    public Integer getInteger(final String name) {
        String value = _conf.getProperty(name);
        if (value == null) {
            return null;
        }
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    /**
     * get String value associated with key in the configuration files.
     * 
     * @param key key
     * @return return value associated with key. If not exists, throw an
     *         exception
     * @throws KeyNotFoundException key error
     */
    public String getStringValue(final String key) throws KeyNotFoundException {
        String value = (String) _conf.get(key);
        if (value == null || "".equals(value)) {
            throw new KeyNotFoundException(
                    "Can not find value correspondence to " + key);
        }
        return value;
    }

    /**
     * get String value associated with key in the configuration files.
     * 
     * @param key key
     * @param defaultValue default value
     * @return return value associated with key. If not exists, return default
     *         value
     */
    public String getStringValue(final String key, final String defaultValue) {
        String value = (String) _conf.get(key);
        if (value == null || "".equals(value)) {
            value = defaultValue;
        }
        return value;
    }

    /**
     * add properties (key, value) for configuration, the duplicated item will
     * be overwrite addProperties
     *  
     * @param props properties
     */
    public void addProperties(final Properties props) {
        if (props != null) {
            Object key, value;
            for (Enumeration e = props.keys(); e.hasMoreElements();) {
                key = e.nextElement();
                value = props.get(key);
                _conf.put(key, value);
            }
        }
        loadPredefineConfiguration();
    }

    /**
     * add properties (key, value) for configuration, the duplicated item will
     * be overwrite addProperties
     * 
     * @param fileName
     *            a properties file
     * @throws GeneratorException generator error
     */
    public void addProperties(final String fileName) throws GeneratorException {
        Properties props = new Properties();
        try {
            props.load(new java.io.FileInputStream(fileName));

            this.addProperties(props);
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
            throw new GeneratorException(ioe.getMessage(), ioe);
        }
        loadPredefineConfiguration();
    }

    /**
     * set property value, this will overwrite the loaded value
     * 
     * @param key key
     * @param value value
     */
    public void setProperty(final String key, final String value) {
        _conf.put(key, value);
        loadPredefineConfiguration();
    }

    /**
     * load pre-define configuraion
     *
     */
    protected void loadPredefineConfiguration() {
        /** field delimeter */
//        setSqlFieldDelimeter(getStringValue(SQL_FIELD_DELIMETER_KEY,
//                getSqlFieldDelimeter()));
        setSqlFieldDelimeter(",");

        /** statement delimeter */
//        setSqlStatDelimeter(getStringValue(SQL_STAT_DELIMETER_KEY,
//                getSqlStatDelimeter()));
        setSqlStatDelimeter(";");

        /** line separator */
        setLineSeparator(getStringValue(LINE_SEPARATOR_KEY, getLineSeparator()));

        /** Line indent */
        setLineIndent(getStringValue(LINE_INDENT_KEY, getLineIndent()));

        /** line separator */
        setSqlBlocSeparator(getStringValue(SQL_BLOC_SEPARATOR_KEY, getLineSeparator()));
        
        String s = getStringValue(DDL_FORMAT_CASE_KEY,
                DDL_FORMAT_CASESENSITIVE_STRING);

        if (DDL_FORMAT_LOWERCASE_STRING.equalsIgnoreCase(s)) {
            setDdlFormatCase(DDL_FORMAT_LOWERCASE);
        } else if (DDL_FORMAT_UPPERCASE_STRING.equalsIgnoreCase(s)) {
            setDdlFormatCase(DDL_FORMAT_UPPERCASE);
        } else {
            setDdlFormatCase(DDL_FORMAT_CASESENSITIVE);
        }
    }
}

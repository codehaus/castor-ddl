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

package org.castor.ddl.schemaobject;

import org.castor.ddl.GeneratorException;
import org.exolab.castor.mapping.xml.KeyGeneratorDef;
import org.exolab.castor.mapping.xml.Param;

/**
 * 
 * Created on Jun 24, 2006 - 2:04:24 AM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class HighLowKey implements KeyGenerator {

    /***name of key*/
    public String _name = "HIGH-LOW";

    /** alias*/
    private String _alias;
        
    /** The name of the special sequence table. */
    private String _table; 
        
    /** The name of the column which contains table names*/
    private String _keyColumn;
        
    /** The name of the column which is used to reserve primary key values*/
    private String _valueColumn;
        
    /** The number of new keys the key generator should grab from the sequence table at a time.*/
    private int _grabSize = 10;
    
    /** To use the same Connection for writing to the sequence table, 
     * values: "true"/"false". This is needed when working in EJB environment, 
     * though less efficient. Optional, default="false"*/
    private boolean _isSameConnection = false;
    
    /** To generate globally unique keys, values: "true"/"false". Optional, default="false"*/
    private boolean _isGlobal = false;
    
    /**
     * 
     * Constructor for HighLowKey
     * @param keyGenDef
     * @throws GeneratorException
     */
    public HighLowKey(KeyGeneratorDef keyGenDef) throws GeneratorException{
        String TABLE = "table";
        String KEY_COLUMN = "key-column";
        String VALUE_COLUMN = "value-column";
        String GRAB_SIZE = "grab-size";
        String SAME_CONNECTION = "same-connection";
        String GLOBAL = "global";
        
        _alias = keyGenDef.getAlias();
        _name = keyGenDef.getName();
        Param []params = keyGenDef.getParam();
        for(int i = 0; i < params.length; i++) {
            String pname = params[i].getName();
            if(pname == null)
                continue;
            if(TABLE.equalsIgnoreCase(pname)) {
                _table = params[i].getValue();
            } else if(KEY_COLUMN.equalsIgnoreCase(pname)) {
                _keyColumn = params[i].getValue();
            } else if(VALUE_COLUMN.equalsIgnoreCase(pname)) {
                _valueColumn = params[i].getValue();
            } else if(GRAB_SIZE.equalsIgnoreCase(pname)) {
                try {
                _grabSize = Integer.parseInt(pname);
                }catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    throw new GeneratorException("can not parse integer" + pname, nfe);
                }
            } else if(SAME_CONNECTION.equalsIgnoreCase(pname)) {
                _isSameConnection = Boolean.parseBoolean(pname);
            } else if(GLOBAL.equals(pname.toLowerCase())) {
                _isGlobal = Boolean.parseBoolean(pname);
            }            
        }                
    }

    /* (non-Javadoc)
     * @see org.castor.ddl.schemaobject.KeyGenerator#getHashKey()
     */
    public String getHashKey() {
        if(_alias == null)
            return _name;
        return _alias;
    }


    /**
     * 
     * @return Returns the alias.
     */
    public final String getAlias() {
        return _alias;
    }


    /**
     * Set the alias by _alias.
     * @param alias 
     */
    public final void setAlias(String alias) {
        _alias = alias;
    }


    /**
     * 
     * @return Returns the grabSize.
     */
    public final int getGrabSize() {
        return _grabSize;
    }


    /**
     * Set the grabSize by _grabSize.
     * @param grabSize 
     */
    public final void setGrabSize(int grabSize) {
        _grabSize = grabSize;
    }


    /**
     * 
     * @return Returns the isGlobal.
     */
    public final boolean isGlobal() {
        return _isGlobal;
    }


    /**
     * Set the isGlobal by _isGlobal.
     * @param isGlobal 
     */
    public final void setGlobal(boolean isGlobal) {
        _isGlobal = isGlobal;
    }


    /**
     * 
     * @return Returns the isSameConnection.
     */
    public final boolean isSameConnection() {
        return _isSameConnection;
    }


    /**
     * Set the isSameConnection by _isSameConnection.
     * @param isSameConnection 
     */
    public final void setSameConnection(boolean isSameConnection) {
        _isSameConnection = isSameConnection;
    }


    /**
     * 
     * @return Returns the keyColumn.
     */
    public final String getKeyColumn() {
        return _keyColumn;
    }


    /**
     * Set the keyColumn by _keyColumn.
     * @param keyColumn 
     */
    public final void setKeyColumn(String keyColumn) {
        _keyColumn = keyColumn;
    }


    /**
     * 
     * @return Returns the table.
     */
    public final String getTable() {
        return _table;
    }


    /**
     * Set the table by _table.
     * @param table 
     */
    public final void setTable(String table) {
        _table = table;
    }


    /**
     * 
     * @return Returns the valueColumn.
     */
    public final String getValueColumn() {
        return _valueColumn;
    }


    /**
     * Set the valueColumn by _valueColumn.
     * @param valueColumn 
     */
    public final void setValueColumn(String valueColumn) {
        _valueColumn = valueColumn;
    }

    /**
     * 
     * @return Returns the name.
     */
    public final String getName() {
        return _name;
    }

    /**
     * Set the name by _name.
     * @param name 
     */
    public final void setName(String name) {
        _name = name;
    }

    /**
     * Constructor for HighLowKey
     * @param name
     * @param alias
     */
    public HighLowKey(String name, String alias) {
        super();
        _name = name;
        _alias = alias;
    }
    
}

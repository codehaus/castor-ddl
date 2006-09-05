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

package org.castor.ddl.keygenerator;

import org.castor.ddl.GeneratorException;
import org.castor.ddl.schemaobject.KeyGenerator;
import org.castor.ddl.schemaobject.Table;
import org.exolab.castor.mapping.xml.KeyGeneratorDef;
import org.exolab.castor.mapping.xml.Param;

/**
 * HighLowKey is not used for DDL Generator
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public final class HighLowKey extends KeyGenerator {
    /** *name of key */
    private String _name = "HIGH-LOW";

    /** alias */
    private String _alias;

    /** The name of the special sequence table. */
    private String _tableName;

    /** The name of the column which contains table names */
    private String _keyColumn;

    /** The name of the column which is used to reserve primary key values */
    private String _valueColumn;

    /**
     * The number of new keys the key generator should grab from the sequence
     * table at a time.
     */
    private int _grabSize;

    /**
     * To use the same Connection for writing to the sequence table, values:
     * "true"/"false". This is needed when working in EJB environment, though
     * less efficient. Optional, default="false"
     */
    private boolean _isSameConnection = false;

    /**
     * To generate globally unique keys, values: "true"/"false". Optional,
     * default="false"
     */
    private boolean _isGlobal = false;

    /**
     * 
     * Constructor for HighLowKey
     * 
     * @param keyGenDef
     *            key generator definition
     * @throws GeneratorException
     *             generator error
     */
    public HighLowKey(final KeyGeneratorDef keyGenDef)
            throws GeneratorException {
        super();
        String tableKey = "table";
        String keyColumnKey = "key-column";
        String valueColumnKey = "value-column";
        String grabSizeKey = "grab-size";
        String sameConnectionKey = "same-connection";
        String globalKey = "global";

        _alias = keyGenDef.getAlias();
        _name = keyGenDef.getName();
        Param[] params = keyGenDef.getParam();
        for (int i = 0; i < params.length; i++) {
            String pname = params[i].getName();
            String pvalue = params[i].getValue();
            if (pname == null) {
                continue;
            }
            
            if (tableKey.equalsIgnoreCase(pvalue)) {
                _tableName = params[i].getValue();
            } else if (keyColumnKey.equalsIgnoreCase(pvalue)) {
                _keyColumn = params[i].getValue();
            } else if (valueColumnKey.equalsIgnoreCase(pvalue)) {
                _valueColumn = params[i].getValue();
            } else if (grabSizeKey.equalsIgnoreCase(pvalue)) {
                try {
                    _grabSize = Integer.parseInt(pvalue);
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    throw new GeneratorException("can not parse integer"
                            + pvalue, nfe);
                }
            } else if (sameConnectionKey.equalsIgnoreCase(pname)) {
                _isSameConnection = Boolean.valueOf(pname).booleanValue();
            } else if (globalKey.equals(pname.toLowerCase())) {
                _isGlobal = Boolean.valueOf(pname).booleanValue();
            }
        }
    }

    /** 
     * @see org.castor.ddl.schemaobject.KeyGenerator#getHashKey()
     * {@inheritDoc}
     */
    public String getHashKey() {
        if (_alias == null) {
            return _name;
        }
        return _alias;
    }

    /**
     * 
     * @return Returns the alias.
     */
    public String getAlias() {
        return _alias;
    }

    /**
     * Set the alias by _alias.
     * 
     * @param alias alias
     */
    public void setAlias(final String alias) {
        _alias = alias;
    }

    /**
     * 
     * @return Returns the grabSize.
     */
    public int getGrabSize() {
        return _grabSize;
    }

    /**
     * Set the grabSize by _grabSize.
     * 
     * @param grabSize grab size
     */
    public void setGrabSize(final int grabSize) {
        _grabSize = grabSize;
    }

    /**
     * 
     * @return Returns the isGlobal.
     */
    public boolean isGlobal() {
        return _isGlobal;
    }

    /**
     * Set the isGlobal by _isGlobal.
     * 
     * @param isGlobal is global
     */
    public void setGlobal(final boolean isGlobal) {
        _isGlobal = isGlobal;
    }

    /**
     * 
     * @return Returns the isSameConnection.
     */
    public boolean isSameConnection() {
        return _isSameConnection;
    }

    /**
     * Set the isSameConnection by _isSameConnection.
     * 
     * @param isSameConnection is same connection
     */
    public void setSameConnection(final boolean isSameConnection) {
        _isSameConnection = isSameConnection;
    }

    /**
     * 
     * @return Returns the keyColumn.
     */
    public String getKeyColumn() {
        return _keyColumn;
    }

    /**
     * Set the keyColumn by _keyColumn.
     * 
     * @param keyColumn key column
     */
    public void setKeyColumn(final String keyColumn) {
        _keyColumn = keyColumn;
    }

    /**
     * 
     * @return Returns the table.
     */
    public String getTableName() {
        return _tableName;
    }

    /**
     * Set the table by _tableName.
     * 
     * @param table table
     */
    public void setTableName(final String table) {
        _tableName = table;
    }

    /**
     * 
     * @return Returns the valueColumn.
     */
    public String getValueColumn() {
        return _valueColumn;
    }

    /**
     * Set the valueColumn by _valueColumn.
     * 
     * @param valueColumn value column
     */
    public void setValueColumn(final String valueColumn) {
        _valueColumn = valueColumn;
    }

    /**
     * 
     * @return Returns the name.
     */
    public String getName() {
        return _name;
    }

    /**
     * Set the name by _name.
     * 
     * @param name name
     */
    public void setName(final String name) {
        _name = name;
    }

    /** 
     * @see org.castor.ddl.schemaobject.KeyGenerator#toDDL()
     * {@inheritDoc}
     */
    public String toDDL() {
        return "";
    }

    /** 
     * @see org.castor.ddl.schemaobject.KeyGenerator#setTable
     * (org.castor.ddl.schemaobject.Table)
     * {@inheritDoc}
     */
    public void setTable(final Table table) {

    }

    /**
     * @see org.castor.ddl.schemaobject.KeyGenerator#getTable()
     * {@inheritDoc}
     */
    public Table getTable() {
        return null;
    }
}

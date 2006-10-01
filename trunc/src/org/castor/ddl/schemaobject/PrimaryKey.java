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

import java.util.Iterator;
import java.util.Vector;

/**
 * Primary key encapsulates information for create a primary key for
 * a table
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public class PrimaryKey extends AbstractSchemaObject {
    //--------------------------------------------------------------------------

    /** table */
    private Table _table = null;

    /** constraint name*/
    private String _name = null;

    /**primary key list*/
    private Vector _primaryKeyColumns = null;

    //--------------------------------------------------------------------------
    
    /**
     * Constructor for PrimaryKey
     */
    public PrimaryKey() {
        super();
        _primaryKeyColumns = new Vector();
    }

    //--------------------------------------------------------------------------

    /**
     * 
     * @return Returns the constraintName.
     */
    public final String getName() {
        return _name;
    }

    /**
     * Set the name by _name.
     * @param name of primary key
     */
    public final void setName(final String name) {
        _name = name;
    }

    /**
     * @return return number of primary key column 
     */
    public final int getPrimaryKeyColumnCount() {
        return _primaryKeyColumns.size();
    }
    
    /**
     * add a columnname to primary key list
     * @param columnname name of column
     */
    public final void addPrimaryKeyColumn(final String columnname) {
        _primaryKeyColumns.add(columnname);
    }

    /**
     * @param index index
     * @return a column name at index
     */
    public final String getPrimaryKeyColumn(final int index) {
        return (String) _primaryKeyColumns.get(index);
    }
    
    /**
     * @return Returns the primaryKeyLists.
     */
    public final Vector getPrimaryKeyColumns() {
        return _primaryKeyColumns;
    }

    /**
     * Set the primaryKeyLists by _primaryKeyColumns.
     * @param primaryKeyLists primary key list
     */
    public final void setPrimaryKeyColumns(final Vector primaryKeyLists) {
        _primaryKeyColumns = primaryKeyLists;
    }

    /**
     * 
     * @return Returns the table.
     */
    public final Table getTable() {
        return _table;
    }

    /**
     * Set the table by _table.
     * @param table table
     */
    public final void setTable(final Table table) {
        _table = table;
    }

    //--------------------------------------------------------------------------

    /**
     * @return return a string represents for create primary key a primary key ddl
     */
    public String toCreateDdl() {
        if (_primaryKeyColumns.size() == 0) { return ""; }
        StringBuffer buff = new StringBuffer(getConfiguration().getLineSeparator());
        buff.append(getConfiguration().getLineSeparator());

        buff.append("ALTER TABLE ").append(_table.getName());
        buff.append(" ADD PRIMARY KEY (");

        boolean isFirstField = true;
        for (Iterator i = _primaryKeyColumns.iterator(); i.hasNext();) {
            String columnName = (String) i.next();
                if (!isFirstField) {
                    buff.append(getConfiguration().getSqlFieldDelimeter());
                    buff.append(" ");
                }
                isFirstField = false;
                buff.append(columnName);
        }
        buff.append(")").append(getConfiguration().getSqlStatDelimeter());

        return buff.toString();
    }

    //--------------------------------------------------------------------------
}

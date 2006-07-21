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

/**
 * ForeignKey handle information for foreign key creation.
 * <pre>ALTER TABLE `prod_group` ADD CONSTRAINT `FK_prod_group_1` 
 * FOREIGN KEY `FK_prod_group_1` (`id`, `name`)
 * REFERENCES `category` (`id`, `name`)
 * </pre> 
 * <br/>Created on Jun 21, 2006 - 11:52:10 AM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class ForeignKey extends AbstractSchemaObject  {
    /** table */
    private Table _table = null;

    /** constraint name*/
    private String _constraintName = null;
    
    /** foreign key name*/
    private String _fkName = null;

    /** foreign key key list*/
    private String []_fkkeyList = null;

    /** reference table*/
    private String _referenceTableName = null;
    
    /** reference key key list*/
    private String []_referenceKeyList = null;

    /** relationship type */
    private int _relationshipType = ONE_ONE;
    
    /** one-one ralationship*/
    public static final int ONE_ONE = 0;
    
    /** one-many ralationship*/
    public static final int ONE_MANY = 1;
    
    /** many-many ralationship*/
    public static final int MANY_MANY = 2;

    /**
     * Constructor for ForeignKey
     */
    protected ForeignKey() {
        super();
    }

    /**
     * 
     * @return Returns the _constraintName.
     */
    public final String getConstraintName() {
        return _constraintName;
    }

    /**
     * Set the _constraintName by _constraintName.
     * @param constraintName constraint name
     */
    public final void setConstraintName(final String constraintName) {
        this._constraintName = constraintName;
    }

    /**
     * 
     * @return Returns the _fkkeyList.
     */
    public final String[] getFkkeyList() {
        return _fkkeyList;
    }

    /**
     * Set the _fkkeyList by _fkkeyList.
     * @param fkkeyList foreign key list
     */
    public final void setFkkeyList(final String[] fkkeyList) {
        this._fkkeyList = fkkeyList;
    }

    /**
     * 
     * @return Returns the _fkName.
     */
    public final String getFkName() {
        return _fkName;
    }

    /**
     * Set the _fkName by _fkName.
     * @param fkName foreign key name
     */
    public final void setFkName(final String fkName) {
        this._fkName = fkName;
    }

    /**
     * 
     * @return Returns the _referenceKeyList.
     */
    public final String[] getReferenceKeyList() {
        return _referenceKeyList;
    }

    /**
     * Set the _referenceKeyList by _referenceKeyList.
     * @param referenceKeyList reference key list
     */
    public final void setReferenceKeyList(final String[] referenceKeyList) {
        this._referenceKeyList = referenceKeyList;
    }

    /**
     * 
     * @return Returns the _referenceTableName.
     */
    public final String getReferenceTableName() {
        return _referenceTableName;
    }

    /**
     * Set the _referenceTableName by _referenceTableName.
     * @param referenceTableName reference table name
     */
    public final void setReferenceTableName(final String referenceTableName) {
        this._referenceTableName = referenceTableName;
    }

    /**
     * 
     * @return Returns the _relationshipType.
     */
    public final int getRelationshipType() {
        return _relationshipType;
    }

    /**
     * Set the _relationshipType by _relationshipType.
     * @param relationshipType relationship type
     */
    public final void setRelationshipType(final int relationshipType) {
        this._relationshipType = relationshipType;
    }

    /**
     * Create DDL for Foreign Key
     * @return ddl string
     */
    public String toDDL() {
        StringBuffer buff = new StringBuffer(getConf().getLineSeparator());
        buff.append(getConf().getLineSeparator());

        buff.append("ALTER TABLE ").append(_table.getName());

        // constraint
        buff.append(getConf().getLineSeparator()).append(
                getConf().getLineIndent());
        buff.append("ADD CONSTRAINT ").append(getConstraintName());

        // foreign key
        buff.append(getConf().getLineSeparator()).append(
                getConf().getLineIndent());
        buff.append("FOREIGN KEY ");
        buff.append(makeListofParams(getFkkeyList()));

        // references
        buff.append(getConf().getLineSeparator()).append(
                getConf().getLineIndent());
        buff.append("REFERENCES ").append(getReferenceTableName()).append(" ");
        buff.append(makeListofParams(getReferenceKeyList()));
        buff.append(getConf().getSqlStatDelimeter());
        return buff.toString();
    }

    /**
     * @param list  key list
     * @return  formated key list
     */
    protected String makeListofParams(final String[] list) {
        StringBuffer buff = new StringBuffer();
        boolean isFirstField = true;
        buff.append("(");
        for (int i = 0; i < list.length; i++) {
            if (!isFirstField) {
                buff.append(getConf().getSqlFieldDelimeter());
                buff.append(" ");
            }
            isFirstField = false;
            buff.append(list[i]);
        }
        buff.append(")");
        return buff.toString();
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

}

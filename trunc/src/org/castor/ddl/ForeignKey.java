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
 * this class handle information for foreign key creation.
 * <pre>ALTER TABLE `prod_group` ADD CONSTRAINT `FK_prod_group_1` FOREIGN KEY `FK_prod_group_1` (`id`, `name`)
 * REFERENCES `category` (`id`, `name`)
 * ON DELETE SET NULL
 * ON UPDATE CASCADE;</pre> 
 * Created on Jun 21, 2006 - 11:52:10 AM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class ForeignKey {
    /** table name*/
    private String tableName = null;
    
    /** constraint name*/
    private String constraintName = null;
    
    /** foreign key name*/
    private String fkName = null;

    /** foreign key key list*/
    private String []fkkeyList = null;

    /** reference table*/
    private String referenceTableName = null;
    
    /** reference key key list*/
    private String []referenceKeyList = null;

    /** relationship type */
    private int relationshipType = ONE_ONE;
    
    /** one-one ralationship*/
    public static final int ONE_ONE = 0;
    
    /** one-many ralationship*/
    public static final int ONE_MANY = 1;
    
    /** many-many ralationship*/
    public static final int MANY_MANY = 2;

    /**
     * Constructor for ForeignKey
     */
    public ForeignKey() {
        super();
    }

    /**
     * 
     * @return Returns the constraintName.
     */
    public final String getConstraintName() {
        return constraintName;
    }

    /**
     * Set the constraintName by constraintName.
     * @param constraintName 
     */
    public final void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    /**
     * 
     * @return Returns the fkkeyList.
     */
    public final String[] getFkkeyList() {
        return fkkeyList;
    }

    /**
     * Set the fkkeyList by fkkeyList.
     * @param fkkeyList 
     */
    public final void setFkkeyList(String[] fkkeyList) {
        this.fkkeyList = fkkeyList;
    }

    /**
     * 
     * @return Returns the fkName.
     */
    public final String getFkName() {
        return fkName;
    }

    /**
     * Set the fkName by fkName.
     * @param fkName 
     */
    public final void setFkName(String fkName) {
        this.fkName = fkName;
    }

    /**
     * 
     * @return Returns the referenceKeyList.
     */
    public final String[] getReferenceKeyList() {
        return referenceKeyList;
    }

    /**
     * Set the referenceKeyList by referenceKeyList.
     * @param referenceKeyList 
     */
    public final void setReferenceKeyList(String[] referenceKeyList) {
        this.referenceKeyList = referenceKeyList;
    }

    /**
     * 
     * @return Returns the referenceTableName.
     */
    public final String getReferenceTableName() {
        return referenceTableName;
    }

    /**
     * Set the referenceTableName by referenceTableName.
     * @param referenceTableName 
     */
    public final void setReferenceTableName(String referenceTableName) {
        this.referenceTableName = referenceTableName;
    }

    /**
     * 
     * @return Returns the tableName.
     */
    public final String getTableName() {
        return tableName;
    }

    /**
     * Set the tableName by tableName.
     * @param tableName 
     */
    public final void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 
     * @return Returns the relationshipType.
     */
    public final int getRelationshipType() {
        return relationshipType;
    }

    /**
     * Set the relationshipType by relationshipType.
     * @param relationshipType 
     */
    public final void setRelationshipType(int relationshipType) {
        this.relationshipType = relationshipType;
    }

}

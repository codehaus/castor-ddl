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

package org.castor.ddl.schemaobject;

/**
 * The SchemaFactory class handles the creation for various schema objects. It 
 * helps the AbstractGenerator to dynamically extract schema�s information for 
 * specific database. It should be overwritten this class if you have overwritten 
 * any schema object classs
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class SchemaFactory {

    /**
     * Constructor for SchemaFactory
     */
    public SchemaFactory() {
        super();
    }

    /**
     * create table object
     * 
     * @return table
     */
    public Table createTable() {
        return new Table();
    }

    /**
     * create field object
     * 
     * @return field
     */
    public Field createField() {
        return new Field();
    }

    /**
     * create Schema object
     * 
     * @return schema
     */
    public Schema createSchema() {
        return new Schema();
    }

    /**
     * create ForeignKey object
     * 
     * @return foreign key
     */
    public ForeignKey createForeignKey() {
        return new ForeignKey();
    }

    /**
     * create Index object
     * 
     * @return index
     */
    public Index createIndex() {
        return new Index();
    }

    /**
     * create primary key 
     * @return primary key
     */
    public PrimaryKey createPrimaryKey() {
        return new PrimaryKey();
    }
}

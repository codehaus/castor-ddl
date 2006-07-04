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
 * Factory provides a way to create schema objects
 * Created on Jul 4, 2006 - 3:01:20 PM
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
     * @return
     */
    public Table createTable() {
        return new Table();
    }
    /**
     * create field object
     * @return
     */
    public Field createField() {
        return new Field();
    }
}

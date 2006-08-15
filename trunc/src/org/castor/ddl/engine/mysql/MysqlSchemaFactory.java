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

package org.castor.ddl.engine.mysql;

import org.castor.ddl.schemaobject.Field;
import org.castor.ddl.schemaobject.ForeignKey;
import org.castor.ddl.schemaobject.Schema;
import org.castor.ddl.schemaobject.SchemaFactory;
import org.castor.ddl.schemaobject.Table;

/**
 * 
 * <br/>Created on Jul 4, 2006 - 3:06:14 PM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class MysqlSchemaFactory extends SchemaFactory {

    /**
     * Constructor for MysqlSchemaFactory
     */
    public MysqlSchemaFactory() {
        super();
    }

    /**
     * @see org.castor.ddl.schemaobject.SchemaFactory#createField()
     * {@inheritDoc}
     */
    public Field createField() {
        return new MysqlField();
    }

    /**
     * @see org.castor.ddl.schemaobject.SchemaFactory#createTable()
     * {@inheritDoc}
     */
    public Table createTable() {
        return new MysqlTable();
    }

    /**
     * @see org.castor.ddl.schemaobject.SchemaFactory#createForeignKey()
     * {@inheritDoc}
     */
    public ForeignKey createForeignKey() {
        return new MysqlForeignKey();
    }

    /**
     * @see org.castor.ddl.schemaobject.SchemaFactory#createSchema()
     * {@inheritDoc}
     */
    public Schema createSchema() {
        return new MysqlSchema();
    }

}

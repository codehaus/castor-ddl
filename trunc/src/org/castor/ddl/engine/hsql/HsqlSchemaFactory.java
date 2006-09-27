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

package org.castor.ddl.engine.hsql;

import org.castor.ddl.schemaobject.Field;
import org.castor.ddl.schemaobject.PrimaryKey;
import org.castor.ddl.schemaobject.SchemaFactory;
import org.castor.ddl.schemaobject.Table;

/**
 * Hsql Schema Factory.
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public final class HsqlSchemaFactory extends SchemaFactory {
    /**
     * Constructor for HsqlSchemaFactory
     */
    public HsqlSchemaFactory() {
        super();
    }

    /**
     * @see org.castor.ddl.schemaobject.SchemaFactory#createTable()
     * {@inheritDoc}
     */
    public Table createTable() {
        return new HsqlTable();
    }

    /**
     * @see org.castor.ddl.schemaobject.SchemaFactory#createField()
     * {@inheritDoc}
     */
    public Field createField() {
        return new HsqlField();
    }

    /**
     * @see org.castor.ddl.schemaobject.SchemaFactory#createPrimaryKey()
     * {@inheritDoc}
     */
    public PrimaryKey createPrimaryKey() {
        return new HsqlPrimaryKey();
    }
}

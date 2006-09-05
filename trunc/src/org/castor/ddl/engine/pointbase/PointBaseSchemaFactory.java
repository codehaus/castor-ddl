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

package org.castor.ddl.engine.pointbase;

import org.castor.ddl.schemaobject.Field;
import org.castor.ddl.schemaobject.PrimaryKey;
import org.castor.ddl.schemaobject.SchemaFactory;

/**
 * PointBase Schema Factory.
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public final class PointBaseSchemaFactory extends SchemaFactory {
    /**
     * Constructor for PointBaseSchemaFactory
     */
    public PointBaseSchemaFactory() {
        super();
    }

    /**
     * @see org.castor.ddl.schemaobject.SchemaFactory#createField()
     * {@inheritDoc}
     */
    public Field createField() {
        return new PointBaseField();
    }

    /**
     * @see org.castor.ddl.schemaobject.SchemaFactory#createPrimaryKey()
     * {@inheritDoc}
     */
    public PrimaryKey createPrimaryKey() {
        return new PointBasePrimaryKey();
    }
}

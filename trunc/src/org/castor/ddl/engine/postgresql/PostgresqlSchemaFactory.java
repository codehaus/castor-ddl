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

package org.castor.ddl.engine.postgresql;

import org.castor.ddl.GeneratorException;
import org.castor.ddl.schemaobject.Field;
import org.castor.ddl.schemaobject.KeyGenerator;
import org.castor.ddl.schemaobject.SchemaFactory;
import org.exolab.castor.mapping.xml.KeyGeneratorDef;

/**
 * Postgre SQL Schema Factory.
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class PostgresqlSchemaFactory extends SchemaFactory {

    /**
     * Constructor for MysqlSchemaFactory 
     */
    public PostgresqlSchemaFactory() {
        super();
    }

    /**
     * @see org.castor.ddl.schemaobject.SchemaFactory#createField()
     * {@inheritDoc}
     */
    public Field createField() {
        return new PostgresqlField();
    }

    /**
     * @see org.castor.ddl.schemaobject.SchemaFactory#createKeyGenerator
     * (org.exolab.castor.mapping.xml.KeyGeneratorDef)
     * {@inheritDoc}
     */
    public KeyGenerator createKeyGenerator(final KeyGeneratorDef kg) 
        throws GeneratorException {
        String name = kg.getName();

        if (KeyGenerator.SEQUENCE_KEY.equalsIgnoreCase(name)) {
            return new PostgresqlSequenceKey(kg);
        }

        return super.createKeyGenerator(kg);
    }

}

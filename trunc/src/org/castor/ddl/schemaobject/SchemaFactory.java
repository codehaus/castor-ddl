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

import org.castor.ddl.GeneratorException;
import org.exolab.castor.mapping.xml.KeyGeneratorDef;

/**
 * Factory provides a way to create schema objects 
 * <br/>Created on Jul 4, 2006 - 3:01:20 PM
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
     * create KeyGenerator object
     * @param kg key generator def
     * @return key generator
     * @throws GeneratorException generator error
     */
    public KeyGenerator createKeyGenerator(final KeyGeneratorDef kg)
            throws GeneratorException {
        String name = kg.getName();

        if (KeyGenerator.MAX_KEY.equalsIgnoreCase(name)) {
            return new MaxKey(kg);
        }

        if (KeyGenerator.HIGH_LOW_KEY.equalsIgnoreCase(name)) {
            return new HighLowKey(kg);
        }

        if (KeyGenerator.UUID_KEY.equalsIgnoreCase(name)) {
            return new UUIDKey(kg);
        }

        if (KeyGenerator.IDENTITY_KEY.equalsIgnoreCase(name)) {
            return new IdentityKey(kg);
        }

        if (KeyGenerator.SEQUENCE_KEY.equalsIgnoreCase(name)) {
            return new SequenceKey(kg);
        }

        throw new GeneratorException("can not create key generator, " 
                + "name = '" + name + "'");
    }

}

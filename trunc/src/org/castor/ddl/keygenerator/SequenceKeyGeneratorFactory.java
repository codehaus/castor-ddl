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
package org.castor.ddl.keygenerator;

import java.util.Iterator;

import org.castor.ddl.GeneratorException;
import org.castor.ddl.KeyGeneratorFactory;
import org.castor.ddl.schemaobject.Field;
import org.castor.ddl.schemaobject.KeyGenerator;
import org.castor.ddl.schemaobject.Table;
import org.exolab.castor.mapping.xml.KeyGeneratorDef;

/**
 * Abstract base class for SEQUENCE key generator factories.
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public abstract class SequenceKeyGeneratorFactory implements KeyGeneratorFactory {
    /**
     * {@inheritDoc}
     */
    public final String getAlgorithmName() { return SequenceKey.ALGORITHM_NAME; }
    
    /**
     * {@inheritDoc}
     */
    public final boolean hasMandatoryParameters() { return false; }

    /**
     * {@inheritDoc}
     */
    public final KeyGenerator createKeyGenerator() throws GeneratorException {
        return new SequenceKey(this);
    }
    
    /**
     * {@inheritDoc}
     */
    public final KeyGenerator createKeyGenerator(final KeyGeneratorDef definition)
    throws GeneratorException {
        return new SequenceKey(this, definition);
    }

    /**
     * Generate DDL for given SEQUENCE key generator.
     * 
     * @param key SEQUENCE key generator to generate DDL for.
     * @return DDL string.
     */
    public abstract String generateDDL(final KeyGenerator key);

    /**
     * Build a string containing names of all primary key columns separated by '_'.
     * 
     * @param table Table build primary key column names string for.
     * @return String containing names of all primary key columns separated by '_'.
     */
    protected final String toPrimaryKeyList(final Table table) {
        boolean hasPrimaryKey = false;
        StringBuffer sb = new StringBuffer();

        boolean isFirstField = true;
        for (Iterator i = table.getFields().iterator(); i.hasNext();) {
            Field field = (Field) i.next();
            if (field.isIdentity()) {
                hasPrimaryKey = true;
                if (!isFirstField) { sb.append("_"); }
                isFirstField = false;
                sb.append(field.getName());
            }
        }
        
        //have no primary key
        if (!hasPrimaryKey) { return ""; }
        return sb.toString();
    }
    
    /**
     * Build a string containing types of all primary key columns separated by '_'.
     * 
     * @param table Table build primary key column types string for.
     * @return String containing types of all primary key columns separated by '_'.
     */
    protected final String toPrimaryKeyTypeList(final Table table) {
        boolean hasPrimaryKey = false;
        StringBuffer sb = new StringBuffer();

        boolean isFirstField = true;
        for (Iterator i = table.getFields().iterator(); i.hasNext();) {
            Field field = (Field) i.next();
            if (field.isIdentity()) {
                hasPrimaryKey = true;
                if (!isFirstField) { sb.append("_"); }
                isFirstField = false;
                sb.append(field.getType().getSqlType());
            }
        }
        
        //have no primary key
        if (!hasPrimaryKey) { return ""; }
        return sb.toString();
    }
}

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

import org.castor.ddl.GeneratorException;
import org.castor.ddl.KeyGeneratorFactory;
import org.castor.ddl.schemaobject.KeyGenerator;
import org.exolab.castor.mapping.xml.KeyGeneratorDef;

/**
 * Factory class for HIGH-LOW key generators.
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public final class HighLowKeyGeneratorFactory implements KeyGeneratorFactory {
    /**
     * {@inheritDoc}
     */
    public String getAlgorithmName() { return HighLowKey.ALGORITHM_NAME; }
    
    /**
     * {@inheritDoc}
     */
    public boolean hasMandatoryParameters() { return true; }

    /**
     * {@inheritDoc}
     */
    public KeyGenerator createKeyGenerator() throws GeneratorException {
        throw new GeneratorException("Creation of default key generator not support");
    }
    
    /**
     * {@inheritDoc}
     */
    public KeyGenerator createKeyGenerator(final KeyGeneratorDef definition)
    throws GeneratorException {
        return new HighLowKey(definition);
    }
}

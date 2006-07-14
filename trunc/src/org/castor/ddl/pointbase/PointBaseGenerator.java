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

package org.castor.ddl.pointbase;

import org.castor.ddl.AbstractGenerator;
import org.castor.ddl.GeneratorException;
import org.castor.ddl.pointbase.schemaobject.PointBaseSchemaFactory;

/**
 * Generator for PointBase based on AbstractGenerator
 * <br/>Created on Jul 10, 2006 - 11:54:21 AM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class PointBaseGenerator extends AbstractGenerator {

    /**
     * Constructor for PointBaseGenerator
     * @param globConf global configuration
     * @param dbConf oracle configuration
     * @throws GeneratorException generator exception
     */
    public PointBaseGenerator(final String globConf, final String dbConf)
            throws GeneratorException {
        super(globConf, dbConf);
        setTypeMapper(new PointBaseTypeMapper(getConf()));
        setSchemaFactory(new PointBaseSchemaFactory());
    }

    /**
     * @see org.castor.ddl.AbstractGenerator#generateHeader()
     * {@inheritDoc}
     */
    public String generateHeader() {
        // TODO Auto-generated method stub
        return null;
    }

}

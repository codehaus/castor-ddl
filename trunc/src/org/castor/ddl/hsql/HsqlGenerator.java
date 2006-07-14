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

package org.castor.ddl.hsql;

import org.castor.ddl.AbstractGenerator;
import org.castor.ddl.GeneratorException;
import org.castor.ddl.hsql.schemaobject.HsqlSchemaFactory;

/**
 * Generator for HSQL based on AbstractGenerator
 * Created on Jul 10, 2006 - 11:54:21 AM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class HsqlGenerator extends AbstractGenerator {

    /**
     * Constructor for HsqlGenerator
     * @param globConf global configuration
     * @param dbConf oracle configuration
     * @throws GeneratorException generator exception
     */
    public HsqlGenerator(final String globConf, final String dbConf)
            throws GeneratorException {
        super(globConf, dbConf);
        setTypeMapper(new HsqlTypeMapper(getConf()));
        setSchemaFactory(new HsqlSchemaFactory());
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
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
package org.castor.ddl.derby;

import org.castor.ddl.AbstractGenerator;
import org.castor.ddl.Configuration;
import org.castor.ddl.GeneratorException;
import org.castor.ddl.TypeMapper;
import org.castor.ddl.derby.schemaobject.DerbySchemaFactory;

/**
 * generator for Derby Created on Jun 4, 2006 - 10:29:02 AM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public class DerbyGenerator extends AbstractGenerator {

    /**
     * 
     * Constructor for DerbyGenerator
     * 
     * @param globConf
     *            global configuration file
     * @param dbConf
     *            db configuration file
     * @throws GeneratorException
     *             generator error
     */
    public DerbyGenerator(final String globConf, final String dbConf)
            throws GeneratorException {
        super(globConf, dbConf);
        TypeMapper typeMapper = new DerbyTypeMapper(getConf());
        setTypeMapper(typeMapper);

        setSchemaFactory(new DerbySchemaFactory());
    }

    /**
     * @see org.castor.ddl.AbstractGenerator#generateHeader()
     * {@inheritDoc}
     */
    public String generateHeader() {
        StringBuffer buff = new StringBuffer("/*");
        buff.append(new java.util.Date());
        buff.append("\n");

        buff.append("Castor DDL Generator from mapping for Derby");
        buff.append(getConf().getStringValue(
                Configuration.HEADER_COMMENT_TEXT_KEY, ""));
        buff.append("*/");
        return buff.toString();
    }
    
}

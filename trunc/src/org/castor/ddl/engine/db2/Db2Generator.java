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

package org.castor.ddl.engine.db2;

import org.castor.ddl.AbstractGenerator;
import org.castor.ddl.Configuration;
import org.castor.ddl.GeneratorException;

/**
 * Generator for DB2 based on AbstractGenerator
 * <br/>Created on Jul 10, 2006 - 11:54:21 AM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class Db2Generator extends AbstractGenerator {
    public static final String NAME = "db2";
    
    public static final String FILEPATH = "conf/";
    
    public static final String FILENAME = NAME + ".properties";
    
    /**
     * @see org.castor.ddl.Generator#getEngineName()
     * {@inheritDoc}
     */
    public String getEngineName() { return NAME; }
    
    /**
     * @see org.castor.ddl.Generator#getEngineConfigurationFilePath()
     * {@inheritDoc}
     */
    public String getEngineConfigurationFilePath() { return FILEPATH; }

    /**
     * @see org.castor.ddl.Generator#getEngineConfigurationFileName()
     * {@inheritDoc}
     */
    public String getEngineConfigurationFileName() { return FILENAME; }
    
    public Db2Generator(final Configuration config) throws GeneratorException {
        super(config);

        setTypeMapper(new Db2TypeMapper(config));
        setSchemaFactory(new Db2SchemaFactory());
    }

    /**
     * Constructor for Db2Generator
     * @param globConf global configuration
     * @param dbConf oracle configuration
     * @throws GeneratorException generator exception
     */
    public Db2Generator(final String globConf, final String dbConf)
            throws GeneratorException { 
        super(globConf, dbConf);
        setTypeMapper(new Db2TypeMapper(getConf()));
        setSchemaFactory(new Db2SchemaFactory());
    }

    /**
     * @see org.castor.ddl.AbstractGenerator#generateHeader()
     * {@inheritDoc}
     */
    public String generateHeader() {
        StringBuffer buff = new StringBuffer("/* ");
        buff.append(getConf().getLineSeparator());
        buff.append(new java.util.Date());
        buff.append(getConf().getLineSeparator());

        buff.append("Castor DDL Generator from mapping for DB2");
        buff.append(getConf().getLineSeparator());
        buff.append(getConf().getStringValue(
                Configuration.HEADER_COMMENT_TEXT_KEY, ""));
        buff.append(getConf().getLineSeparator());
        buff.append("*/");
        return buff.toString();
    }

}

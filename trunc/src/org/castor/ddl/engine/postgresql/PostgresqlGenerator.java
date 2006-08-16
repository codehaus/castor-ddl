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

import org.castor.ddl.AbstractGenerator;
import org.castor.ddl.Configuration;
import org.castor.ddl.GeneratorException;

/**
 * Generator for PostgreSQL 
 * <br/>Created on Jun 4, 2006 - 10:29:02 AM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public class PostgresqlGenerator extends AbstractGenerator {
    public static final String NAME = "postgresql";
    
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
    
    public PostgresqlGenerator(final Configuration config) throws GeneratorException {
        super(config);

        setTypeMapper(new PostgresqlTypeMapper(config));
        setSchemaFactory(new PostgresqlSchemaFactory());
    }


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
    public PostgresqlGenerator(final String globConf, final String dbConf)
            throws GeneratorException {
        super(globConf, dbConf);
        setTypeMapper(new PostgresqlTypeMapper(getConf()));
        setSchemaFactory(new PostgresqlSchemaFactory());
    }

    /**
     * @see org.castor.ddl.AbstractGenerator#generateHeader()
     * {@inheritDoc}
     */
    public String generateHeader() {
        StringBuffer buff = new StringBuffer("-- ");
        buff.append(new java.util.Date());
        buff.append("\n");

        buff.append("-- Castor DDL Generator from mapping for PostgreSQL");
        buff.append(getConf().getStringValue(
                Configuration.HEADER_COMMENT_TEXT_KEY, ""));
        return buff.toString();
    }
    
}

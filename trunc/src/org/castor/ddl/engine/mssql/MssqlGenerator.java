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
package org.castor.ddl.engine.mssql;

import org.castor.ddl.AbstractGenerator;
import org.castor.ddl.BaseConfiguration;
import org.castor.ddl.Configuration;
import org.castor.ddl.MappingHelper;

/**
 * Generator for Microsoft SQL server.
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 * @version $Revision: 5951 $ $Date: 2006-04-25 16:09:10 -0600 (Tue, 25 Apr 2006) $
 */
public final class MssqlGenerator extends AbstractGenerator {
    //--------------------------------------------------------------------------

    /** Name of database engine. */
    public static final String NAME = "mssql";
    
    /** Path to specific configuration for generator. */
    public static final String ENGINE_CONFIG_PATH = "conf/";
    
    /** Filename of specific configuration for generator. */
    public static final String ENGINE_CONFIG_NAME = NAME + ".properties";
    
    //--------------------------------------------------------------------------
    
    /**
     * Constructor for MssqlGenerator.
     * 
     * @param configuration Configuration to use by the generator.
     */
    public MssqlGenerator(final Configuration configuration) {
        super(configuration);
    }

    /**
     * {@inheritDoc}
     */
    public void initialize() {
        setMappingHelper(new MappingHelper());
        setTypeMapper(new MssqlTypeMapper(getConfiguration()));
        setSchemaFactory(new MssqlSchemaFactory());
    }

    //--------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public String getEngineName() { return NAME; }
    
    /**
     * {@inheritDoc}
     */
    public String getEngineConfigPath() { return ENGINE_CONFIG_PATH; }

    /**
     * {@inheritDoc}
     */
    public String getEngineConfigName() { return ENGINE_CONFIG_NAME; }

    //--------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public String generateHeader() {
        StringBuffer buff = new StringBuffer("/* ");
        buff.append(getConfiguration().getLineSeparator());
        buff.append(new java.util.Date());
        buff.append(getConfiguration().getLineSeparator());

        buff.append("Castor DDL Generator from mapping for Microsoft SQL Server");
        buff.append(getConfiguration().getLineSeparator());
        buff.append(getConfiguration().getStringValue(
                BaseConfiguration.HEADER_COMMENT_TEXT_KEY, ""));
        buff.append(getConfiguration().getLineSeparator());
        buff.append("*/");
        
        return buff.toString();
    }
    
    //--------------------------------------------------------------------------
}

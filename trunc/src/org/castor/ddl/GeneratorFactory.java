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

package org.castor.ddl;

import java.io.IOException;
import java.util.Properties;

/**
 * This class handles the creation for various databse generators 
 * 
 * <br/>Created on Jun 7, 2006 - 3:22:36 PM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public final class GeneratorFactory {
    /**
     * Constructor for GeneratorFactory 
     */
    private GeneratorFactory() { }

    /**
     * create the DDL Generator for specific engine with the configurations if
     * the globalConf already defines specific db _conf, pass null for
     * specificConf
     * 
     * @param engine
     *            db engine
     * @param globalConf
     *            global configuration file
     * @param specificConf
     *            db configuration file
     * @return DDL generator ddl generator
     * @throws GeneratorException
     *             generator error
     */
    public static Generator createDDLGenerator(final String engine,
            final String globalConf, final String specificConf)
    throws GeneratorException {
        /** define the configuration file name of DDL Generator */
        String globConf = "conf/ddl.properties";
        String eng = engine;

        // verify the global configuration
        if (globalConf != null) {
            globConf = globalConf;
        }

        // load engine and specific database config
        Properties prop = new Properties();
        try {
            prop.load(new java.io.FileInputStream(globConf));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // adjust database engine
        if (eng == null) {
            eng = prop.getProperty(BaseConfiguration.DEFAULT_ENGINE_KEY);
        }

        // no db engine
        if (eng == null) {
            throw new GeneratorException("database engine is not defined");
        }

        // TODO take specific configuration from commandline into account
        
        Configuration config = new Configuration(globConf);
        GeneratorRegistry registry = new GeneratorRegistry(config);
        Generator generator = registry.getGenerator(eng.toLowerCase());
        if (generator != null) { return generator; }
        throw new GeneratorException("can not create DDL generator for " + eng);
    }
}

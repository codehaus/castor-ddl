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
     * @param engine db engine
     * @param globalConf global configuration file
     * @param specificConf db configuration file
     * @return DDL generator ddl generator
     * @throws GeneratorException generator error
     */
    public static Generator createDDLGenerator(final String engine,
            final String globalConf, final String specificConf)
    throws GeneratorException {
        Configuration config = new Configuration();

        // load default global configuration
        config.addProperties(Generator.GLOBAL_CONFIG_PATH + Generator.GLOBAL_CONFIG_NAME);
        // overload with global configuration given on commandline
        if (globalConf != null) { config.addProperties(globalConf); }
        
        GeneratorRegistry registry = new GeneratorRegistry(config);
        
        String eng = config.getStringValue(BaseConfiguration.DEFAULT_ENGINE_KEY, "");
        if (engine != null) { eng = engine; }
        Generator gen = registry.getGenerator(eng.toLowerCase());
        
        // load default configuration for specific database engine
        config.addProperties(gen.getEngineConfigPath() + gen.getEngineConfigName());
        // overload with specific configuration given on commandline
        if (specificConf != null) { config.addProperties(specificConf); }
        
        // initialize generator
        gen.initialize();
        
        return gen;
    }
}

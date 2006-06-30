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

package org.castor.ddl;

import java.io.IOException;
import java.util.Properties;

import org.castor.ddl.mysql.MySQLGenerator;

/** 
 * This class handles the creation for specific databse generator
 * Created on Jun 7, 2006 - 3:22:36 PM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class GeneratorFactory {
    
    /**
     * create an DDL generator for MySQL
     * @return
     * @throws GeneratorException
     */
    public static Generator createMySQLDDLGenerator(String globalConf, String mysqlConf) throws GeneratorException{
        /** define the configuration file name of DDL Generator*/
        Generator mySQLDDLGenerator = new MySQLGenerator(globalConf, mysqlConf);            
        return mySQLDDLGenerator;
    }
    
    /**
     * create the DDL Generator for specific engine with the configurations
     * if the globalConf already defines specific db conf, pass null for specificConf 
     * @param engine
     * @param globalConf
     * @param specificConf
     * @return DDL generator
     * @throws GeneratorException
     */
    public static Generator createDDLGenerator(String engine, String globalConf, String specificConf) throws GeneratorException{
        /** define the configuration file name of DDL Generator*/
        String globConf = "conf/config.properties";
        String eng = engine;
        
        //verify the global configuration
        if(globalConf != null) {
            globConf = globalConf;
        }
        
        // load engine and specific database config
        Properties prop = new Properties();
        try {
            prop.load(new java.io.FileInputStream(globConf));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //adjust database engine
        if(eng == null) {
            eng = prop.getProperty(BaseConfiguration.DATABASE_ENGINE_KEY);
        }
        
        //no db engine
        if(eng == null) {
            throw new GeneratorException("database engine is not defined");
        }
        
        //mysql ddl generator
        if(eng.toLowerCase().equals("mysql")) {
            //adjust specific db config
            String specConf = "conf/mysql.properties";
            if(specificConf != null) {
                specConf = specificConf;
            }
            
            return createMySQLDDLGenerator(globConf, specConf);            
        }
        
        //engine is not found
        throw new GeneratorException("can not create DDL generator for " + eng);
    }
}

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

import org.castor.ddl.mysql.MySQLGenerator;

/** 
 * This class handles the creation for specific databse generator
 * Created on Jun 7, 2006 - 3:22:36 PM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class GeneratorFactory {
    /** define the configuration file name of DDL Generator*/
    private static String _globalConf = "conf/config.properties";
    
    /** Handle the internal MySQL DDL generator */
    private static Generator mySQLDDLGenerator = null;
    /** define the configuration file name of MySQL*/
    private static String _mySQLConf = "conf/mysql.properties";
    
    /**
     * create an DDL generator for MySQL
     * @return
     * @throws GeneratorException
     */
    public static Generator getMySQLDDLGenerator() throws GeneratorException{
        if(mySQLDDLGenerator == null) {
            mySQLDDLGenerator = new MySQLGenerator(_globalConf, _mySQLConf);            
        }
        return mySQLDDLGenerator;
    }
    
}

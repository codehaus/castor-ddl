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

package ctf.org.castor.ddl;


import java.io.File;

import org.castor.ddl.Main;
/**
 * 
 * CTF test
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public final class CTF {

    /**castor home*/
//    public static final String CASTOR_HOME = new File("").getAbsolutePath();
    public static final String CASTOR_HOME = new File("").getAbsolutePath();
        
    /**
     * Constructor for CTF
     */
    protected CTF() {
        super();
    }

    /**
     * @param args arguments
     */
    public static void main(final String[] args) {
        String[] dbs = new String[]{"mysql", "oracle", "postgresql", "db2", "derby",
                "hsql", "mssql", "pointbase", "sapdb", "sybase"};
   
        String[] params = new String[10];

//Usage: -m mapping.xml [-c ddl.properties] [-d mysql.properties] [-e MySQL] [-o output.sql] [-h ]

        for (int i = 0; i < dbs.length; i++) {
            params[0] = "-m";
            params[1] = CASTOR_HOME + "\\test\\resources\\jdo\\mapping.xml";
            params[2] = "-c";
            params[3] = "conf\\ddl.properties";
            params[4] = "-d";
            params[5] = "conf\\" + dbs[i] + ".properties";
            params[6] = "-e";
            params[7] = dbs[i];
            params[8] = "-o";
            params[9] = "test\\ctf\\org\\castor\\ddl\\" + dbs[i] + ".sql";
            Main.main(params);
        }
    }
    
}

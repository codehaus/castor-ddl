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

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Properties;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.util.CommandLineOptions;

/**
 * Main Class
 * Created on Jun 4, 2006 - 10:28:41 AM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        CommandLineOptions allOptions = new CommandLineOptions();
        
        //-- input mapping file
        allOptions.addFlag("m", "mapping.xml", "input mapping file.");

        //-- configuration file
        allOptions.addFlag("c", "ddl.properties", "configuration file.", true);

        //-- specific database configuration file
        allOptions.addFlag("d", "mysql.properties", "specific database configuration file.", true);

        //-- database engine
        allOptions.addFlag("e", "MySQL", "database engine, for example MySQL, Oracle", true);

        //out put file
        allOptions.addFlag("o", "output.sql", "output ddl file", true);
        
        allOptions.addFlag("h", "", "Displays this help screen.", true);

        //-- Process the specified command line options
        Properties options = allOptions.getOptions(args);

        String  mappingName     = options.getProperty("m");
        String  ddlName         = options.getProperty("o");
        String  globalConfig    = options.getProperty("c");
        String  specificConfig  = options.getProperty("d");
        String  engine  = options.getProperty("e");
        
        if (options.getProperty("h") != null || mappingName == null) {
            PrintWriter pw = new PrintWriter(System.out, true);
            allOptions.printHelp(pw);
            pw.flush();
            System.exit(0);
        }
        
        // verify and adjust output file
        if(ddlName == null) {
            ddlName = mappingName.replaceAll(".xml", ".sql");
        }        
        
        System.out.println("mapping file: " + mappingName);
        System.out.println("ddl output file: "+ ddlName);
        System.out.println("global configuration file: "+ globalConfig);
        System.out.println("specific database configuration file: "+ specificConfig);
        System.out.println("database: "+ engine);
        
        // create generator and generate ddl
	    try {
	        
            Generator generator = GeneratorFactory.createDDLGenerator(engine, globalConfig, specificConfig);
            generator.setPrinter(new PrintStream(new FileOutputStream(ddlName)));
            Mapping mapping = new Mapping();
            mapping.loadMapping(mappingName);
            generator.generateDDL(mapping);            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
	}
}

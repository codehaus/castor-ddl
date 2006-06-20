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

import org.exolab.castor.mapping.Mapping;

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
        
	    try {
            Generator generator = GeneratorFactory.getMySQLDDLGenerator();
            generator.setPrinter(System.out);
            Mapping mapping = new Mapping();
            String abs = "test/utf/org/castor/ddl/data/2levels_reference.xml";
            mapping.loadMapping(abs);
            generator.generateDDL(mapping);            

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
	}

}

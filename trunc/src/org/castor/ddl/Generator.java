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
import java.io.PrintStream;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;

/**
 * Generator for generator 
 * 
 * <br/>
 * Created on Jun 4, 2006 - 10:25:34 AM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public interface Generator {

    /**
     * generate DDL script for a mapping file 
     * 
     * @param mappingFile
     *            mapping file name
     * @throws GeneratorException
     *             generation error
     * @throws MappingException
     *             mapping error
     * @throws IOException
     *             file reading error
     */
    void generateDDL(String mappingFile) throws GeneratorException,
            IOException, MappingException;

    /**
     * generate DDL for a mapping document
     * @param mappingDoc
     *            mapping document
     * @throws GeneratorException
     *             generation error
     */
    void generateDDL(Mapping mappingDoc) throws GeneratorException;

    /**
     * 
     * @param mappingDoc
     *            mapping document
     */
    void setMapping(Mapping mappingDoc);

    /**
     * 
     * @param printer
     *            print stream for output
     */

    void setPrinter(PrintStream printer);

}

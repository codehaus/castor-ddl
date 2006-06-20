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
import java.util.HashMap;
import java.util.Map;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;

/**
 * this class is the base class for various DDL generator for specific DB
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 * 
 */
public abstract class AbstractGenerator implements Generator{

    /** hold all configurations (key, value) */
    protected Configuration _conf;

    /** hold the writer for output */
    protected java.io.PrintStream _printer;

    /** handle the _mapping document*/
    protected Mapping _mapping;

    /** handle the typemapper*/
    protected TypeMapper _typeMapper;

    /** handle the MappingHelper */
    protected MappingHelper _mappingHelper;
    
    /** handle all resolving tables*/
    protected Map _resolveTable;
    
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    
    /**
     * Constructor for AbstractGenerator
     */
    public AbstractGenerator() {
        super();
        _resolveTable = new HashMap();
        _mappingHelper = new MappingHelper();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ddl.mapper.DDLGenerator#generateDDL()
     */
    public void generateDDL(String mappingFile) throws GeneratorException, IOException, MappingException {
        _mapping = new Mapping();
        _mapping.loadMapping(mappingFile);
        
        _mappingHelper.setMapping(_mapping);
        _mappingHelper.setTypeMapper(_typeMapper);
        
        _printer.println(generateSchemaDDL());
        _printer.println(generateTableDDL());
        _printer.println(generateResolvingTable());
        _printer.println(generateConstraint());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ddl.mapper.DDLGenerator#generateDDL()
     */
    public void generateDDL(Mapping mappingDoc) throws GeneratorException {
        _mapping = mappingDoc;
        _mappingHelper.setMapping(mappingDoc);
        _mappingHelper.setTypeMapper(_typeMapper);
        
        _printer.println(generateSchemaDDL());
        _printer.println(generateTableDDL());
        _printer.println(generateResolvingTable());
        _printer.println(generateConstraint());
    }

    /**
     * generate header, pre-create statement, setting up for DDL script
     * generateHeader
     * @return TODO
     * @throws GeneratorException
     */
    public String generateSchemaDDL() throws GeneratorException{
        String s = "";
        s += "# " + new java.util.Date() + "\n";
        s += "# Castor DDL Generator from _mapping\n";
        s += "# \n";
        return s;
    }

    /**
     * generate create statement for DDL script
     * generateBody
     * @return TODO
     * @throws GeneratorException
     */
    public String generateTableDDL() throws GeneratorException{
        return "";
    }

    /**
     * generate post-create statement, cleaning up for DDL scritp
     * generateFooter
     * @return TODO
     * @throws GeneratorException
     */
    public String generateResolvingTable() throws GeneratorException{
        return "";
    }

    /**
     * generate constrains statement, cleaning up for DDL scritp
     * generateFooter
     * @return TODO
     * @throws GeneratorException
     */
    public String generateConstraint() throws GeneratorException{
        return "";
    }
    
    /**
     * @return Returns the conf.
     */
    public Configuration getConf() {
        return _conf;
    }

    /**
     * @param conf
     * The conf to set.
     */
    public void setConf(Configuration conf) {
        this._conf = conf;
    }

    /**
     * @param _mapping
     * The _mapping to set.
     */
    public void setMapping(Mapping mapping) {
        this._mapping = mapping;
        _mappingHelper.setMapping(_mapping);
    }

    /**
     * set Writer
     * @param printer
     */
    public final void setPrinter(PrintStream printer) {
        this._printer = printer;
    }

    /**
     * 
     * @return Returns the _mapping.
     */
    public final Mapping getMapping() {
        return _mapping;
    }

    /**
     * 
     * @return Returns the writer.
     */
    public final java.io.PrintStream getPrinter() {
        return _printer;
    }

    /**
     * Set the typeMapper by _typeMapper.
     * @param typeMapper 
     */
    public final void setTypeMapper(TypeMapper typeMapper) {
        _typeMapper = typeMapper;
        _mappingHelper.setTypeMapper(_typeMapper);
    }

}

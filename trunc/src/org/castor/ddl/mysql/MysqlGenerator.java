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
package org.castor.ddl.mysql;

import org.castor.ddl.AbstractGenerator;
import org.castor.ddl.Configuration;
import org.castor.ddl.GeneratorException;
import org.castor.ddl.TypeMapper;
import org.castor.ddl.mysql.schemaobject.MysqlSchemaFactory;

/**
 * generator for MySQL Created on Jun 4, 2006 - 10:29:02 AM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public class MysqlGenerator extends AbstractGenerator {

    /**
     * 
     * Constructor for MysqlGenerator
     * 
     * @param globConf
     *            global configuration file
     * @param dbConf
     *            db configuration file
     * @throws GeneratorException
     *             generator error
     */
    public MysqlGenerator(final String globConf, final String dbConf)
            throws GeneratorException {
        super(globConf, dbConf);
        TypeMapper typeMapper = new MysqlTypeMapper(getConf());
        setTypeMapper(typeMapper);

        setSchemaFactory(new MysqlSchemaFactory());
    }

    /**
     * @see org.castor.ddl.AbstractGenerator#generateHeader()
     * {@inheritDoc}
     */
    public String generateHeader() {
        StringBuffer buff = new StringBuffer("# ");
        buff.append(new java.util.Date());
        buff.append("\n");

        buff.append("# Castor DDL Generator from _mapping");
        buff.append(getConf().getLineSeparator()).append(
                getConf().getLineSeparator());
        buff.append(getConf().getStringValue(
                Configuration.HEADER_COMMENT_TEXT_KEY, ""));
        return buff.toString();
    }
}
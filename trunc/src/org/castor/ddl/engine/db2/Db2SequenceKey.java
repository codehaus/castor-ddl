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

package org.castor.ddl.engine.db2;

import java.text.MessageFormat;

import org.castor.ddl.BaseConfiguration;
import org.castor.ddl.GeneratorException;
import org.castor.ddl.keygenerator.SequenceKey;
import org.exolab.castor.mapping.xml.KeyGeneratorDef;

/**
 * Db2 Sequence Key generator.
 * <br> Sequence creation in DB2 uses a dedicated syntax. This implementation lets 
 * users to define the DDL for sequence creation in the db2.properties. It provide 
 * five pre-defined keywords for a template:
 *    <li>&lt;sequence_name&gt;: Usually one sequence is used 
 *    for one table, so in general you have to define one key generator per table. 
 *    But if you use some naming pattern for sequences, you can use one key generator 
 *    for all tables. For example, if you always obtain sequence name by adding "_seq" 
 *    to the name of the correspondent table, you can set "sequence" parameter of the 
 *    key generator to "{0}_seq" (the default value). In this case the key generator 
 *    will use sequence "a_seq" for table "a", "b_seq" for table "b", etc. Castor 
 *    also allows for inserting the primary key into the sequence name as well. 
 *    This is accomplished by including the {1} tag into the "sequence" parameter. 
 *    Example: "{0}_{1}_seq"
 *    <li>&lt;trigger_name&gt;: is the sequence name prefixed by TRG_. If the sequence
 *    name contains SEQ_, it will be omitted. 
 *    <li>&lt;table_name&gt;: name of table which uses this sequence. Note that one 
 *    sequence key may be used in many class/table. 
 *    <li>&lt;pk_name&gt;: primary key list
 *    <li>&lt;pk_type&gt;:  primary key type          
 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public final class Db2SequenceKey extends SequenceKey {

    /**
     * Constructor for Db2SequenceKey
     * @param keyGenDef key generator definition
     * @throws GeneratorException exception
     */
    public Db2SequenceKey(final KeyGeneratorDef keyGenDef) throws GeneratorException {
        super(keyGenDef);
    }

    /**
     * @see org.castor.ddl.keygenerator.SequenceKey#toDDL()
     * {@inheritDoc}
     */
    public String toDDL() {
        StringBuffer buff = new StringBuffer();
        String tableName = getTable().getName();
        String pkList = toPrimaryKeyList();
        String sequenceName = MessageFormat.format(getSequence(), 
                new String[]{tableName, pkList});
        
        buff.append(getConf().getLineSeparator()).append(getConf().getLineSeparator());
        buff.append("CREATE SEQUENCE AS INTEGER ").append(sequenceName);
        buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
        buff.append("START WITH 1 INCREMENT BY 1");
        buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
        buff.append("MAXVALUE ").append(Integer.MAX_VALUE);
        buff.append(getConf().getSqlStatDelimeter());

        if (isTrigger()) {
            //refer to http://publib.boulder.ibm.com/infocenter/db2luw/v8/index.
            // jsp?topic=/com.ibm.db2.udb.doc/admin/r0000931.htm
            String pkTypeList = toPrimaryKeyTypeList();
            String triggerName = null;
            if (sequenceName.matches(".*SEQ.*")) {
                triggerName = sequenceName.replaceAll("SEQ", "TRG");
            } else {
                triggerName = "TRG" + sequenceName;
            }

            String triggerTemp = getConf().getStringValue(
                    BaseConfiguration.TRIGGER_TEMPLATE, "");
    
            triggerTemp = triggerTemp.replaceAll("<trigger_name>", triggerName);
            triggerTemp = triggerTemp.replaceAll("<sequence_name>", sequenceName);
            triggerTemp = triggerTemp.replaceAll("<table_name>", tableName);            
            triggerTemp = triggerTemp.replaceAll("<pk_name>", pkList);            
            triggerTemp = triggerTemp.replaceAll("<pk_type>", pkTypeList);            
            buff.append(getConf().getLineSeparator());
            buff.append(getConf().getLineSeparator());
            buff.append(triggerTemp);
        }
        
        return buff.toString();
    }

}

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

package org.castor.ddl.hsql.schemaobject;

import java.text.MessageFormat;

import org.castor.ddl.Configuration;
import org.castor.ddl.GeneratorException;
import org.castor.ddl.KeyGenNotSupportException;
import org.castor.ddl.keygenerator.SequenceKey;
import org.exolab.castor.mapping.xml.KeyGeneratorDef;

/**
 * Hsql sequence key
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class HsqlSequenceKey extends SequenceKey {

    /**
     * Constructor for HsqlSequenceKey
     * @param keyGenDef key generator definition
     * @throws GeneratorException exception
     */
    public HsqlSequenceKey(final KeyGeneratorDef keyGenDef) 
        throws GeneratorException {
        super(keyGenDef);
    }

    /**
     * @see org.castor.ddl.keygenerator.SequenceKey#toDDL()
     * {@inheritDoc}
     * REATE SEQUENCE <sequencename> [AS {INTEGER | BIGINT}] 
     * [START WITH <startvalue>] [INCREMENT BY <incrementvalue>];
     */
    public String toDDL() throws KeyGenNotSupportException {
        StringBuffer buff = new StringBuffer();
        String tableName = getTable().getName();
        String pkList = toPrimaryKeyList();
        String sequenceName = MessageFormat.format(getSequence(), 
                new String[]{tableName, pkList});
        
        buff.append(getConf().getLineSeparator()).append(getConf().getLineSeparator());
        buff.append("CREATE SEQUENCE ").append(sequenceName).append(" AS INTEGER");
        buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
        buff.append("START WITH 1 INCREMENT BY 1");
        buff.append(getConf().getSqlStatDelimeter());

        if (isTrigger()) {
            String pkTypeList = toPrimaryKeyTypeList();
            String triggerName = null;
            if (sequenceName.matches(".*SEQ.*")) {
                triggerName = sequenceName.replaceAll("SEQ", "TRG");
            } else {
                triggerName = "TRG" + sequenceName;
            }

            String triggerTemp = getConf().getStringValue(Configuration.TRIGGER_TEMPLATE, 
            "");
    
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

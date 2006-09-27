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
package org.castor.ddl.engine.oracle;

import java.text.MessageFormat;

import org.castor.ddl.BaseConfiguration;
import org.castor.ddl.Configuration;
import org.castor.ddl.keygenerator.SequenceKey;
import org.castor.ddl.keygenerator.SequenceKeyGeneratorFactory;
import org.castor.ddl.schemaobject.KeyGenerator;

/**
 * Factory class for SEQUENCE key generators for ORACLE.
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public final class OracleSequenceKeyGenerator extends SequenceKeyGeneratorFactory {
    /**
     * {@inheritDoc}
     */
    public String generateDDL(final KeyGenerator key) {
        SequenceKey sequenceKey = (SequenceKey) key;
        StringBuffer buff = new StringBuffer();
        Configuration conf = sequenceKey.getConf();
        
        String tableName = sequenceKey.getTable().getName();
        String pkList = toPrimaryKeyList(key.getTable());
        String pkTypeList = toPrimaryKeyTypeList(key.getTable());
        String sequenceName = MessageFormat.format(sequenceKey.getSequence(), 
                new String[]{tableName, pkList});
        
        buff.append(conf.getLineSeparator()).append(conf.getLineSeparator());
        buff.append("CREATE SEQUENCE ").append(sequenceName);
        buff.append(conf.getLineSeparator()).append(conf.getLineIndent());
        buff.append("MAXVALUE ").append(Integer.MAX_VALUE);
        buff.append(conf.getLineSeparator()).append(conf.getLineIndent());
        buff.append("INCREMENT BY 1 START WITH 1");
        buff.append(conf.getSqlStatDelimeter());

        if (sequenceKey.isTrigger()) {
            String triggerName = null;
            if (sequenceName.matches(".*SEQ.*")) {
                triggerName = sequenceName.replaceAll("SEQ", "TRG");
            } else {
                triggerName = "TRG" + sequenceName;
            }
            String triggerTemp = conf.getStringValue(
                    BaseConfiguration.TRIGGER_TEMPLATE, "");
    
            triggerTemp = triggerTemp.replaceAll("<trigger_name>", triggerName);
            triggerTemp = triggerTemp.replaceAll("<sequence_name>", sequenceName);
            triggerTemp = triggerTemp.replaceAll("<table_name>", tableName);            
            triggerTemp = triggerTemp.replaceAll("<pk_name>", pkList);            
            triggerTemp = triggerTemp.replaceAll("<pk_type>", pkTypeList);            
            buff.append(conf.getLineSeparator());
            buff.append(conf.getLineSeparator());
            buff.append(triggerTemp);
        }
        
        return buff.toString();
    }
}

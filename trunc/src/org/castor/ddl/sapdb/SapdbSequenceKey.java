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

package org.castor.ddl.sapdb;

import java.text.MessageFormat;

import org.castor.ddl.Configuration;
import org.castor.ddl.GeneratorException;
import org.castor.ddl.keygenerator.SequenceKey;
import org.exolab.castor.mapping.xml.KeyGeneratorDef;

/**
 * SapdbSequenceKey
 * <br/>Created on Jul 11, 2006 - 2:37:37 PM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class SapdbSequenceKey extends SequenceKey {

    /**
     * Constructor for SapdbSequenceKey
     * @param keyGenDef key generator definition
     * @throws GeneratorException exception
     */
    public SapdbSequenceKey(final KeyGeneratorDef keyGenDef)
            throws GeneratorException {
        super(keyGenDef);
    }

    /**<pre>
     * <create_sequence_statement> ::= CREATE SEQUENCE [<owner>.]<sequence_name>
     *  [INCREMENT BY <integer>] [START WITH <integer>]
     *  [MAXVALUE <integer> | NOMAXVALUE] [MINVALUE <integer> | NOMINVALUE]
     *  [CYCLE | NOCYCLE]
     *  [CACHE <unsigned_integer> | NOCACHE]
     *  [ORDER | NOORDER]
     *  
     *  CREATE SEQUENCE SEQ_PCV_LIEFERANT
     *  INCREMENT BY 1 START WITH 1
     *  MAXVALUE 2147483647
     *  
     *  <create_trigger_statement> ::= CREATE TRIGGER <trigger_name> FOR <table_name>
     *  AFTER <trigger_event,..> EXECUTE (<routine>) [WHENEVER <search_condition> ]
     *  <trigger_event> :: INSERT | UPDATE [(<column_list>)] | DELETE
     *  <column_list> ::= <column_name> | <column_list>,<column_name>     
     *  
     * for trigger
     * CREATE TRIGGER TRG_PCV_LIEFERANT FOR <tablename>
     * AFTER INSERT, UPDATE EXECUTE (
     * TRY
     * FOR EACH ROW
     * DECLARE
     *    iCounter TAB_PCV_LIEFERANT.PCLI_ID%TYPE;
     *    cannot_change_counter EXCEPTION;
     * BEGIN
     *     IF INSERTING THEN
     *         Select <seq_name>.NEXTVAL INTO iCounter FROM Dual;
     *         :new.<id> := iCounter;
     *     END IF;
     *     
     *     IF UPDATING THEN
     *          IF NOT (:new.PCLI_ID = :old.PCLI_ID) THEN
     *              RAISE cannot_change_counter;
     *          END IF;
     *     END IF;
     *     
     * CATCH
     *          raise_application_error(-20000, 'Cannot Change Counter Value');
     * )
     * </pre>
     * CREATE TRIGGER [trigger-name] FOR [table-name]
     *  AFTER INSERT EXECUTE (
     *  VAR iCounter [pk_type]
     *  TRY
     *         Select <seq_name>.NEXTVAL INTO iCounter FROM Dual;
     *         :new.<id> := iCounter;
     *  CATCH
     *  
     *  )
     *  
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
        buff.append("CREATE SEQUENCE ").append(sequenceName);
        buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
        buff.append("INCREMENT BY 1 START WITH 1");
        buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
        buff.append("MAXVALUE ").append(Integer.MAX_VALUE);
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

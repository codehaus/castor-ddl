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

package org.castor.ddl.oracle.schemaobject;

import java.text.MessageFormat;

import org.castor.ddl.GeneratorException;
import org.castor.ddl.schemaobject.SequenceKey;
import org.exolab.castor.mapping.xml.KeyGeneratorDef;

/**
 * 
 * Created on Jul 5, 2006 - 2:54:35 PM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class OracleSequenceKey extends SequenceKey {

    /**
     * Constructor for OracleSequenceKey
     * @param keyGenDef key generator definition
     * @throws GeneratorException generator error
     */
    protected OracleSequenceKey(final KeyGeneratorDef keyGenDef)
            throws GeneratorException {
        super(keyGenDef);
    }

    /**
     * <pre>
     * CREATE TABLE TAB_PCV_LIEFERANT (
     *     PCLI_ID NUMERIC(12, 13) NOT NULL,
     *     PCLI_NAME CHAR(16)
     * );
     * 
     * CREATE SEQUENCE SEQ_PCV_LIEFERANT
     * MAXVALUE 2147483647
     * INCREMENT BY 1 START WITH 1
     * 
     * for trigger
     * CREATE TRIGGER TRG_PCV_LIEFERANT
     * BEFORE INSERT OR UPDATE ON <tablename>
     * FOR EACH ROW
     * DECLARE
     *    iCounter <tablename>.<id>%TYPE;
     *    cannot_change_counter EXCEPTION;
     * BEGIN
     *     IF INSERTING THEN
     *         Select <seq_name>.NEXTVAL INTO iCounter FROM Dual;
     *         :new.<id> := iCounter;
     *     END IF;
     *     
     *     IF UPDATING THEN
     *          IF NOT (:new.<id> = :old.<id>) THEN
     *              RAISE cannot_change_counter;
     *          END IF;
     *     END IF;
     *     
     * EXCEPTION
     *      WHEN cannot_change_counter THEN
     *          raise_application_error(-20000, 'Cannot Change Counter Value');
     *      END;
     * </pre>
     * @see org.castor.ddl.schemaobject.SequenceKey#toDDL()
     * {@inheritDoc}
     */
    
    public String toDDL() {
        StringBuffer buff = new StringBuffer();
        String tableName = getTable().getName();
        String pk = toPrimaryKeyList();
        String sequence = MessageFormat.format(getSequence(), 
                new String[]{tableName, pk});
        
        buff.append(getConf().getLineSeparator()).append(getConf().getLineSeparator());
        buff.append("CREATE SEQUENCE ").append(sequence);
        buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
        buff.append("MAXVALUE ").append(Integer.MAX_VALUE);
        buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
        buff.append("INCREMENT BY 1 START WITH 1");
        buff.append(getConf().getSqlStatDelimeter());

        if (isTrigger()) {
            String trigger = null;
            if (sequence.matches(".*SEQ.*")) {
                trigger = sequence.replaceAll("SEQ", "TRG");
            } else {
                trigger = "TRG" + sequence;
            }
                
            buff.append(getConf().getLineSeparator());
            buff.append(getConf().getLineSeparator());
            buff.append("CREATE TRIGGER ").append(trigger);
            
            buff.append(getConf().getLineSeparator());
            buff.append("BEFORE INSERT OR UPDATE ON ").append(tableName);
            
            buff.append(getConf().getLineSeparator());
            buff.append("FOR EACH ROW");
            
            buff.append(getConf().getLineSeparator());
            buff.append("DECLARE");
            
            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
            buff.append("iCounter ").append(tableName).append('.');
            buff.append(pk).append("%TYPE;");
            
            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
            buff.append("cannot_change_counter EXCEPTION;");
            
            buff.append(getConf().getLineSeparator());
            buff.append("BEGIN");
            
            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
            buff.append("IF INSERTING THEN");
            
            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
            buff.append(getConf().getLineIndent());
            buff.append("Select ").append(sequence).
                append(".NEXTVAL INTO iCounter FROM Dual;");
            
            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
            buff.append(getConf().getLineIndent());
            buff.append(":new.").append(pk).append(" := iCounter;");
            
            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
            buff.append("END IF;");

            buff.append(getConf().getLineSeparator());
            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
            buff.append("IF UPDATING THEN");

            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
            buff.append(getConf().getLineIndent());
            buff.append("IF NOT (:new.").append(pk);
            buff.append(" = :old.").append(pk).append(") THEN");
            
            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
            buff.append(getConf().getLineIndent()).append(getConf().getLineIndent());
            buff.append("RAISE cannot_change_counter;");
            
            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
            buff.append(getConf().getLineIndent());
            buff.append("END IF;");
            
            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
            buff.append("END IF;");

            buff.append(getConf().getLineSeparator());
            buff.append("EXCEPTION");

            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
            buff.append("WHEN cannot_change_counter THEN");

            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
            buff.append(getConf().getLineIndent());
            buff.append("raise_application_error(-20000, ");
            buff.append("'Cannot Change Counter Value');");

            buff.append(getConf().getLineSeparator());
            buff.append("END;");
        }
        return buff.toString();
    }

    
}

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

package org.castor.ddl.postgresql.schemaobject;

import java.text.MessageFormat;

import org.castor.ddl.GeneratorException;
import org.castor.ddl.schemaobject.SequenceKey;
import org.exolab.castor.mapping.xml.KeyGeneratorDef;

/**
 * 
 * <br/>Created on Jul 5, 2006 - 2:54:35 PM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class PostgresqlSequenceKey extends SequenceKey {

    /**
     * Constructor for PostgresqlSequenceKey 
     * @param keyGenDef key generator definition
     * @throws GeneratorException generator error
     */
    protected PostgresqlSequenceKey(final KeyGeneratorDef keyGenDef)
            throws GeneratorException {
        super(keyGenDef);
    }

    /**
     * <pre>CREATE SEQUENCE SEQ_PCV_LIEFERANT
     * MAXVALUE 2147483647
     * INCREMENT BY 1 START WITH 1
     * 
     * for trigger
     * CREATE TRIGGER TRG_PCV_LIEFERANT
     * BEFORE INSERT OR UPDATE ON TAB_PCV_LIEFERANT
     * FOR EACH ROW
     * DECLARE
     *    iCounter TAB_PCV_LIEFERANT.PCLI_ID%TYPE;
     *    cannot_change_counter EXCEPTION;
     * BEGIN
     *     IF INSERTING THEN
     *         Select SEQ_PCV_LIEFERANT.NEXTVAL INTO iCounter FROM Dual;
     *         :new.PCLI_ID := iCounter;
     *     END IF;
     *     
     *     IF UPDATING THEN
     *          IF NOT (:new.PCLI_ID = :old.PCLI_ID) THEN
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
        buff.append(" INCREMENT 1 ").append("MAXVALUE ").
            append(Integer.MAX_VALUE).append(" START 1");
        buff.append(getConf().getSqlStatDelimeter());

        if (isTrigger()) {
            buff.append("");
        }
        return buff.toString();
    }

    
}

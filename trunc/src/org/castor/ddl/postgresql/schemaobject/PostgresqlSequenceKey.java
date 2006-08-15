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

import org.castor.ddl.Configuration;
import org.castor.ddl.GeneratorException;
import org.castor.ddl.keygenerator.SequenceKey;
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
     * <pre>
     * CREATE SEQUENCE SEQ_PCV_LIEFERANT
     * MAXVALUE 2147483647
     * INCREMENT BY 1 START WITH 1
     * 
     * Trigger for PostgreSQL
     *CREATE FUNCTION [func_name]() RETURNS TRIGGER AS $[func_name]$
     *    DECLARE
     *      iCounter [table_name].[pk_name]%TYPE;
     *      cannot_change_counter EXCEPTION;
     *    BEGIN
     *      IF (TG_OP = 'INSERT') THEN
     *          SELECT INTO iCounter NEXTVAL('[seq_name]');
     *          NEW.[pk_name] := iCounter;
     *      END IF;
     *        
     *      IF (TG_OP = 'UPDATE') THEN
     *          IF (NEW.[pk_name] != OLD.[pk_name]) THEN
     *              RAISE cannot_change_counter;
     *          END IF;
     *      END IF;

     *      RETURN NEW;
     *    EXCEPTION
     *      WHEN cannot_change_counter THEN
     *          RAISE EXCEPTION '% Cannot Change Counter Value', -2000;
     *    END;
     *$[func_name]$ LANGUAGE plpgsql
     *CREATE TRIGGER [trigger_name] BEFORE INSERT OR UPDATE ON [table_name]
     *    FOR EACH ROW EXECUTE PROCEDURE [func_name]();
     * </pre>
     * @see org.castor.ddl.keygenerator.SequenceKey#toDDL()
     * {@inheritDoc}
     */
    
    public String toDDL() {
        StringBuffer buff = new StringBuffer();
        String tableName = getTable().getName();
        String pkList = toPrimaryKeyList();
        String pkTypeList = toPrimaryKeyTypeList();
        String sequenceName = MessageFormat.format(getSequence(), 
                new String[]{tableName, pkList});
        
        buff.append(getConf().getLineSeparator()).append(getConf().getLineSeparator());
        buff.append("CREATE SEQUENCE ").append(sequenceName);
        buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
        buff.append(" INCREMENT 1 ").append("MAXVALUE ").
            append(Integer.MAX_VALUE).append(" START 1");
        buff.append(getConf().getSqlStatDelimeter());

        if (isTrigger()) {
            String triggerName = null;
            if (sequenceName.matches(".*SEQ.*")) {
                triggerName = sequenceName.replaceAll("SEQ", "TRG");
            } else {
                triggerName = "TRG" + sequenceName;
            }

//            String functionName = null;
//            if (sequenceName.matches(".*SEQ.*")) {
//                functionName = sequenceName.replaceAll("SEQ", "FUN");
//            } else {
//                functionName = "FUN" + sequenceName;
//            }

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
                        
//            buff.append(getConf().getLineSeparator());
//            buff.append(getConf().getLineSeparator());
//            buff.append("CREATE FUNCTION ").append(functionName).append("()");
//            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
//            buff.append("RETURNS TRIGGER AS $").append(functionName).append("$");
//
//            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
//            buff.append("DECLARE");
//            
//            buff.append(getConf().getLineSeparator());
//            buff.append(getConf().getLineIndent()).append(getConf().getLineIndent());
//            buff.append("iCounter ").append(tableName).append(".");
//            buff.append(pkList).append("%TYPE;");
//            
//            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
//            buff.append("BEGIN");
//            buff.append(getConf().getLineSeparator());
//            buff.append(getConf().getLineIndent()).append(getConf().getLineIndent());
//            buff.append("IF (TG_OP = 'INSERT') THEN");
//            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
//            buff.append(getConf().getLineIndent()).append(getConf().getLineIndent());
//            buff.append("SELECT INTO iCounter NEXTVAL('");
//            buff.append(sequenceName).append("');");
//            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
//            buff.append(getConf().getLineIndent()).append(getConf().getLineIndent());
//            buff.append("NEW.").append(pkList).append(" := iCounter;");
//            buff.append(getConf().getLineSeparator());
//            buff.append(getConf().getLineIndent()).append(getConf().getLineIndent());
//            buff.append("END IF;");
//
//            buff.append(getConf().getLineSeparator());
//            buff.append(getConf().getLineSeparator());
//            buff.append(getConf().getLineIndent()).append(getConf().getLineIndent());
//            buff.append("IF (TG_OP = 'UPDATE') THEN");
//            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
//            buff.append(getConf().getLineIndent()).append(getConf().getLineIndent());
//            buff.append("IF (NEW.").append(pkList);
//            buff.append(" != OLD.").append(pkList).append(") THEN");
//            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
//            buff.append(getConf().getLineIndent()).append(getConf().getLineIndent());
//            buff.append(getConf().getLineIndent());
//            buff.append("RAISE EXCEPTION '% Cannot Change Counter Value', -2000;");
//            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
//            buff.append(getConf().getLineIndent()).append(getConf().getLineIndent());
//            buff.append("END IF;");
//            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
//            buff.append(getConf().getLineIndent());
//            buff.append("END IF;");
//
//            buff.append(getConf().getLineSeparator());
//            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
//            buff.append("RETURN NEW;");
//
//            buff.append(getConf().getLineSeparator());
//            buff.append("END;");
//
//            buff.append(getConf().getLineSeparator());
//            buff.append(getConf().getLineSeparator());
//            buff.append("$").append(functionName).append("$ LANGUAGE plpgsql");
//            buff.append(getConf().getLineSeparator());
//            buff.append(getConf().getLineSeparator());
//            buff.append("CREATE TRIGGER ").append(triggerName);
//            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
//            buff.append("BEFORE INSERT OR UPDATE ON ").append(tableName);
//            buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
//            buff.append("FOR EACH ROW EXECUTE PROCEDURE ");
//            buff.append(functionName).append("();");
        }
        return buff.toString();
    }    
}

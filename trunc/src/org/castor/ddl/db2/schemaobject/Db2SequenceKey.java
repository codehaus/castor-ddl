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

package org.castor.ddl.db2.schemaobject;

import java.text.MessageFormat;

import org.castor.ddl.GeneratorException;
import org.castor.ddl.KeyGenNotSupportException;
import org.castor.ddl.schemaobject.SequenceKey;
import org.exolab.castor.mapping.xml.KeyGeneratorDef;

/**
 * 
 * Created on Jul 11, 2006 - 4:08:00 PM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class Db2SequenceKey extends SequenceKey {

    /**
     * Constructor for Db2SequenceKey
     * @param keyGenDef key generator definition
     * @throws GeneratorException exception
     */
    public Db2SequenceKey(final KeyGeneratorDef keyGenDef) throws GeneratorException {
        super(keyGenDef);
    }

    /**
     * @see org.castor.ddl.schemaobject.SequenceKey#toDDL()
     * {@inheritDoc}
     */
    public String toDDL() throws KeyGenNotSupportException {
        StringBuffer buff = new StringBuffer();
        String tableName = getTable().getName();
        String pk = toPrimaryKeyList();
        String sequence = MessageFormat.format(getSequence(), 
                new String[]{tableName, pk});
        
        buff.append(getConf().getLineSeparator()).append(getConf().getLineSeparator());
        buff.append("CREATE SEQUENCE AS INTEGER ").append(sequence);
        buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
        buff.append("START WITH 1 INCREMENT BY 1");
        buff.append(getConf().getLineSeparator()).append(getConf().getLineIndent());
        buff.append("MAXVALUE ").append(Integer.MAX_VALUE);
        buff.append(getConf().getSqlStatDelimeter());

        if (isTrigger()) {
            buff.append("");
           //todo implement later 
            
           //refer to http://publib.boulder.ibm.com/infocenter/db2luw/v8/index.
           // jsp?topic=/com.ibm.db2.udb.doc/admin/r0000931.htm
        }
        return buff.toString();
    }

}

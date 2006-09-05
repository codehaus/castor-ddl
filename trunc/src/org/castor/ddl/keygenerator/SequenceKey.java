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

package org.castor.ddl.keygenerator;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.castor.ddl.GeneratorException;
import org.castor.ddl.KeyGenNotSupportException;
import org.castor.ddl.schemaobject.Field;
import org.castor.ddl.schemaobject.KeyGenerator;
import org.castor.ddl.schemaobject.Table;
import org.exolab.castor.mapping.xml.KeyGeneratorDef;
import org.exolab.castor.mapping.xml.Param;

/** 
 *SEQUENCE key generator can be used only with Oracle, PostgreSQL, Interbase and 
 *SAP DB. It generates keys using sequences.
 *The key generator has the following parameters:
 *<li/>sequence:    The name of the sequence, Optional, default="{0}_seq"
 *<li/>returning:   RETURNING mode for Oracle8i, values: "true"/"false", Optional, 
 *default="false"
 *<li/>increment:   Increment for Interbase, Optional, default="1"
 *<li/>trigger:     Assume that there is a trigger that already generates key. 
 *Values: "true"/"false", Optional, default="false"
 *
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public class SequenceKey extends KeyGenerator {
    /**LOGGING*/
    private static final Log LOG = LogFactory.getLog(SequenceKey.class);
    
    /** sequence name*/
    private String _name;
    
    /** The name of the sequence, Optional, default="{0}_seq"*/
    private String _sequence = "{0}_seq";
    
    /** alias*/
    private String _alias;
        
    /** Increment for Interbase, Optional, default="1"*/
    private int _increment = 1;
    
    /** RETURNING mode for Oracle8i, values: "true"/"false", Optional, default="false"*/
    private boolean _isReturning = false;
     
    /** 
     * Assume that there is a trigger that already generates key. Values: "true"/"false"
     * Optional, default="false"
     * */
    private boolean _isTrigger = false;
    
    /**handle to table which use this SequenceKey*/
    private Table _table = null;
    
    /**
     * Constructor for SequenceKey
     * @param keyGenDef key generator def
     * @throws GeneratorException generator error
     */
    public SequenceKey(final KeyGeneratorDef keyGenDef) 
        throws GeneratorException {
        String sequenceKey = "sequence";
        String returningKey = "returning";
        String incrementKey = "increment";
        String triggerKey = "trigger";
        
        _alias = keyGenDef.getAlias();
        _name = keyGenDef.getName();
        Param []params = keyGenDef.getParam();
        for (int i = 0; i < params.length; i++) {
            String pname = params[i].getName();
            String pval = params[i].getValue();
            if (pname == null) {
                continue;
            }
            if (sequenceKey.equalsIgnoreCase(pname)) {
                _sequence = pval;
            } else if (returningKey.equalsIgnoreCase(pname)) {
                _isReturning = Boolean.valueOf(pval).booleanValue();
            } else if (incrementKey.equalsIgnoreCase(pname)) {
                try {
                _increment = Integer.parseInt(pval);
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    throw new GeneratorException("can not parse integer" + pname, nfe);
                }
            } else if (triggerKey.equalsIgnoreCase(pname)) {
                _isTrigger = Boolean.valueOf(pval).booleanValue();
            }            
        }                
    }

    /**
     * 
     * @return Returns the alias.
     */
    public final String getAlias() {
        return _alias;
    }

    /**
     * Set the alias by _alias.
     * @param alias  alias
     */
    public final void setAlias(final String alias) {
        _alias = alias;
    }

    /**
     * 
     * @return Returns the increment.
     */
    public final int getIncrement() {
        return _increment;
    }

    /**
     * Set the increment by _increment.
     * @param increment increment 
     */
    public final void setIncrement(final int increment) {
        _increment = increment;
    }

    /**
     * 
     * @return Returns the isReturning.
     */
    public final boolean isReturning() {
        return _isReturning;
    }

    /**
     * Set the isReturning by _isReturning.
     * @param isReturning  returning
     */
    public final void setReturning(final boolean isReturning) {
        _isReturning = isReturning;
    }

    /**
     * 
     * @return Returns the isTrigger.
     */
    public final boolean isTrigger() {
        return _isTrigger;
    }

    /**
     * Set the isTrigger by _isTrigger.
     * @param isTrigger trigger
     */
    public final void setTrigger(final boolean isTrigger) {
        _isTrigger = isTrigger;
    }

    /**
     * 
     * @return Returns the sequence.
     */
    public final String getSequence() {
        return _sequence;
    }

    /**
     * Set the sequence by _sequence.
     * @param sequence sequence
     */
    public final void setSequence(final String sequence) {
        _sequence = sequence;
    }

    /**
     * @see org.castor.ddl.schemaobject.KeyGenerator#getHashKey()
     * {@inheritDoc}
     */
    public final String getHashKey() {
        if (_alias == null) {
            return _name;
        }
        return _alias;
    }

    /**
     * 
     * @return Returns the name.
     */
    public final String getName() {
        return _name;
    }

    /**
     * Set the name by _name.
     * @param name name
     */
    public final void setName(final String name) {
        _name = name;
    }

    /**
     * 
     * @return Returns the table.
     */
    public final Table getTable() {
        return _table;
    }

    /**
     * Set the table by _table.
     * @param table table
     */
    public final void setTable(final Table table) {
        _table = table;
    }

    /**
     * Create DDL for sequence and trigger. By default, it throw an exception. 
     * You must overwrite this class if the database supports SEQUENCE/TRIGGER 
     * creation
     * @return ddl string
     * @throws KeyGenNotSupportException exception
     */
    public String toDDL() throws KeyGenNotSupportException {
        LOG.warn("Not support SEQUENCE key-gen for this database");
        return "";
    }
    
    /**
     * 
     * @return primary key list
     */
    protected final String toPrimaryKeyList() {
        boolean isHasPK = false;
        StringBuffer buff = new StringBuffer();

        boolean isFirstField = true;
        for (Iterator i = _table.getFields().iterator(); i.hasNext();) {
            Field field = (Field) i.next();
            if (field.isIdentity()) {
                isHasPK = true;
                if (!isFirstField) {
                    buff.append("_");
                }
                isFirstField = false;
                buff.append(field.getName());
            }
        }
        //have no primary key
        if (!isHasPK) {
            return "";
        }
        return buff.toString();
    }
    
    /**
     * 
     * @return type of primary key
     */
    protected final String toPrimaryKeyTypeList() {
        boolean isHasPK = false;
        StringBuffer buff = new StringBuffer();

        boolean isFirstField = true;
        for (Iterator i = _table.getFields().iterator(); i.hasNext();) {
            Field field = (Field) i.next();
            if (field.isIdentity()) {
                isHasPK = true;
                if (!isFirstField) {
                    buff.append("_");
                }
                isFirstField = false;
                buff.append(field.getType().getSqlType());
            }
        }
        //have no primary key
        if (!isHasPK) {
            return "";
        }
        return buff.toString();
    }
}

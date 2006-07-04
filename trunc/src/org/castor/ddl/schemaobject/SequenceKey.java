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

package org.castor.ddl.schemaobject;

import org.castor.ddl.GeneratorException;
import org.exolab.castor.mapping.xml.KeyGeneratorDef;
import org.exolab.castor.mapping.xml.Param;


/**
 * 
 * Created on Jun 24, 2006 - 2:01:26 AM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class SequenceKey implements KeyGenerator {
    /** sequence name*/
    public String _name;
    
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
    

    /**
     * Constructor for SequenceKey
     * @param alias
     */
    public SequenceKey(String alias) {
        super();
        _alias = alias;
    }

    /**
     * Constructor for SequenceKey
     * @param keyGenDef
     * @throws GeneratorException
     */
    public SequenceKey(KeyGeneratorDef keyGenDef) throws GeneratorException{
//        String name = keyGenDef.getName();
        String SEQUENCE = "sequence";
        String RETURNING = "returning";
        String INCREMENT = "increment";
        String TRIGGER = "trigger";
        
//        if(name == null || _name.equals(name.toUpperCase()))
//            throw new GeneratorException("can not create sequence key for name" + name);
        _alias = keyGenDef.getAlias();
        _name = keyGenDef.getName();
        Param []params = keyGenDef.getParam();
        for(int i = 0; i < params.length; i++) {
            String pname = params[i].getName();
            if(pname == null)
                continue;
            if(SEQUENCE.equals(pname.toLowerCase())) {
                _sequence = params[i].getValue();
            } else if(RETURNING.equals(pname.toLowerCase())) {
                _isReturning = Boolean.valueOf(pname).booleanValue();
            } else if(INCREMENT.equals(pname.toLowerCase())) {
                try {
                _increment = Integer.parseInt(pname);
                }catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    throw new GeneratorException("can not parse integer" + pname, nfe);
                }
            } else if(TRIGGER.equals(pname.toLowerCase())) {
                _isTrigger = Boolean.valueOf(pname).booleanValue();
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
     * @param alias 
     */
    public final void setAlias(String alias) {
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
     * @param increment 
     */
    public final void setIncrement(int increment) {
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
     * @param isReturning 
     */
    public final void setReturning(boolean isReturning) {
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
     * @param isTrigger 
     */
    public final void setTrigger(boolean isTrigger) {
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
     * @param sequence 
     */
    public final void setSequence(String sequence) {
        _sequence = sequence;
    }


    /* (non-Javadoc)
     * @see org.castor.ddl.schemaobject.KeyGenerator#getHashKey()
     */
    public String getHashKey() {
        if(_alias == null)
            return _name;
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
     * @param name 
     */
    public final void setName(String name) {
        _name = name;
    }

    /**
     * Constructor for SequenceKey
     * @param name
     * @param alias
     */
    public SequenceKey(String name, String alias) {
        super();
        // TODO Auto-generated constructor stub
        _name = name;
        _alias = alias;
    }

    /* (non-Javadoc)
     * @see org.castor.ddl.schemaobject.SchemaObject#toDDL()
     */
    public String toDDL() {
        // TODO Auto-generated method stub
        return null;
    }
}

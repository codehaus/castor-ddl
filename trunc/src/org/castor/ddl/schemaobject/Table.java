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

import java.util.Vector;

/**
 * 
 * Created on Jun 23, 2006 - 5:43:29 PM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

/**
 * 
 * Created on Jun 23, 2006 - 6:22:33 PM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public class Table implements SchemaObject {
    /**table name*/
    private String _name = null;
    
    /**handle key generator*/
    private String _keyGenerator = null;
    
    /**list of field*/
    private Vector _fields;
    
    /**identity*/
    private Vector _identities;
    
    /**triggers*/
    private Vector _triggers;
    
    /**indexes*/
    private Vector _indexes;
    
    /** foriegn key*/
    private Vector _foreignKey;
    
    /**
     * Constructor for Table
     * @param name
     */
    public Table(String name) {
        super();
        // TODO Auto-generated constructor stub
        _name = name;
        _fields = new Vector();
        _identities = new Vector();
        _triggers = new Vector();
        _indexes = new Vector();
        _foreignKey = new Vector();
    }

    /**
     * 
     * @return Returns the fields.
     */
    public Vector getFields() {
        return _fields;
    }

    /**
     * Set the fields by _fields.
     * @param fields 
     */
    public void setFields(Vector fields) {
        _fields = fields;
    }

    /**
     * 
     * @param field
     */
    public void addField(Field field) {
        _fields.add(field);
    }
    
    /**
     * 
     * @param index
     * @return
     */
    public Field getField(int index) {
        return (Field)_fields.get(index);
        
    }
    
    /**
     * 
     * @return
     */
    public int getFieldCount() {
        return _fields.size();
    }
    
    /**
     * 
     * @return Returns the identities.
     */
    public Vector getIdentities() {
        return _identities;
    }

    /**
     * Set the identities by _identities.
     * @param identities 
     */
    public void setIdentities(Vector identities) {
        _identities = identities;
    }

    /**
     * 
     * @param name
     */
    public void addIdentity(String name) {
        _identities.add(name);
    }
    
    /**
     * 
     * @return
     */
    public int getIdentityCount() {
        return _identities.size();
        
    }
    
    /**
     * 
     * @param index
     * @return
     */
    public String getIdentity(int index) {
        
        return (String) _identities.get(index);
    }
    /**
     * 
     * @return Returns the indexes.
     */
    public Vector getIndexes() {
        return _indexes;
    }

    /**
     * Set the indexes by _indexes.
     * @param indexes 
     */
    public void setIndexes(Vector indexes) {
        _indexes = indexes;
    }

    /**
     * 
     * @param index
     */
    public void setIndex(Index index) {        
        _indexes.add(index);
    }
    
    public Index getIndex(int index) {
        return (Index)_indexes.get(index);        
    }
    
    /**
     * 
     * @return Returns the name.
     */
    public String getName() {
        return _name;
    }

    /**
     * Set the name by _name.
     * @param name 
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * 
     * @return Returns the triggers.
     */
    public Vector getTriggers() {
        return _triggers;
    }

    /**
     * Set the triggers by _triggers.
     * @param triggers 
     */
    public void setTriggers(Vector triggers) {
        _triggers = triggers;
    }

    /**
     * 
     * @param trigger
     */
    public void addTrigger(Trigger trigger) {
        _triggers.add(trigger);       
    }
    
    /**
     * 
     * @return
     */
    public int getTriggerCount() {
        return _triggers.size();
    }
    
    /**
     * 
     * @return Returns the foreignKey.
     */
    public Vector getForeignKey() {
        return _foreignKey;
    }

    /**
     * Set the foreignKey by _foreignKey.
     * @param foreignKey 
     */
    public void setForeignKey(Vector foreignKey) {
        _foreignKey = foreignKey;
    }

    /**
     * 
     * @param fk
     */
    public void addForeignKey(ForeignKey fk) {
       _foreignKey.add(fk);       
    }
    
    /**
     * 
     * @return
     */
    public int getForeignKeyCount() {
        return _foreignKey.size();
    }

    /**
     * 
     * @return Returns the keyGenerator.
     */
    public final String getKeyGenerator() {
        return _keyGenerator;
    }

    /**
     * Set the keyGenerator by _keyGenerator.
     * @param keyGenerator 
     */
    public final void setKeyGenerator(String keyGenerator) {
        _keyGenerator = keyGenerator;
    }
}

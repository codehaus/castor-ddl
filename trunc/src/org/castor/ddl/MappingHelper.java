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

package org.castor.ddl;

import java.util.Enumeration;
import java.util.Vector;

import org.castor.ddl.TypeInfo;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.xml.ClassMapping;
import org.exolab.castor.mapping.xml.FieldMapping;
import org.exolab.castor.mapping.xml.KeyGeneratorDef;

/**
 * this class handles all common tasks for Mapping
 * Created on Jun 8, 2006 - 1:13:56 PM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */ 

public class MappingHelper {

    /** handle the _mapping document*/
    private Mapping _mapping;

    /** handle the class _mapping document*/
    private ClassMapping _classMapping;
    
    /** handle the field _mapping document*/
    private FieldMapping _fieldMapping;
    
    /** handle the typemapper*/
    private TypeMapper _typeMapper;
    
    
    /**
     * Constructor for MappingHelper
     */
    public MappingHelper() {
        super();
    }

    /**
     * 
     * @return Returns the mapping.
     */
    public final Mapping getMapping() {
        return _mapping;
    }

    /**
     * Set the mapping by _mapping.
     * @param mapping 
     */
    public final void setMapping(Mapping mapping) {
        _mapping = mapping;
    }

    /**
     * 
     * @return Returns the typeMapper.
     */
    public final TypeMapper getTypeMapper() {
        return _typeMapper;
    }

    /**
     * Set the typeMapper by _typeMapper.
     * @param typeMapper 
     */
    public final void setTypeMapper(TypeMapper typeMapper) {
        _typeMapper = typeMapper;
    }

    /**
     * Constructor for MappingHelper
     */
    public MappingHelper(Mapping mapping, TypeMapper typeMapper) {
        super();
        _mapping = mapping;
        _typeMapper = typeMapper;
    }

    /**
     * return the ClassMapping which has the name attribute
     * @param name
     * @return
     */
    public synchronized ClassMapping getClassMappingByName(String name) throws GeneratorException{
        
       Enumeration ec = _mapping.getRoot().enumerateClassMapping();
      while (ec.hasMoreElements()) {
          ClassMapping cm = (ClassMapping) ec.nextElement();
          String cmName = cm.getName();
          if(cmName != null && cmName.equals(name)) {
              _classMapping = cm;
              return cm;
          }
      }
      throw new GeneratorException("can not find class " + name);
    }
    
    /**
     * 
     * @param className
     * @param fieldName
     * @return
     */
    public synchronized FieldMapping getFieldMappingByName(String className, String fieldName) throws GeneratorException{
        ClassMapping cm = getClassMappingByName(className);
        
        Enumeration ef = cm.getClassChoice().enumerateFieldMapping();
        while (ef.hasMoreElements()) {
            FieldMapping fm = (FieldMapping) ef.nextElement();
            String fmName = fm.getName();
            if(fmName != null && fmName.equals(fieldName)) {
                _fieldMapping = fm;
                return fm;
            }
        }
        throw new GeneratorException("can not find field " + fieldName + " in class " + className);
    }

    /**
     * return an array of type of reference table. In case the table A refers to 
     * table B, table B refers to table C, it should call resolveTypeReference recursively.
     * @param className
     * @return
     */
    public synchronized String[] resolveTypeReferenceForIds(String className)throws GeneratorException {
        //get the reference's class _mapping 
        
        ClassMapping cm = getClassMappingByName(className);
        String []ids = cm.getIdentity();
        Vector types = new Vector();
        
        Enumeration ef = cm.getClassChoice().enumerateFieldMapping();
        boolean isExistFieldId = isUseFieldIdentity(cm);

        while (ef.hasMoreElements()) {
            FieldMapping fm = (FieldMapping) ef.nextElement();
            
            if(isExistFieldId && fm.getIdentity()) {
                /**
                <class name="myapp.ProductGroup" >
                  <field name="id" type="integer" identity="true">
                    <sql name="id1 id2" type="integer"/>
                  </field>
                </class>
               */
                
                TypeInfo typeinfo = null;
                String sqltype = fm.getSql().getType();//field type
                
                if(sqltype != null)
                    typeinfo = _typeMapper.getType(sqltype);
                
                if(typeinfo == null) {
                    String []refRefType = resolveTypeReferenceForIds(fm.getType());
                    for(int l = 0; l < refRefType.length; l++) {
                        types.add(refRefType[l]);                                    
                    }
                }else {
                    for(int i = 0; i < fm.getSql().getNameCount(); i++) {
                        types.add(fm.getSql().getType());
                    }
                }
            }else if(!isExistFieldId) {
                /**
                <class name="myapp.ProductGroup" identity="id">
                  <field name="id" type="integer" >
                    <sql name="id1 id2" type="integer"/>
                  </field>
                </class>
               */
                  String fieldName = fm.getName();
//                String []sqlnames = fm.getSql().getName();
//                for(int i = 0; i < sqlnames.length; i++) {
                    for(int j = 0; j < ids.length; j++) {
//                        if(sqlnames[i].equals(ids[j])) {//found reference type
                        if(fieldName.equals(ids[j])) {  
                        //checking for type if this table is a reference table
                            TypeInfo typeinfo = _typeMapper.getType(fm.getType());
                            if(typeinfo == null) {
                                String []refRefType = resolveTypeReferenceForIds(fm.getType());
                                for(int l = 0; l < refRefType.length; l++) {
                                    types.add(refRefType[l]);                                    
                                }
                            }else {
                                types.add(fm.getSql().getType());
                            }
                        }
//                    }
                }
            }
        }
        
        return (String[])types.toArray(new String[0]);
    }

    public boolean isUseFieldIdentity(ClassMapping cm) {
        Enumeration ef = cm.getClassChoice().enumerateFieldMapping();
        //re-check all identity;
        while (ef.hasMoreElements()) {
            FieldMapping fm = (FieldMapping) ef.nextElement();
            if(fm.getIdentity()) {
                return true;
            }
        }
        return false;
    }

    /**
    <code> <class name="myapp.ProductGroup" identity="id">
      <field name="id" type="integer" >
        <sql name="id1 id2" type="integer"/>
      </field>
    </class>
      <code>
     * @param cm
     * @param fm
     * @return
     */
    public boolean isIdentity(ClassMapping cm, FieldMapping fm) {
        String []ids = cm.getIdentity();
//        String []sqlname = fm.getSql().getName();
        String fieldName = fm.getName();
//        for(int i=0; i < sqlname.length; i++)
            for(int j=0; j < ids.length; j++) {
//                if(ids[j].equals(sqlname[i]))
                if(ids[j].equals(fieldName))
                    return true;
            }
        return false;
    }
    
    /**
     * get identity for a classmapping
     * @param cm
     * @return
     */
    public String[] getClassMappingIdentity(ClassMapping cm) {
        Vector ids = new Vector();
        Enumeration ef = cm.getClassChoice().enumerateFieldMapping();
        //re-check all identity;
        boolean isExistFieldId = false;
        while (ef.hasMoreElements()) {
            FieldMapping fm = (FieldMapping) ef.nextElement();
            if(fm.getIdentity()) {
                isExistFieldId = true;
                int ncount = fm.getSql().getNameCount();
                for (int i = 0; i < ncount; i++) {
                    ids.add(fm.getSql().getName(i));
                }
            }
        }
        //if there is a field identity, overwrite the class's one
        if (isExistFieldId)
            return (String[])ids.toArray(new String[0]);
        
        //else, return class's identity
        return cm.getIdentity();
    }
    /**
     * 
     * @return Returns the classMapping.
     */
    public final ClassMapping getClassMapping() {
        return _classMapping;
    }

    /**
     * 
     * @return Returns the fieldMapping.
     */
    public final FieldMapping getFieldMapping() {
        return _fieldMapping;
    }

    public KeyGeneratorDef getKeyGeneratorDef(String keyGen) {
        return null;
    }
}


/*
 * Copyright 2006 Le Duc Bao
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.castor.ddl;

import java.util.Enumeration;
import java.util.Vector;

import org.castor.ddl.typeinfo.TypeInfo;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.xml.ClassMapping;
import org.exolab.castor.mapping.xml.FieldMapping;

/** 
 * This class handles all common tasks for manipulating Mapping document
 * 
 * <br/> Created on Jun 8, 2006 - 1:13:56 PM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class MappingHelper {

    /** handle the _mapping document */
    private Mapping _mapping;

    /** handle the class _mapping document */
    private ClassMapping _classMapping;

    /** handle the field _mapping document */
    private FieldMapping _fieldMapping;

    /** handle the typemapper */
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
     * 
     * @param mapping
     *            mapping
     */
    public final void setMapping(final Mapping mapping) {
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
     * 
     * @param typeMapper
     *            type mapper
     */
    public final void setTypeMapper(final TypeMapper typeMapper) {
        _typeMapper = typeMapper;
    }

    /**
     * 
     * Constructor for MappingHelper
     * 
     * @param mapping
     *            mmapping
     * @param typeMapper
     *            type mapper
     */
    public MappingHelper(final Mapping mapping, final TypeMapper typeMapper) {
        super();
        _mapping = mapping;
        _typeMapper = typeMapper;
    }

    /**
     * return the ClassMapping which has the attribute "name" associated with
     * parameter name and throw an exception if it is not found.
     * @param name
     *            class name
     * @return class by class name
     */
    public synchronized ClassMapping getClassMappingByName(final String name) {

        Enumeration ec = _mapping.getRoot().enumerateClassMapping();
        while (ec.hasMoreElements()) {
            ClassMapping cm = (ClassMapping) ec.nextElement();
            String cmName = cm.getName();
            if (cmName != null && cmName.equals(name)) {
                _classMapping = cm;
                return cm;
            }
        }
        return null;
    }

    /**
     * searching FieldMapping in Class by className in which its name is
     * associated with parameter fieldName and throw an exception if it is not
     * found
     * 
     * @param className
     *            class name
     * @param fieldName
     *            field name
     * @return field mapping
     * @throws GeneratorException
     *             field is not fould
     */
    public synchronized FieldMapping getFieldMappingByName(
            final String className, final String fieldName)
            throws GeneratorException {
        ClassMapping cm = getClassMappingByName(className);

        Enumeration ef = cm.getClassChoice().enumerateFieldMapping();
        while (ef.hasMoreElements()) {
            FieldMapping fm = (FieldMapping) ef.nextElement();
            String fmName = fm.getName();
            if (fmName != null && fmName.equals(fieldName)) {
                _fieldMapping = fm;
                return fm;
            }
        }
        throw new GeneratorException("can not find field " + fieldName
                + " in class " + className);
    }

    /**
     * 
     * @param cm class mapping
     * @return list of type of reference ids from class name
     * @throws GeneratorException an exception
     */
    public final synchronized String[] resolveTypeReferenceForIds(
            final ClassMapping cm) throws GeneratorException {
        boolean isFoundKey = false;
        
        String[] ids = cm.getIdentity();
        Vector types = new Vector();

        Enumeration ef = cm.getClassChoice().enumerateFieldMapping();
        boolean isExistFieldId = isUseFieldIdentity(cm);

        while (ef.hasMoreElements()) {
            FieldMapping fm = (FieldMapping) ef.nextElement();

            if (isExistFieldId && fm.getIdentity()) {
                /**
                 * <class name="myapp.ProductGroup" > 
                 *  <field name="id" type="integer" identity="true"> 
                 *      <sql name="id1 id2" type="integer"/> 
                 *  </field> 
                 * </class>
                 */

                TypeInfo typeinfo = null;
                String sqltype = fm.getSql().getType();

                if (sqltype != null) {
                    typeinfo = _typeMapper.getType(sqltype);
                }

                if (typeinfo == null) {
                    String[] refRefType = resolveTypeReferenceForIds(fm
                            .getType());
                    for (int l = 0; l < refRefType.length; l++) {
                        types.add(refRefType[l]);
                        isFoundKey = true;
                    }
                } else {
                    for (int i = 0; i < fm.getSql().getNameCount(); i++) {
                        types.add(fm.getSql().getType());
                    }
                }
            } else if (!isExistFieldId) {
                /**
                 * <class name="myapp.ProductGroup" identity="id"> 
                 *  <field name="id" type="integer" > 
                 *      <sql name="id1 id2" type="integer"/> 
                 *  </field> 
                 * </class>
                 */
                String fieldName = fm.getName();
                // String []sqlnames = fm.getSql().getName();
                // for(int i = 0; i < sqlnames.length; i++) {
                for (int j = 0; j < ids.length; j++) {
                    // if(sqlnames[i].equals(ids[j])) {//found reference type
                    if (fieldName.equals(ids[j])) {

                        // checking for type if this table is a reference table
                        TypeInfo typeinfo = null;
                        String sqltype = fm.getSql().getType();

                        // verify if sqltype exists
                        if (sqltype != null) {
                            typeinfo = _typeMapper.getType(sqltype);
                        }

                        if (typeinfo == null) {
                            ClassMapping cmRef = getClassMappingByName(fm.getType());
                            // if cmRef is null, the reference class is not found
                            //then using field type
                            if (cmRef == null) {                        
                                typeinfo = _typeMapper.getType(fm.getType());
                                
                                if (typeinfo == null) {
                                    throw new TypeNotFoundException("Cann't resolve type "
                                        + fm.getType());
                                }
                                int count = fm.getSql().getNameCount();
                                if (count == 0) { count = fm.getSql().getManyKeyCount(); }
                                
                                for (int l = 0; l < count; l++) {
                                    types.add(fm.getType());
                                    isFoundKey = true;
                                }
                                
                            } else {
                                //resolve type for reference class
                                String[] refRefType = resolveTypeReferenceForIds(fm
                                        .getType());
                                for (int l = 0; l < refRefType.length; l++) {
                                    types.add(refRefType[l]);
                                    isFoundKey = true;
                                }
                            }
                        } else {
                            types.add(fm.getSql().getType());
                            isFoundKey = true;
                        }
                    }
                    // }
                }
            }
            
        }

        //if there is no identity found, looking in the extend class
        if (!isFoundKey && cm.getExtends() != null) {
           ClassMapping extendClass = (ClassMapping) cm.getExtends(); 
           String[] refRefType = resolveTypeReferenceForIds(extendClass);
           for (int l = 0; l < refRefType.length; l++) {
               types.add(refRefType[l]);
           }               
        }

        return (String[]) types.toArray(new String[0]);
    }

    /**
     * Return an array of type of reference table. In case the table A refers to
     * table B, table B refers to table C, it should call resolveTypeReference
     * recursively. For example,
     * 
     * <pre>
     * &lt;mapping&gt;
     *   &lt;class name=&quot;myapp.OtherProductGroup&quot; &gt;
     *     &lt;map-to table=&quot;other_prod_group&quot; xml=&quot;group&quot; /&gt;
     *     &lt;field name=&quot;id&quot; type=&quot;integer&quot; identity="true"&gt;
     *       &lt;sql name=&quot;id&quot; type=&quot;integer&quot;/&gt;
     *     &lt;/field&gt;
     *   &lt;/class&gt;
     * 
     *   &lt;class name=&quot;myapp.ProductGroup&quot; identity=&quot;id&quot;&gt;
     *     &lt;map-to table=&quot;prod_group&quot; xml=&quot;group&quot; /&gt;
     *     &lt;field name=&quot;id&quot; type=&quot;myapp.OtherProductGroup&quot; &gt;
     *       &lt;sql name=&quot;prod_id&quot; /&gt;
     *     &lt;/field&gt;
     *   &lt;/class&gt;
     * 
     *   &lt;class name=&quot;myapp.Product&quot; identity=&quot;id&quot;&gt;
     *     &lt;field name=&quot;group&quot; type=&quot;myapp.ProductGroup&quot;&gt;
     *       &lt;sql name=&quot;group_id&quot; /&gt;
     *     &lt;/field&gt;
     *   &lt;/class&gt;
     * &lt;/mapping&gt;     
     * </pre>
     * 
     * @param className
     *            class name
     * @return list of type of reference ids from class name
     * @throws GeneratorException
     *             generator error
     */
    public final synchronized String[] resolveTypeReferenceForIds(
            final String className) throws GeneratorException {
        // get the reference's class _mapping
        ClassMapping cm = getClassMappingByName(className);

        if (cm == null) {
            throw new GeneratorException("can not find class " + className);
        }

        return resolveTypeReferenceForIds(cm);
    }

    /**
     * If the ClassMapping uses identity in the FieldMapping
     * 
     * @param cm
     *            class mapping
     * @return true if this class uses the field identity
     */
    public final boolean isUseFieldIdentity(final ClassMapping cm) {
        Enumeration ef = cm.getClassChoice().enumerateFieldMapping();
        // re-check all identity;
        while (ef.hasMoreElements()) {
            FieldMapping fm = (FieldMapping) ef.nextElement();
            if (fm.getIdentity()) {
                return true;
            }
        }
        return false;
    }

    /**check if fm is an identity
     * <pre>
     *  &lt;class name=&quot;myapp.ProductGroup&quot; identity=&quot;id&quot;&gt;
     *      &lt;field name=&quot;id&quot; type=&quot;integer&quot; &gt;
     *          &lt;sql name=&quot;id1 id2&quot; type=&quot;integer&quot;/&gt;
     *      &lt;/field&gt;
     *  &lt;/class&gt;
     *  <pre/>
     *  @param cm class mapping
     *  @param fm field mapping
     *  @return true if fm is an identity
     */
    public boolean isIdentity(final ClassMapping cm, final FieldMapping fm) {
        String[] ids = cm.getIdentity();
        String fieldName = fm.getName();

        for (int j = 0; j < ids.length; j++) {
            if (ids[j].equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The identity definitions at class and field are alternative syntax. If
     * both are specified the one at field should take precedence over the class
     * one. In other words if both are specified the one at class will be
     * ignored.
     * 
     * @param cm
     *            Class mapping
     * @return list of identity
     */
    public String[] getClassMappingIdentity(final ClassMapping cm) {
        Vector ids = new Vector();
        
        //if this is a child class, using its parrent identities as its identities
        if (cm.getExtends() != null) {
           return getClassMappingIdentity((ClassMapping) cm.getExtends()); 
        }
        
        String[] identities = cm.getIdentity();
        Enumeration ef = cm.getClassChoice().enumerateFieldMapping();
        // re-check all identity;
        boolean isExistFieldId = isUseFieldIdentity(cm);
        
        while (ef.hasMoreElements()) {
            FieldMapping fm = (FieldMapping) ef.nextElement();
            //add all sql columns into identity list
            if (isExistFieldId && fm.getIdentity()) {
                isExistFieldId = true;
                int ncount = fm.getSql().getNameCount();
                for (int i = 0; i < ncount; i++) {
                    ids.add(fm.getSql().getName(i));
                }
            } else if (!isExistFieldId) {
                //if using class identity, find out all correspondent column names
                String fieldName = fm.getName();
                for (int j = 0; j < identities.length; j++) {
                    if (fieldName.equals(identities[j])) {
                        // checking for type if this table is a reference table
                        int ncount = fm.getSql().getNameCount();
                        for (int i = 0; i < ncount; i++) {
                            ids.add(fm.getSql().getName(i));
                        }
                    }
                }
                
            }
        }
        return (String[]) ids.toArray(new String[0]);
//        // if there is a field identity, overwrite the class's one
//        if (isExistFieldId) {
//            return (String[]) ids.toArray(new String[0]);
//        }
//
//        // else, return class's identity
//        return cm.getIdentity();
    }

    /**
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

}

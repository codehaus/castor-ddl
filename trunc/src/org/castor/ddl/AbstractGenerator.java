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

import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.castor.ddl.schemaobject.Field;
import org.castor.ddl.schemaobject.ForeignKey;
import org.castor.ddl.schemaobject.Index;
import org.castor.ddl.schemaobject.KeyGenerator;
import org.castor.ddl.schemaobject.Schema;
import org.castor.ddl.schemaobject.SchemaFactory;
import org.castor.ddl.schemaobject.Table;
import org.castor.ddl.typeinfo.TypeInfo;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.mapping.xml.ClassChoice;
import org.exolab.castor.mapping.xml.ClassMapping;
import org.exolab.castor.mapping.xml.FieldMapping;
import org.exolab.castor.mapping.xml.KeyGeneratorDef;
import org.exolab.castor.mapping.xml.MapTo;
import org.exolab.castor.mapping.xml.MappingRoot;
import org.exolab.castor.mapping.xml.Sql;

/**
 * AbstractGenerator is the base class for various DDL generator of specific DB and
 * handles following tasks: 
 * <li/> Extract information from Mapping to Schema
 * <li/> Loop through the schema and provide a skeleton for DDL creation
 * 
 * <p/>AbstractGenerator will automatically extract necessary informations for DDL 
 * creation. Thoses informations are handled by Schema.
 * <p/>To create new generator for a DBMS, you should:
 * <li/> Overwrite this class to create new generator for a DBMS. 
 * <li/> If the systax of DBMS is different to standard DDL systax, you should 
 * overwrite SchemaObjects (Table, Field, KeyGenerator, Index, ForeignKey,...) class. 
 * The class SchemaObjectFactory is also who handles the SchemaObject creation must 
 * be overwritten.
 * <li/> Mapping type between JDBC type and specific DBMS being also different among 
 * varios DBMS, you must overwrite the TypeMapper.
 * <p/>Example:
 * <li/> Generator for DB2
 * <pre>
 * 
 *public class Db2Generator extends AbstractGenerator {
 *
 *    public Db2Generator(final String globConf, final String dbConf)
 *            throws GeneratorException {
 *        super(globConf, dbConf);
 *        setTypeMapper(new Db2TypeMapper(getConf()));
 *    }
 *}   
 * </pre>
 * <li/>TypeMapper for DB2
 * <pre>
 *public final class Db2TypeMapper extends AbstractTypeMapper {
 *    public Db2TypeMapper(final Configuration conf) {
 *        super(conf);
 *    }
 * 
 *    protected void initialize(final Configuration conf) {
 *        // numeric types
 *        this.add(new NotSupportedType("bit"));
 *        LOG.warn("Db2 does not support 'TINY' type, use SMALLINT instead.");
 *        this.add(new NoParamType("tinyint", "SMALLINT"));
 *        this.add(new NoParamType("smallint", "SMALLINT"));
 *        this.add(new NoParamType("integer", "INTEGER"));
 *        this.add(new NoParamType("bigint", "BIGINT"));
 *    }
 *} 
 * </pre>
 * 
 * @version $Date: 2006-04-25 16:09:10 -0600 (Tue, 25 Apr 2006) $
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 * 
 */
public abstract class AbstractGenerator implements Generator {
    /**logging*/
    /** handle all configurations (key, value) */
    private Configuration _conf;

    /** handle the writer for output */
    private java.io.PrintStream _printer;

    /** handle the _mapping document */
    private Mapping _mapping;

    /** handle the typemapper */
    private TypeMapper _typeMapper;

    /** handle the MappingHelper */
    private MappingHelper _mappingHelper;

    /** handle all resolving tables */
    private Map _resolveTable;

    /** schema */
    private Schema _schema;

    /** handle schema factory */
    private SchemaFactory _schemaFactory;

    /**
     * Create a Generator from ddl configuration file and db configuration file
     * 
     * @param globConf
     *            global configuration file
     * @param dbConf
     *            db specfic configuration file
     * @throws GeneratorException
     *             throws exception when can not read file
     */
    protected AbstractGenerator(final String globConf, final String dbConf)
            throws GeneratorException {
        super();
        _mappingHelper = new MappingHelper();
        _resolveTable = new HashMap();
        Configuration conf = new Configuration(globConf);
        conf.addProperties(dbConf);
        setConf(conf);
        setSchemaFactory(new SchemaFactory());

    }

    /**
     * @see org.castor.ddl.Generator#generateDDL(java.lang.String) 
     * {@inheritDoc}
     */
    public void generateDDL(final String mappingFile)
            throws GeneratorException, IOException, MappingException {
        _mapping = new Mapping();
        _mapping.loadMapping(mappingFile);

        _mappingHelper.setMapping(_mapping);
        _mappingHelper.setTypeMapper(_typeMapper);

        //create schema
        createSchema();
        //generate DDL
        generateDDL();
    }

    /**
     * @see org.castor.ddl.Generator#generateDDL(Mapping) 
     * {@inheritDoc}
     */
    public void generateDDL(final Mapping mappingDoc) throws GeneratorException {
        _mapping = mappingDoc;
        _mappingHelper.setMapping(mappingDoc);
        _mappingHelper.setTypeMapper(_typeMapper);

        //create schema
        createSchema();
        //generate ddl
        generateDDL();
    }

    /**
     * @throws GeneratorException
     *             generator exception
     */
    private void generateDDL() throws GeneratorException {
        String groupBy = _conf.getStringValue(Configuration.GROUP_DDL_BY_KEY,
                Configuration.GROUP_DDL_BY_TABLE);
        if (Configuration.GROUP_DDL_BY_TABLE.equalsIgnoreCase(groupBy)) {
            generateDDLGroupByTable();
        } else if (Configuration.GROUP_DDL_BY_DDLTYPE.equalsIgnoreCase(groupBy)) {
            generateDDLGroupByDDLType();
        } else {
            throw new GeneratorException("group ddl by do not support: "
                    + groupBy);
        }
    }

    /**
     * generating ddl group by ddl type
     * 
     * @throws GeneratorException
     *             generator exception
     */
    private void generateDDLGroupByDDLType() throws GeneratorException {
        boolean genSchema = _conf.getBoolValue(
                Configuration.GENERATE_DDL_FOR_SCHEMA_KEY, true);
        boolean genDrop = _conf.getBoolValue(
                Configuration.GENERATE_DDL_FOR_DROP_KEY, true);
        boolean genCreate = _conf.getBoolValue(
                Configuration.GENERATE_DDL_FOR_CREATE_KEY, true);
        boolean genPrimaryKey = _conf.getBoolValue(
                Configuration.GENERATE_DDL_FOR_PRIMARYKEY_KEY, true);
        boolean genForeignKey = _conf.getBoolValue(
                Configuration.GENERATE_DDL_FOR_FOREIRNKEY_KEY, true);
        boolean genIndex = _conf.getBoolValue(
                Configuration.GENERATE_DDL_FOR_INDEX_KEY, true);
        boolean genKeyGen = _conf.getBoolValue(
                Configuration.GENERATE_DDL_FOR_KEYGENERATOR_KEY, true);

        write(generateHeader());
        
        //generate ddl for schema
        if (genSchema) {
            write(_schema.toDDL());
        }

        //generate drop statemetn
        if (genDrop) {
            write(generateDrop());
        }

        //generate create statement
        if (genCreate) {
            write(generateCreate());
        }

        //generate primary key creation statement
        if (genPrimaryKey) {
            write(generatePrimaryKey());
        }

        //generate foreign key creation statement
        if (genForeignKey) {
            write(generateForeignKey());
        }

        //generate index creation statement
        if (genIndex) {
            write(generateIndex());
        }

        if (genKeyGen) {
            write(generateKeyGenerator());
        }
    }

    /**
     * generating ddl group by table
     * @throws GeneratorException
     *             generator exception
     */
    private void generateDDLGroupByTable() throws GeneratorException {
        Vector tables = _schema.getTables();

        boolean genSchema = _conf.getBoolValue(
                Configuration.GENERATE_DDL_FOR_SCHEMA_KEY, true);
        boolean genDrop = _conf.getBoolValue(
                Configuration.GENERATE_DDL_FOR_DROP_KEY, true);
        boolean genCreate = _conf.getBoolValue(
                Configuration.GENERATE_DDL_FOR_CREATE_KEY, true);
        boolean genPrimaryKey = _conf.getBoolValue(
                Configuration.GENERATE_DDL_FOR_PRIMARYKEY_KEY, true);
        boolean genForeignKey = _conf.getBoolValue(
                Configuration.GENERATE_DDL_FOR_FOREIRNKEY_KEY, true);
        boolean genIndex = _conf.getBoolValue(
                Configuration.GENERATE_DDL_FOR_INDEX_KEY, true);
        boolean genKeyGen = _conf.getBoolValue(
                Configuration.GENERATE_DDL_FOR_KEYGENERATOR_KEY, true);

        write(generateHeader());
        if (genSchema) {
            write(_schema.toDDL());
        }

        for (Iterator i = tables.iterator(); i.hasNext();) {
            StringBuffer buff = new StringBuffer();
            Table table = (Table) i.next();

            // drop
            if (genDrop) {
                buff.append(table.toDropDDL());
            }

            // create
            if (genCreate) {
                buff.append(table.toCreateDDL());
            }

            // primary key
            if (genPrimaryKey) {
                buff.append(table.toPrimaryKeyDDL());
            }

            // foreign key
            if (genForeignKey) {
                buff.append(createForeignKeyDDL(table));
            }

            // index
            if (genIndex) {
                buff.append(createIndexDDL(table));
            }

            // KeyGenerator
            if (genKeyGen) {
                buff.append(table.toKeyGeneratorDDL());
            }

            write(buff.toString());
        }

    }

    /**
     * generate primany key creation, delegate to table.toPrimaryKeyDDL()
     * @return primary key creation ddl
     */
    public String generatePrimaryKey() {
        Vector tables = _schema.getTables();
        StringBuffer buff = new StringBuffer();
        for (Iterator i = tables.iterator(); i.hasNext();) {
            Table table = (Table) i.next();
            buff.append(table.toPrimaryKeyDDL());
        }
        return buff.toString();
    }

    /**
     * generate sequence/trigger ddl, delegate to SequenceKey.toDDL() 
     * @return key generator creation ddl
     * @throws KeyGenNotSupportException
     *             exception
     */
    public String generateKeyGenerator() throws KeyGenNotSupportException {
        Vector tables = _schema.getTables();
        StringBuffer buff = new StringBuffer();

        for (Iterator i = tables.iterator(); i.hasNext();) {
            Table table = (Table) i.next();
            buff.append(table.toKeyGeneratorDDL());
        }
        return buff.toString();

    }

    /**
     * generate ddl for index creation
     * @return index creation ddl
     */
    public String generateIndex() {
        Vector tables = _schema.getTables();
        StringBuffer buff = new StringBuffer();

        for (Iterator i = tables.iterator(); i.hasNext();) {
            Table table = (Table) i.next();
            buff.append(createIndexDDL(table));
        }
        return buff.toString();
    }

    /** generate ddl for drop statement
     * @return drop creation ddl
     */
    public String generateDrop() {
        Vector tables = _schema.getTables();
        StringBuffer buff = new StringBuffer();

        for (Iterator i = tables.iterator(); i.hasNext();) {
            Table table = (Table) i.next();
            buff.append(table.toDropDDL());
        }
        return buff.toString();
    }

    /**
     * generate header comment, print out the header_comment_text definition 
     * in the ddl.properties
     * @return header comment
     */
    public String generateHeader() {
       return ""; 
    }

    /**
     * generate create statement, delegate to table.toCreateDDL() 
     * <pre>
     * CREATE TABLE prod (
     *  id INTEGER NOT NULL,
     *  name CHAR(16)
     * );

     * CREATE TABLE prod_detail (
     *  id INTEGER NOT NULL,
     *  prod_id CHAR(16)
     * );
     * </pre>
     * @return create table ddl
     * @throws GeneratorException
     *             generator exception
     */
    public String generateCreate() throws GeneratorException {
        Vector tables = _schema.getTables();
        StringBuffer buff = new StringBuffer();

        for (Iterator i = tables.iterator(); i.hasNext();) {
            Table table = (Table) i.next();
            buff.append(table.toCreateDDL());
        }
        return buff.toString();
    }

    /**
     * generate constrains statement
     * 
     * <pre>
     * Example:
     *   ALTER TABLE `prod_group` ADD CONSTRAINT `FK_prod_group_1` 
     *   FOREIGN KEY `FK_prod_group_1` (`id`, `name`)
     *    REFERENCES `category` (`id`, `name`)
     *    ON DELETE SET NULL
     *    ON UPDATE CASCADE;
     * </pre>
     * 
     * @return foreign key creation ddl
     * @throws GeneratorException
     *             generator exception
     */
    public String generateForeignKey() throws GeneratorException {
        Vector tables = _schema.getTables();
        StringBuffer buff = new StringBuffer();

        for (Iterator i = tables.iterator(); i.hasNext();) {
            Table table = (Table) i.next();
            buff.append(createForeignKeyDDL(table));
        }
        return buff.toString();
    }

    /**
     * 
     * @return Returns the schemaFactory.
     */
    public final SchemaFactory getSchemaFactory() {
        return _schemaFactory;
    }

    /**
     * Set the schemaFactory by _schemaFactory.
     * 
     * @param schemaFactory
     *            schema factory
     */
    public final void setSchemaFactory(final SchemaFactory schemaFactory) {
        _schemaFactory = schemaFactory;
    }

    /**
     * @return Returns the _conf.
     */
    public Configuration getConf() {
        return _conf;
    }

    /**
     * @param conf
     *            The _conf to set.
     */
    public void setConf(final Configuration conf) {
        this._conf = conf;
    }

    /**
     * @param mapping
     *            The mapping to set.
     */
    public void setMapping(final Mapping mapping) {
        this._mapping = mapping;
        _mappingHelper.setMapping(_mapping);
    }

    /**
     * set Writer
     * 
     * @param printer
     *            printer
     */
    public final void setPrinter(final PrintStream printer) {
        this._printer = printer;
    }

    /**
     * 
     * @return Returns the _mapping.
     */
    public final Mapping getMapping() {
        return _mapping;
    }

    /**
     * 
     * @return Returns the writer.
     */
    public final java.io.PrintStream getPrinter() {
        return _printer;
    }

    /**
     * Set the typeMapper by _typeMapper.
     * 
     * @param typeMapper
     *            type mapper
     */
    public final void setTypeMapper(final TypeMapper typeMapper) {
        _typeMapper = typeMapper;
        _mappingHelper.setTypeMapper(_typeMapper);
    }

    /**
     * Extracting informations from mapping to schema, this is done by 3 steps
     * <li/>Extracting KeyGeneratorDef
     * <li/>Extracting Table, the additional for many-many relationship will be added
     * to the _resolveTable
     * <li/>Extracting additional tables for many-many relationship
     * 
     * @throws GeneratorException
     *             generator exception
     */
    public void createSchema() throws GeneratorException {
        // _schema
        MappingRoot root = _mapping.getRoot();
        _schema = _schemaFactory.createSchema();
        _schema.setConf(_conf);

        // generate key generator definition
        Enumeration ekg = root.enumerateKeyGeneratorDef();
        while (ekg.hasMoreElements()) {
            KeyGeneratorDef kg = (KeyGeneratorDef) ekg.nextElement();
            KeyGenerator keygen = _schemaFactory.createKeyGenerator(kg);
            keygen.setConf(_conf);
            _schema.putKeyGenerator(keygen.getHashKey(), keygen);
        }

        //go throught ClassMapping
        Enumeration ec = root.enumerateClassMapping();
        while (ec.hasMoreElements()) {
            ClassMapping cm = (ClassMapping) ec.nextElement();
            Table table = createTable(cm);
            if (table != null) {
                _schema.addTable(table);
            }
        }
        // generate _resolve Table
        Iterator i = _resolveTable.keySet().iterator();
        while (i.hasNext()) {
            ClassMapping cm = (ClassMapping) _resolveTable.get(i.next());
            Table table = createTable(cm);
            if (table != null) {
                _schema.addTable(table);
            }
        }
    }

    /**
     * create sql table from a ClassMapping
     * @param cm
     *            ClassMapping
     * @return table
     * @throws GeneratorException
     *             generator exception
     */
    private Table createTable(final ClassMapping cm) throws GeneratorException {
        String tableName = cm.getMapTo().getTable();
        if (tableName == null) {
            return null;
        }

        Table table = _schemaFactory.createTable();
        table.setConf(_conf);
        table.setName(tableName);
        table.setSchema(_schema);

        // if there are no field in the table
        if (cm.getClassChoice() == null) {
            return table;
        }

        boolean isUseFieldIdentity = _mappingHelper.isUseFieldIdentity(cm);
        Enumeration ef = cm.getClassChoice().enumerateFieldMapping();

        // process key-generator
        String keygenerator = cm.getKeyGenerator();
        KeyGenerator keyGen = null;
        if (keygenerator != null) {
            keyGen = getSchema().getKeyRepository().getKeyGenerator(
                    keygenerator.toUpperCase());

            // key gen is not found
            if (keyGen == null) {
                throw new GeneratorException("key-generator is not found: "
                        + keygenerator);
            }
            keyGen.setConf(_conf);
        }
        table.setKeyGenerator(keyGen);

        while (ef.hasMoreElements()) {
            FieldMapping fm = (FieldMapping) ef.nextElement();

            // if <sql> tag is not defined, there is no _mapping to DB, skip it
            if (fm.getSql() == null) {
                continue;
            }

            // create a field
            boolean isFieldIdentity = fm.getIdentity();
            // if use class' identity, overwrite this one
            if (!isUseFieldIdentity) {
                isFieldIdentity = _mappingHelper.isIdentity(cm, fm);
            }

            // checking for many-key, many-table definition
            if (fm.getSql().getManyKeyCount() > 0) {
                if (fm.getSql().getManyTable() != null) {
                    // checking for many-table case just
                    // 1. generate DDL for creating relationship, with MySQL,
                    // not need
                    // addManyManyForeignKey(table, fm);

                    // 2. generate resolving table for many-many relationship
                    addResolveField(fm, cm.getKeyGenerator());
                    // } else {
                    // // checking for many-key case (not need generate DDL),
                    // // 1. generate DDL for creating relationship, with MySQL
                    // // addOneManyForeignKey(fm, cm.getMapTo().getTable());
                }
                // normal field
            } else {
                String[] sqlnames = fm.getSql().getName();
                String sqltype = fm.getSql().getType();

                TypeInfo typeInfo = null;
                ClassMapping cmRef = null;
                String[] refIdTypes = null;
                boolean isUseReferenceType = false;

                // type resolving: get type info
                if (sqltype != null) {
                    typeInfo = _typeMapper.getType(sqltype);
                }

                // if typeInfo is null, this table has a reference to another
                // one.
                if (typeInfo == null) {
                    isUseReferenceType = true;
                    cmRef = _mappingHelper.getClassMappingByName(fm.getType());
                    // if cmRef is null, the reference class is not found
                    if (cmRef == null) {
                        throw new TypeNotFoundException("can not resolve type "
                                + fm.getType());
                    }

                    refIdTypes = _mappingHelper.resolveTypeReferenceForIds(fm
                            .getType());

                    // if number of reference table's Id differs to number of
                    // field elements
                    if (refIdTypes.length != sqlnames.length) {
                        throw new TypeNotFoundException(
                                "number of reference table's Id differs"
                                        + "to number of field elements "
                                        + fm.getName());
                    }

                    // create foreign key
                    addOneOneForeignKey(table, fm, cm.getMapTo().getTable());
                }

                // create fields
                for (int i = 0; i < sqlnames.length; i++) {
                    Field field = _schemaFactory.createField();
                    field.setConf(_conf);
                    table.addField(field);

                    // group_moderator mediumint(8) DEFAULT '0' NOT NULL,
                    if (isUseReferenceType) {
                        // each sqlname is correpondent to a identity of the
                        // reference table
                        // so, it should get the original type of the reference
                        // field
                        typeInfo = _typeMapper.getType(refIdTypes[i]);
                        if (typeInfo == null) {
                            throw new TypeNotFoundException(
                                    "can not find reference type "
                                            + refIdTypes[i] + " of class "
                                            + cm.getName());
                        }
                    }

                    field.setName(sqlnames[i]);
                    field.setTable(table);
                    field.setType(typeInfo);
                    field.setIdentity(isFieldIdentity);

                    // process key-generator
                    field.setKeyGenerator(keyGen);

                }
            }
        }
        return table;
    }

    /**
     * add many-many foreign key into a table
     * @param fm
     *            Field Mapping
     * @param table
     *            parent table
     * @throws GeneratorException
     *             generator exception
     */
    protected void addManyManyForeignKey(final Table table,
            final FieldMapping fm) throws GeneratorException {
        ForeignKey fk = _schemaFactory.createForeignKey();
        fk.setConf(_conf);
        String s;

        fk.setTable(table);
        s = table + "_" + fm.getName();
        fk.setConstraintName(s);
        fk.setFkName(s);
        fk.setFkkeyList(fm.getSql().getName());

        ClassMapping cm = _mappingHelper.getClassMappingByName(fm.getType());
        fk.setReferenceTableName(cm.getMapTo().getTable());
        fk.setReferenceKeyList(_mappingHelper.getClassMappingIdentity(cm));

        fk.setRelationshipType(ForeignKey.MANY_MANY);

        table.addForeignKey(fk);
    }

    /**
     * add 1-many foreign key into a table
     * @param fm
     *            field mapping
     * @param table
     *            table
     * @param tableName
     *            table name
     * @throws GeneratorException
     *             generator exception
     */
    protected void addOneManyForeignKey(final Table table,
            final FieldMapping fm, final String tableName)
            throws GeneratorException {
        ForeignKey fk = _schemaFactory.createForeignKey();
        fk.setConf(_conf);
        String s;

        ClassMapping cm = _mappingHelper.getClassMappingByName(fm.getType());
        fk.setTable(table);
        s = cm.getMapTo().getTable() + "_" + fm.getName();
        fk.setConstraintName(s);
        fk.setFkName(s);
        fk.setFkkeyList(_mappingHelper.getClassMappingIdentity(cm));

        fk.setReferenceTableName(tableName);
        fk.setReferenceKeyList(fm.getSql().getManyKey());

        fk.setRelationshipType(ForeignKey.ONE_MANY);
        table.addForeignKey(fk);
    }

    /**
     * add 1-1 foreign key into a table
     * @param table
     *            table
     * @param fm
     *            field mapping
     * @param tableName
     *            table name
     * @throws GeneratorException
     *             generator exception
     */
    protected void addOneOneForeignKey(final Table table,
            final FieldMapping fm, final String tableName)
            throws GeneratorException {
        ForeignKey fk = _schemaFactory.createForeignKey();
        fk.setConf(_conf);
        String s;

        fk.setTable(table);
        s = tableName + "_" + fm.getName();
        fk.setConstraintName(s);
        fk.setFkName(s);
        fk.setFkkeyList(fm.getSql().getName());

        ClassMapping cm = _mappingHelper.getClassMappingByName(fm.getType());
        fk.setReferenceTableName(cm.getMapTo().getTable());
        fk.setReferenceKeyList(_mappingHelper.getClassMappingIdentity(cm));

        fk.setRelationshipType(ForeignKey.ONE_ONE);
        table.addForeignKey(fk);
    }

    /**
     * @param fm
     *            field mapping
     * @param keyGen
     *            key generator string
     */
    protected void addResolveField(final FieldMapping fm, final String keyGen) {
        ClassMapping resolveCm = null;

        // get table, if not existe, create one
        if (_resolveTable.containsKey(fm.getSql().getManyTable())) {
            resolveCm = (ClassMapping) _resolveTable.get(fm.getSql()
                    .getManyTable());
        } else {
            resolveCm = new ClassMapping();
            resolveCm.setName(fm.getSql().getManyTable());
            resolveCm.setKeyGenerator(keyGen);

            MapTo mapto = new MapTo();
            mapto.setTable(fm.getSql().getManyTable());
            resolveCm.setMapTo(mapto);
            _resolveTable.put(fm.getSql().getManyTable(), resolveCm);
        }

        FieldMapping resolveFm = new FieldMapping();
        resolveFm.setIdentity(true);
        resolveFm.setName(fm.getName());
        resolveFm.setType(fm.getType());

        ClassChoice cc = resolveCm.getClassChoice();
        if (cc == null) {
            cc = new ClassChoice();
            resolveCm.setClassChoice(cc);
        }
        cc.addFieldMapping(resolveFm);

        Sql sql = new Sql();
        sql.setName(fm.getSql().getName());
        resolveFm.setSql(sql);
    }

    /**
     * generate ddl for foreign key creation of table
     * @param table
     *            table
     * @return foreignkey creation ddl
     */
    protected String createForeignKeyDDL(final Table table) {
        StringBuffer buff = new StringBuffer();

        for (Iterator i = table.getForeignKey().iterator(); i.hasNext();) {
            ForeignKey fk = (ForeignKey) i.next();
            buff.append(fk.toDDL());
        }

        return buff.toString();
    }

    /**
     * generate ddl for index creation of table
     * @param table
     *            table
     * @return index creation ddl
     */
    protected String createIndexDDL(final Table table) {
        StringBuffer buff = new StringBuffer();

        for (Iterator i = table.getIndexes().iterator(); i.hasNext();) {
            Index index = (Index) i.next();
            buff.append(index.toDDL());
        }

        return buff.toString();
    }

    /**
     * 
     * @return Returns the mappingHelper.
     */
    public final MappingHelper getMappingHelper() {
        return _mappingHelper;
    }

    /**
     * Set the mappingHelper by _mappingHelper.
     * 
     * @param mappingHelper
     *            mapping helper
     */
    public final void setMappingHelper(final MappingHelper mappingHelper) {
        _mappingHelper = mappingHelper;
    }

    /**
     * 
     * @return Returns the schema.
     */
    public final Schema getSchema() {
        return _schema;
    }

    /**
     * Set the schema by _schema.
     * 
     * @param schema
     *            schema
     */
    public final void setSchema(final Schema schema) {
        _schema = schema;
    }

    /**
     * 
     * @return Returns the typeMapper.
     */
    public final TypeMapper getTypeMapper() {
        return _typeMapper;
    }

    /**
     * Formating ddl string before its outputs. Depending the ddl_format_case 
     * property, the ddl string will be formated 
     * @param s
     *            string
     * @return format string base on Configuration._ddlFormatCase
     */
    private String format(final String s) {
        if (_conf.getDdlFormatCase() == Configuration.DDL_FORMAT_LOWERCASE) {
            return s.toLowerCase();
        }
        if (_conf.getDdlFormatCase() == Configuration.DDL_FORMAT_UPPERCASE) {
            return s.toUpperCase();
        }
        return s;
    }

    /**
     * 
     * @param s
     *            a string to write to _printer
     */
    protected void write(final String s) {
        _printer.println(format(s));
    }

}

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

package utf.org.castor.ddl;

import java.io.File;
import java.net.URL;

import junit.framework.TestCase;

import org.castor.ddl.AbstractGenerator;
import org.castor.ddl.Configuration;
import org.castor.ddl.TypeMapper;
import org.castor.ddl.mysql.MySQLTypeMapper;
import org.exolab.castor.mapping.Mapping;

/**
 * This class handles all testcase for all database. The specific database will
 * inherite this class. Expecting that all testcase use the same scenarios for
 * all database. The expected result maybe differ within each other. The _engine
 * defines which expected result will be loaded to the testcase. The inherited
 * class may redefine this variable to reuse the test scenarios, but _engine.
 * 
 * Created on Jun 13, 2006 - 6:15:36 PM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public abstract class BaseGeneratorTest extends TestCase {
    /**param prefix*/
    protected static final String PARAM_PREFIX = "default_";

    /** Prostfix of length parameters for types in ddl.properties file. */
    protected static final String PARAM_POSTFIX_LENGTH = "_length";

    /** Prostfix of precision parameters for types in ddl.properties file. */
    protected static final String PARAM_POSTFIX_PRECISION = "_precision";

    /** Prostfix of decimals parameters for types in ddl.properties file. */
    protected static final String PARAM_POSTFIX_DECIMALS = "_decimals";

    /** expected result for specific database engine */
    private String _engine = ExpectedResult.ENGINE_GENERIC;

    /** handle the mapping for test */
    private Mapping _mapping = null;

    /** handle expected ddl */
    private ExpectedResult _expectedDDL = null;

    /** handle the generator */
    private AbstractGenerator _generator = null;

    /** handle the global configuration */
    private String _globalConf = null;

    /** handle the database configuration */
    private String _dbConf = null;

    /**
     * Constructor for BaseGeneratorTest
     * 
     * @param testcase test case
     */
    public BaseGeneratorTest(final String testcase) {
        super(testcase);
    }

    /**
     * load data for test including mapping file and expected result
     * 
     * @param mappingFile mapping file
     * @param expectedFile expected file
     * @throws Exception exception
     */
    protected void loadData(final String mappingFile, final String expectedFile)
            throws Exception {
        String dataDir = "data" + File.separator;

        try {
            URL url = BaseGeneratorTest.class.getResource(dataDir
                    + expectedFile);
            _expectedDDL = ExpectedResult.getExpectedResult(url);
            _expectedDDL.setConf(_generator.getConf());

            _mapping = new Mapping();
            _mapping.loadMapping(BaseGeneratorTest.class.getResource(dataDir
                    + mappingFile));
            _generator.setMapping(_mapping);
            _generator.createSchema();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * load mapping file
     * 
     * @param mappingFile mapping file
     * @throws Exception exception
     */
    protected void loadData(final String mappingFile) throws Exception {
        String dataDir = "data" + File.separator;

        try {
            _mapping = new Mapping();
            _mapping.loadMapping(BaseGeneratorTest.class.getResource(dataDir
                    + mappingFile));
            _generator.setMapping(_mapping);
            _generator.createSchema();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * test single table
     * 
     */
    public void testSingleTable() {
        try {
            // load test data
            loadData("single_table.xml", "single_table.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH) };
            String ddl = _generator.generateCreate();

            boolean b = _expectedDDL.match(_engine, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testSingleTable: " + e.getMessage(), false);
        }
    }

    /**
     * test for ignored table in the mapping
     * 
     */
    public void testIgnoredTable() {
        try {
            // load test data
            loadData("ignored_table.xml", "ignored_table.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH) };
            String ddl = _generator.generateCreate();

            boolean b = _expectedDDL.match(_engine, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testIgnoredTable: " + e.getMessage(), false);
        }
    }

    /**
     * test for no table in the mapping
     * 
     */
    public void testNoTable() {
        try {
            // load test data
            loadData("no_table.xml", "no_table.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH) };
            String ddl = _generator.generateCreate();

            boolean b = _expectedDDL.match(_engine, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testNoTable: " + e.getMessage(), false);
        }
    }

    /**
     * test single table
     * 
     */
    public void testDropTable() {
        try {
            // load test data
            loadData("drop_table.xml", "drop_table.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            String ddl = _generator.generateDrop();

            boolean b = _expectedDDL.match(_engine, ddl);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testDropTable: " + e.getMessage(), false);
        }
    }

    /**
     * test the identity is defined in the class tag
     * 
     */
    public void testClassId() {
        try {
            // load test data
            loadData("class_id.xml", "class_id.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH) };

            String ddl = _generator.generateCreate();
            boolean b = _expectedDDL.match(_engine, 0, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);

            ddl = _generator.generatePrimaryKey();
            b = _expectedDDL.match(_engine, 1, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testClassId: " + e.getMessage(), false);
        }
    }

    /**
     * test the identity is defined in the class tag and there are multiple ids
     * 
     */
    public void testClassMultipleId() {
        try {
            // load test data
            loadData("class_multiple_id.xml", "class_multiple_id.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH) };
            String ddl = _generator.generateCreate();

            boolean b = _expectedDDL.match(_engine, 0, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);

            ddl = _generator.generatePrimaryKey();
            b = _expectedDDL.match(_engine, 1, ddl, null);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);

        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testClassMultipleId: " + e.getMessage(), false);
        }
    }

    /**
     * test the identity is defined in the field tag
     * 
     */
    public void testFieldId() {
        try {
            // load test data
            loadData("field_id.xml", "field_id.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH) };
            String ddl = _generator.generateCreate();
            boolean b = _expectedDDL.match(_engine, 0, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);

            ddl = _generator.generatePrimaryKey();
            b = _expectedDDL.match(_engine, 1, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);

        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testFieldId: " + e.getMessage(), false);
        }
    }

    /**
     * test the identity is defined in the field tag and there are multiple ids
     * 
     */
    public void testFieldMultipleId() {
        try {
            // load test data
            loadData("field_multiple_id.xml", "field_multiple_id.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH) };
            String ddl = _generator.generateCreate();

            boolean b = _expectedDDL.match(_engine, 0, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);

            ddl = _generator.generatePrimaryKey();

            b = _expectedDDL.match(_engine, 1, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testFieldMultipleId: " + e.getMessage(), false);
        }
    }

    /**
     * test for no id definition
     * 
     */
    public void testNoId() {
        try {
            // load test data
            loadData("no_id.xml", "no_id.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH) };
            String ddl = _generator.generateCreate();

            boolean b = _expectedDDL.match(_engine, 0, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);

            ddl = _generator.generatePrimaryKey();

            b = _expectedDDL.match(_engine, 1, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testNoId: " + e.getMessage(), false);
        }
    }

    /**
     * test to create many table in a mapping
     * 
     */
    public void testMultipleTable() {
        try {
            // load test data
            loadData("multiple_table.xml", "multiple_table.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH) };
            String ddl = _generator.generateCreate();

            boolean b = _expectedDDL.match(_engine, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testMultipleTable: " + e.getMessage(), false);
        }
    }

    /**
     * Create a table with 23 fields represented to each data type
     * 
     */
    public void testSingleFieldForAll() {
        try {
            // load test data
            loadData("single_field_for_all.xml", "single_field_for_all.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "tinyint"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "smallint"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "bigint"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "float"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "float"
                            + PARAM_POSTFIX_DECIMALS),
                    conf.getInteger(PARAM_PREFIX + "double"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "double"
                            + PARAM_POSTFIX_DECIMALS),
                    conf.getInteger(PARAM_PREFIX + "real"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "real"
                            + PARAM_POSTFIX_DECIMALS),
                    conf.getInteger(PARAM_PREFIX + "numeric"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "numeric"
                            + PARAM_POSTFIX_DECIMALS),
                    conf.getInteger(PARAM_PREFIX + "decimal"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "decimal"
                            + PARAM_POSTFIX_DECIMALS),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH),
                    conf.getInteger(PARAM_PREFIX + "varchar"
                            + PARAM_POSTFIX_LENGTH),
                    conf.getInteger(PARAM_PREFIX + "longvarchar"
                            + PARAM_POSTFIX_LENGTH),
                    conf.getInteger(PARAM_PREFIX + "timestamp"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "binary"
                            + PARAM_POSTFIX_LENGTH),
                    conf.getInteger(PARAM_PREFIX + "varbinary"
                            + PARAM_POSTFIX_LENGTH),
                    conf.getInteger(PARAM_PREFIX + "longvarbinary"
                            + PARAM_POSTFIX_LENGTH) };

            String ddl = _generator.generateCreate();
            boolean b = _expectedDDL.match(_engine, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testSingleFieldForAll: " + e.getMessage(), false);
        }
    }

    /**
     * test single table
     * 
     */
    public void testSingleField() {
        try {
            // load test data
            loadData("single_field.xml", "single_field.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH) };
            String ddl = _generator.generateCreate();

            boolean b = _expectedDDL.match(_engine, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testSingleField: " + e.getMessage(), false);
        }
    }

    /**
     * test to create identity for key generator
     * 
     */
    public void testKeyGenIdentity() {
        try {
            // load test data
            loadData("key_gen_identity.xml", "key_gen_identity.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH) };
            String ddl = _generator.generateCreate();

            boolean b = _expectedDDL.match(_engine, 0, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);

            ddl = _generator.generateKeyGenerator();

            b = _expectedDDL.match(_engine, 1, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testKeyGenIdentity: " + e.getMessage(), false);
        }
    }

    /**
     * test to create high-low key generator
     * 
     */
    public void testKeyGenHighLow() {
        try {
            // load test data
            loadData("key_gen_high-low.xml", "key_gen_high-low.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH) };
            String ddl = _generator.generateCreate();

            boolean b = _expectedDDL.match(_engine, 0, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);

            ddl = _generator.generateKeyGenerator();

            b = _expectedDDL.match(_engine, 1, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testKeyGenHighLow: " + e.getMessage(), false);
        }
    }

    /**
     * test to create MAX key generator
     * 
     */
    public void testKeyGenMax() {
        try {
            // load test data
            loadData("key_gen_max.xml", "key_gen_max.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH) };
            String ddl = _generator.generateCreate();

            boolean b = _expectedDDL.match(_engine, 0, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);

            ddl = _generator.generateKeyGenerator();
            b = _expectedDDL.match(_engine, 1, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testKeyGenMax: " + e.getMessage(), false);
        }
    }

    /**
     * test to create Sequence key generator
     * 
     */
    public void testKeyGenSequence() {
        try {
            // load test data
            loadData("key_gen_sequence.xml", "key_gen_sequence.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            // test
            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH) };

            String ddl = _generator.generateCreate();

            boolean b = _expectedDDL.match(_engine, 0, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);

            ddl = _generator.generateKeyGenerator();

            b = _expectedDDL.match(_engine, 1, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testKeyGenSequence: " + e.getMessage(), false);
        }
    }

    /**
     * test to create Sequence key generator
     * 
     */
    public void testKeyGenUUID() {
        try {
            // load test data
            loadData("key_gen_uuid.xml", "key_gen_uuid.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            // test
            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH) };

            String ddl = _generator.generateCreate();

            boolean b = _expectedDDL.match(_engine, 0, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);

            ddl = _generator.generateKeyGenerator();

            b = _expectedDDL.match(_engine, 1, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testKeyGenUUID: " + e.getMessage(), false);
        }
    }

    /**
     * test to create one-one relationship
     * 
     */
    public void testOneOneRelationship() {
        try {
            // load test data
            loadData("relationship_1_1.xml", "relationship_1_1.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            // test
            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH) };

            String ddl = _generator.generateCreate();
            boolean b = _expectedDDL.match(_engine, 0, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);

            ddl = _generator.generateForeignKey();
            b = _expectedDDL.match(_engine, 1, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);

        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testOneOneRelationship: " + e.getMessage(), false);
        }
    }

    /**
     * test to create one-many relationship
     * 
     */
    public void testOneManyRelationship() {
        try {
            // load test data
            loadData("relationship_1_n.xml", "relationship_1_n.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            // test
            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH) };

            String ddl = _generator.generateCreate();

            boolean b = _expectedDDL.match(_engine, 0, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);

            ddl = _generator.generateForeignKey();
            b = _expectedDDL.match(_engine, 1, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testOneManyRelationship: " + e.getMessage(), false);
        }
    }

    /**
     * test to create many-many relationship
     * 
     */
    public void testManyManyRelationship() {
        try {
            // load test data
            loadData("relationship_m_n.xml", "relationship_m_n.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            // test
            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH) };

            String ddl = _generator.generateCreate();
            boolean b = _expectedDDL.match(_engine, 0, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);

            ddl = _generator.generateForeignKey();
            b = _expectedDDL.match(_engine, 1, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);

        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testManyManyRelationship: " + e.getMessage(), false);
        }
    }

    /**
     * test to create one-one relationship
     * 
     */
    public void test2LevelsReference() {
        try {
            // load test data
            loadData("2levels_reference.xml", "2levels_reference.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            // test
            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH) };

            String ddl = _generator.generateCreate();

            boolean b = _expectedDDL.match(_engine, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("test2LevelsReference: " + e.getMessage(), false);
        }
    }

    /**
     * test single table
     * 
     */
    public void testIgnoredField() {
        try {
            // load test data
            loadData("ignored_field.xml", "ignored_field.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH) };
            String ddl = _generator.generateCreate();

            boolean b = _expectedDDL.match(_engine, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testIgnoredField: " + e.getMessage(), false);
        }
    }

    /**
     * test field bit
     * 
     */
    public void testNoField() {
        try {
            // load test data
            loadData("no_field.xml", "no_field.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            String ddl = _generator.generateCreate();

            boolean b = _expectedDDL.match(_engine, ddl);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testNoField: " + e.getMessage(), false);
        }
    }

    /**
     * test overwrite identity by field'id
     * 
     */
    public void testOverwriteFieldId() {
        try {
            // load test data
            loadData("overwrite_field_id.xml", "overwrite_field_id.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH) };
            String ddl = _generator.generateCreate();

            boolean b = _expectedDDL.match(_engine, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testOverwriteFieldId: " + e.getMessage(), false);
        }
    }

    /**
     * test to many keys reference
     * 
     */
    public void testManyKeysReference() {
        try {
            // load test data
            loadData("many_keys_reference.xml", "many_keys_reference.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            // test
            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH) };

            String ddl = _generator.generateCreate();

            boolean b = _expectedDDL.match(_engine, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testManyKeysReference: " + e.getMessage(), false);
        }
    }

    /**
     * test to many class's keys reference
     * 
     */
    public void testManyClassKeysReference() {
        try {
            // load test data
            loadData("many_class_keys_reference.xml",
                    "many_class_keys_reference.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            // test
            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH) };

            String ddl = _generator.generateCreate();

            boolean b = _expectedDDL.match(_engine, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testManyClassKeysReference: " + e.getMessage(), false);
        }
    }

    /**
     * test to many class's keys reference
     * 
     */
    public void testCreateSchema() {
        try {
            loadData("empty.xml", "create_schema.exp.xml");
            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            String schema = "test";
            conf.setProperty(Configuration.SCHEMA_NAME_KEY, schema);

            String ddl = _generator.getSchema().toDDL();
            boolean b = _expectedDDL.match(_engine, 0, ddl, new String[] {schema});
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);

            schema = "";
            conf.setProperty(Configuration.SCHEMA_NAME_KEY, schema);
            ddl = _generator.getSchema().toDDL();
            b = _expectedDDL.match(_engine, 1, ddl, new String[] {});
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);

        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testCreateSchema: " + e.getMessage(), false);
        }
    }

    /**
     * test for create index, there are no ddl createdfor now
     * 
     */
    public void testCreateIndex() {
        try {
            // load test data
            loadData("index_creation.xml", "index_creation.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            String ddl = _generator.generateIndex();

            boolean b = _expectedDDL.match(_engine, ddl);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testCreateIndex: " + e.getMessage(), false);
        }
    }

    /**
     * 
     * @return Returns the dbConf.
     */
    public final String getDbConf() {
        return _dbConf;
    }

    /**
     * Set the dbConf by _dbConf.
     * 
     * @param dbConf db conf
     */
    public final void setDbConf(final String dbConf) {
        _dbConf = dbConf;
    }

    /**
     * 
     * @return Returns the engine.
     */
    public final String getEngine() {
        return _engine;
    }

    /**
     * Set the engine by _engine.
     * 
     * @param engine db engine
     */
    public final void setEngine(final String engine) {
        _engine = engine;
    }

    /**
     * 
     * @return Returns the expectedDDL.
     */
    public final ExpectedResult getExpectedDDL() {
        return _expectedDDL;
    }

    /**
     * Set the expectedDDL by _expectedDDL.
     * 
     * @param expectedDDL expected ddl
     */
    public final void setExpectedDDL(final ExpectedResult expectedDDL) {
        _expectedDDL = expectedDDL;
    }

    /**
     * 
     * @return Returns the generator.
     */
    public final AbstractGenerator getGenerator() {
        return _generator;
    }

    /**
     * Set the generator by _generator.
     * 
     * @param generator generator
     */
    public final void setGenerator(final AbstractGenerator generator) {
        _generator = generator;
    }

    /**
     * 
     * @return Returns the globalConf.
     */
    public final String getGlobalConf() {
        return _globalConf;
    }

    /**
     * Set the globalConf by _globalConf.
     * 
     * @param globalConf global conf
     */
    public final void setGlobalConf(final String globalConf) {
        _globalConf = globalConf;
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
     * @param mapping mapping
     */
    public final void setMapping(final Mapping mapping) {
        _mapping = mapping;
    }

}

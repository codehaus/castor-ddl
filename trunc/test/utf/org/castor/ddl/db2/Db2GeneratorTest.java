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

package utf.org.castor.ddl.db2;

import java.util.logging.Logger;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.castor.ddl.Configuration;
import org.castor.ddl.GeneratorException;
import org.castor.ddl.TypeMapper;
import org.castor.ddl.db2.Db2Generator;
import org.castor.ddl.db2.Db2TypeMapper;

import utf.org.castor.ddl.BaseGeneratorTest;
import utf.org.castor.ddl.ExpectedResult;

/**
 * Db2 generator test
 * <br/>Created on Jun 13, 2006 - 6:56:15 PM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public final class Db2GeneratorTest extends BaseGeneratorTest {
    /** Prostfix of length parameters for types in ddl.properties file. */
    protected static final String PARAM_POSTFIX_SUFIXE = "_sufixe";

    /**
     * Constructor for Db2GeneratorTest
     * 
     * @param testcase test case
     */
    public Db2GeneratorTest(final String testcase) {
        super(testcase);
    }

    /**
     * this constructor aims to reuse all test scenerios, except _engine
     * Constructor for PostgresqlGeneratorTest
     * 
     * @param testcase test case
     * @param useDBEngine is use mysql engine
     */
    public Db2GeneratorTest(final String testcase, final boolean useDBEngine) {
        super(testcase);
        if (useDBEngine) {
            setEngine(ExpectedResult.ENGINE_DB2);
        }
    }

    /**
     * 
     * @return Test
     * @throws Exception exception
     */
    public static Test suite() throws Exception {
        TestSuite suite = new TestSuite("All org.castor.ddl.Db2 tests");

        // schema test
        suite.addTest(new Db2GeneratorTest("testCreateSchema", true));
        // drop test
        suite.addTest(new Db2GeneratorTest("testDropTable", false));
        
        // table test
        suite.addTest(new Db2GeneratorTest("testSingleTable", false));
        suite.addTest(new Db2GeneratorTest("testMultipleTable", false));
        suite.addTest(new Db2GeneratorTest("testIgnoredTable", false));
        suite.addTest(new Db2GeneratorTest("testNoTable", false));

        //field test
        suite.addTest(new Db2GeneratorTest("testSingleField", false));
        suite.addTest(new Db2GeneratorTest("testSingleFieldForAll", true));
        suite.addTest(new Db2GeneratorTest("testSingleFieldForAllExceptBit", 
                true));        
        suite.addTest(new Db2GeneratorTest("testIgnoredField", false));
        suite.addTest(new Db2GeneratorTest("testNoField", false));
        suite.addTest(new Db2GeneratorTest("testManyKeysReference", false));
        suite.addTest(new Db2GeneratorTest("testManyClassKeysReference", false));
        suite.addTest(new Db2GeneratorTest("test2LevelsReference", false));
        
        // primary key test
        suite.addTest(new Db2GeneratorTest("testClassId", true));
        suite.addTest(new Db2GeneratorTest("testClassMultipleId", true));
        suite.addTest(new Db2GeneratorTest("testFieldId", true));
        suite.addTest(new Db2GeneratorTest("testFieldMultipleId", true));
        suite.addTest(new Db2GeneratorTest("testOverwriteFieldId", false));
        suite.addTest(new Db2GeneratorTest("testNoId", false));

        // foreign key test
        suite.addTest(new Db2GeneratorTest("testOneOneRelationship", false));
        suite.addTest(new Db2GeneratorTest("testOneManyRelationship", false));
        suite.addTest(new Db2GeneratorTest("testManyManyRelationship", false));

        // index test - 
        suite.addTest(new Db2GeneratorTest("testCreateIndex", false));        
        
        // key generator test
        suite.addTest(new Db2GeneratorTest("testKeyGenIdentity", true));
        suite.addTest(new Db2GeneratorTest("testKeyGenHighLow", false));
        suite.addTest(new Db2GeneratorTest("testKeyGenMax", false));
        suite.addTest(new Db2GeneratorTest("testKeyGenSequence", true));
        suite.addTest(new Db2GeneratorTest("testKeyGenUUID", false));

        return suite;
    }

    /** 
     * @see junit.framework.TestCase#setUp()
     * {@inheritDoc}
     */
    protected void setUp() throws Exception {
        super.setUp();
        setDbConf(Db2GeneratorTest.class.getResource("db2.properties").getFile());
        setGenerator(new Db2Generator(getGlobalConf(), getDbConf()));
        getGenerator().setMapping(getMapping());
    }

    /** 
     * @see junit.framework.TestCase#tearDown()
     * {@inheritDoc}
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        setGenerator(null);
    }

    /**
     * @see utf.org.castor.ddl.BaseGeneratorTest#testSingleFieldForAll()
     * {@inheritDoc}
     */
    public void testSingleFieldForAll() {
        try {
            // load test data
            loadData("single_field_for_all.xml", "single_field_for_all.exp.xml");

            // setup
            Configuration conf = getGenerator().getConf();
            TypeMapper typeMapper = new Db2TypeMapper(conf);
            getGenerator().setTypeMapper(typeMapper);

            try {
                getGenerator().generateCreate();
                assertTrue("bit type does not support, expected an exception", false);
            } catch (GeneratorException e) {
                Logger.global.warning("test bit type does not support, " 
                        + "expected an exception");
            }
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testSingleFieldForAll: " + e.getMessage(), false);
        }
    }
    
    /**
     * Create a table with 23 fields represented to each data type
     * 
     */
    public void testSingleFieldForAllExceptBit() {
        try {
            // load test data
            loadData("single_field_for_all_except_bit.xml", 
                    "single_field_for_all_except_bit.exp.xml");

            // setup
            Configuration conf = getGenerator().getConf();
            TypeMapper typeMapper = new Db2TypeMapper(conf);
            getGenerator().setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "tinyint" // 0
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "smallint"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "bigint"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "float"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "float" // 5
                            + PARAM_POSTFIX_DECIMALS),
                    conf.getInteger(PARAM_PREFIX + "double"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "double"
                            + PARAM_POSTFIX_DECIMALS),
                    conf.getInteger(PARAM_PREFIX + "real"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "real"
                            + PARAM_POSTFIX_DECIMALS),
                    conf.getInteger(PARAM_PREFIX + "numeric" // 10
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "numeric"
                            + PARAM_POSTFIX_DECIMALS),
                    conf.getInteger(PARAM_PREFIX + "decimal"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "decimal"
                            + PARAM_POSTFIX_DECIMALS),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH),
                    conf.getInteger(PARAM_PREFIX + "varchar" // 15
                            + PARAM_POSTFIX_LENGTH),
                    conf.getInteger(PARAM_PREFIX + "longvarchar"
                            + PARAM_POSTFIX_LENGTH),
                    conf.getInteger(PARAM_PREFIX + "timestamp"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "binary"
                            + PARAM_POSTFIX_LENGTH),
                    conf.getInteger(PARAM_PREFIX + "varbinary"
                            + PARAM_POSTFIX_LENGTH),
                    conf.getStringValue(PARAM_PREFIX + "other" // 20
                            + PARAM_POSTFIX_LENGTH),
                    conf.getStringValue(PARAM_PREFIX + "other"
                            + PARAM_POSTFIX_SUFIXE),
                    conf.getStringValue(PARAM_PREFIX + "javaobject"
                            + PARAM_POSTFIX_LENGTH),
                    conf.getStringValue(PARAM_PREFIX + "javaobject"
                            + PARAM_POSTFIX_SUFIXE),
                    conf.getStringValue(PARAM_PREFIX + "blob"
                            + PARAM_POSTFIX_LENGTH),
                    conf.getStringValue(PARAM_PREFIX + "blob" //25
                            + PARAM_POSTFIX_SUFIXE),
                    conf.getStringValue(PARAM_PREFIX + "clob"
                            + PARAM_POSTFIX_LENGTH),
                    conf.getStringValue(PARAM_PREFIX + "clob"
                            + PARAM_POSTFIX_SUFIXE) };

            String ddl = getGenerator().generateCreate();
            boolean b = getExpectedDDL().match(getEngine(), ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + getExpectedDDL().getMessage(), b);
            
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testSingleFieldForAll: " + e.getMessage(), false);
        }
    }


}

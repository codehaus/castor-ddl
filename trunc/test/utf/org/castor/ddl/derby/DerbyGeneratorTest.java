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

package utf.org.castor.ddl.derby;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.castor.ddl.Configuration;
import org.castor.ddl.KeyGenNotSupportException;
import org.castor.ddl.TypeMapper;
import org.castor.ddl.derby.DerbyGenerator;
import org.castor.ddl.derby.DerbyTypeMapper;

import utf.org.castor.ddl.BaseGeneratorTest;
import utf.org.castor.ddl.ExpectedResult;

/**
 * 
 * Created on Jun 13, 2006 - 6:56:15 PM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public final class DerbyGeneratorTest extends BaseGeneratorTest {
    /** Prostfix of length parameters for types in ddl.properties file. */
    protected static final String PARAM_POSTFIX_SUFIXE = "_sufixe";

    /**
     * Constructor for DerbyGeneratorTest
     * 
     * @param testcase test case
     */
    public DerbyGeneratorTest(final String testcase) {
        super(testcase);
    }

    /**
     * this constructor aims to reuse all test scenerios, except _engine
     * Constructor for DerbyGeneratorTest
     * 
     * @param testcase test case
     * @param useDBEngine is use mysql engine
     */
    public DerbyGeneratorTest(final String testcase, final boolean useDBEngine) {
        super(testcase);
        if (useDBEngine) {
            setEngine(ExpectedResult.ENGINE_DERBY);
        }
    }

    /**
     * 
     * @return Test
     * @throws Exception exception
     */
    public static Test suite() throws Exception {
        TestSuite suite = new TestSuite("All org.castor.ddl.derby tests");

        // schema test
        suite.addTest(new DerbyGeneratorTest("testCreateSchema", true));
        // drop test
        suite.addTest(new DerbyGeneratorTest("testDropTable", false));
        
        // table test
        suite.addTest(new DerbyGeneratorTest("testSingleTable", false));
        suite.addTest(new DerbyGeneratorTest("testMultipleTable", false));
        suite.addTest(new DerbyGeneratorTest("testIgnoredTable", false));
        suite.addTest(new DerbyGeneratorTest("testNoTable", false));

        //field test
        suite.addTest(new DerbyGeneratorTest("testSingleField", false));
        suite.addTest(new DerbyGeneratorTest("testSingleFieldForAll", true));
        suite.addTest(new DerbyGeneratorTest("testIgnoredField", false));
        suite.addTest(new DerbyGeneratorTest("testNoField", false));
        suite.addTest(new DerbyGeneratorTest("testManyKeysReference", false));
        suite.addTest(new DerbyGeneratorTest("testManyClassKeysReference", false));
        suite.addTest(new DerbyGeneratorTest("test2LevelsReference", false));
        
        // primary key test
        suite.addTest(new DerbyGeneratorTest("testClassId", false));
        suite.addTest(new DerbyGeneratorTest("testClassMultipleId", false));
        suite.addTest(new DerbyGeneratorTest("testFieldId", false));
        suite.addTest(new DerbyGeneratorTest("testFieldMultipleId", false));
        suite.addTest(new DerbyGeneratorTest("testOverwriteFieldId", false));
        suite.addTest(new DerbyGeneratorTest("testNoId", false));

        // foreign key test
        suite.addTest(new DerbyGeneratorTest("testOneOneRelationship", false));
        suite.addTest(new DerbyGeneratorTest("testOneManyRelationship", false));
        suite.addTest(new DerbyGeneratorTest("testManyManyRelationship", false));

        // index test - 
        suite.addTest(new DerbyGeneratorTest("testCreateIndex", false));        
        
        // key generator test
        suite.addTest(new DerbyGeneratorTest("testKeyGenIdentity", true));
        suite.addTest(new DerbyGeneratorTest("testKeyGenHighLow", false));
        suite.addTest(new DerbyGeneratorTest("testKeyGenMax", false));
        suite.addTest(new DerbyGeneratorTest("testKeyGenSequence", false));
        suite.addTest(new DerbyGeneratorTest("testKeyGenUUID", false));
        
        // trigger test - not yet

        return suite;
    }

    /** 
     * @see junit.framework.TestCase#setUp()
     * {@inheritDoc}
     */
    protected void setUp() throws Exception {
        super.setUp();
        setGlobalConf("conf/ddl.properties");
        setDbConf("conf/derby.properties");
        setGenerator(new DerbyGenerator(getGlobalConf(), getDbConf()));
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
     * Create a table with 23 fields represented to each data type
     * 
     */
    public void testSingleFieldForAll() {
        try {
            // load test data
            loadData("single_field_for_all.xml", "single_field_for_all.exp.xml");

            // setup
            Configuration conf = getGenerator().getConf();
            TypeMapper typeMapper = new DerbyTypeMapper(conf);
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
                    conf.getInteger(PARAM_PREFIX + "other" // 20
                            + PARAM_POSTFIX_LENGTH),
                    conf.getStringValue(PARAM_PREFIX + "other"
                            + PARAM_POSTFIX_SUFIXE),
                    conf.getInteger(PARAM_PREFIX + "javaobject"
                            + PARAM_POSTFIX_LENGTH),
                    conf.getStringValue(PARAM_PREFIX + "javaobject"
                            + PARAM_POSTFIX_SUFIXE),
                    conf.getInteger(PARAM_PREFIX + "blob"
                            + PARAM_POSTFIX_LENGTH),
                    conf.getStringValue(PARAM_PREFIX + "blob" //25
                            + PARAM_POSTFIX_SUFIXE),
                    conf.getInteger(PARAM_PREFIX + "clob"
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
    /**
     * @see utf.org.castor.ddl.BaseGeneratorTest#testKeyGenSequence()
     * {@inheritDoc}
     */
    public void testKeyGenSequence() {
        try {
            // load test data
            loadData("key_gen_sequence.xml", "key_gen_sequence.exp.xml");

            // setup
            Configuration conf = getGenerator().getConf();
            TypeMapper typeMapper = new DerbyTypeMapper(conf);
            getGenerator().setTypeMapper(typeMapper);

            // test
            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH) };

            String ddl = getGenerator().generateCreate();

            boolean b = getExpectedDDL().match(getEngine(), 0, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + getExpectedDDL().getMessage(), b);

            try {
                ddl = getGenerator().generateKeyGenerator();
                b = getExpectedDDL().match(getEngine(), 1, ddl, params);
                assertTrue("expected KeyGenNotSupportException", false);
            } catch (KeyGenNotSupportException ex) {
                //expected exception
            }

        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testKeyGenSequence: " + e.getMessage(), false);
        }
    }

}

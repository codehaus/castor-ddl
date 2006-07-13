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

package utf.org.castor.ddl.mysql;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.castor.ddl.Configuration;
import org.castor.ddl.KeyGenNotSupportException;
import org.castor.ddl.TypeMapper;
import org.castor.ddl.mysql.MysqlGenerator;
import org.castor.ddl.mysql.MysqlTypeMapper;

import utf.org.castor.ddl.BaseGeneratorTest;
import utf.org.castor.ddl.ExpectedResult;

/**
 * 
 * Created on Jun 13, 2006 - 6:56:15 PM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public final class MysqlGeneratorTest extends BaseGeneratorTest {
    /**
     * Constructor for MysqlGeneratorTest
     * 
     * @param testcase test case
     */
    public MysqlGeneratorTest(final String testcase) {
        super(testcase);
    }

    /**
     * this constructor aims to reuse all test scenerios, except _engine
     * Constructor for MysqlGeneratorTest
     * 
     * @param testcase test case
     * @param useDBlEngine is use mysql engine
     */
    public MysqlGeneratorTest(final String testcase, final boolean useDBlEngine) {
        super(testcase);
        if (useDBlEngine) {
            setEngine(ExpectedResult.ENGINE_MYSQL);
        }
    }

    /**
     * 
     * @return Test
     * @throws Exception exception
     */
    public static Test suite() throws Exception {
        TestSuite suite = new TestSuite("All org.castor.ddl.mysql tests");

        // schema test
        suite.addTest(new MysqlGeneratorTest("testCreateSchema", true));
//         drop test
        suite.addTest(new MysqlGeneratorTest("testDropTable", true));
        
        // table test
        suite.addTest(new MysqlGeneratorTest("testSingleTable", true));
        suite.addTest(new MysqlGeneratorTest("testMultipleTable", true));
        suite.addTest(new MysqlGeneratorTest("testIgnoredTable", true));
        suite.addTest(new MysqlGeneratorTest("testNoTable", false));

        //field test
        suite.addTest(new MysqlGeneratorTest("testSingleField", true));
        suite.addTest(new MysqlGeneratorTest("testSingleFieldForAll", true));
        suite.addTest(new MysqlGeneratorTest("testIgnoredField", true));
        suite.addTest(new MysqlGeneratorTest("testNoField", false));
        suite.addTest(new MysqlGeneratorTest("testManyKeysReference", true));
        suite.addTest(new MysqlGeneratorTest("testManyClassKeysReference", true));
        suite.addTest(new MysqlGeneratorTest("test2LevelsReference", true));
        
        // primary key test
        suite.addTest(new MysqlGeneratorTest("testClassId", true));
        suite.addTest(new MysqlGeneratorTest("testClassMultipleId", true));
        suite.addTest(new MysqlGeneratorTest("testFieldId", true));
        suite.addTest(new MysqlGeneratorTest("testFieldMultipleId", true));
        suite.addTest(new MysqlGeneratorTest("testOverwriteFieldId", true));
        suite.addTest(new MysqlGeneratorTest("testNoId", true));

        // foreign key test
        suite.addTest(new MysqlGeneratorTest("testOneOneRelationship", true));
        suite.addTest(new MysqlGeneratorTest("testOneManyRelationship", true));
        suite.addTest(new MysqlGeneratorTest("testManyManyRelationship", true));

        // index test - 
        suite.addTest(new MysqlGeneratorTest("testCreateIndex", false));        
        
        // key generator test
        suite.addTest(new MysqlGeneratorTest("testKeyGenIdentity", true));
        suite.addTest(new MysqlGeneratorTest("testKeyGenHighLow", true));
        suite.addTest(new MysqlGeneratorTest("testKeyGenMax", true));
        suite.addTest(new MysqlGeneratorTest("testKeyGenSequence", true));
        suite.addTest(new MysqlGeneratorTest("testKeyGenUUID", true));

        return suite;
    }

    /** 
     * @see junit.framework.TestCase#setUp()
     * {@inheritDoc}
     */
    protected void setUp() throws Exception {
        super.setUp();
        setGlobalConf("conf/ddl.properties");
        setDbConf("conf/mysql.properties");
        setGenerator(new MysqlGenerator(getGlobalConf(), getDbConf()));
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
     * @see utf.org.castor.ddl.BaseGeneratorTest#testKeyGenSequence()
     * {@inheritDoc}
     */
    public void testKeyGenSequence() {
        try {
            // load test data
            loadData("key_gen_sequence.xml", "key_gen_sequence.exp.xml");

            // setup
            Configuration conf = getGenerator().getConf();
            TypeMapper typeMapper = new MysqlTypeMapper(conf);
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

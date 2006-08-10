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

package utf.org.castor.ddl.sapdb;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.castor.ddl.Configuration;
import org.castor.ddl.TypeMapper;
import org.castor.ddl.sapdb.SapdbGenerator;
import org.castor.ddl.sapdb.SapdbTypeMapper;

import utf.org.castor.ddl.BaseGeneratorTest;
import utf.org.castor.ddl.ExpectedResult;

/**
 * SapDB generator test
 * <br/>Created on Jun 13, 2006 - 6:56:15 PM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public final class SapdbGeneratorTest extends BaseGeneratorTest {
    /**
     * Constructor for SapdbGeneratorTest
     * 
     * @param testcase test case
     */
    public SapdbGeneratorTest(final String testcase) {
        super(testcase);
    }

    /**
     * this constructor aims to reuse all test scenerios, except _engine
     * Constructor for DerbyGeneratorTest
     * 
     * @param testcase test case
     * @param useDBEngine is use mysql engine
     */
    public SapdbGeneratorTest(final String testcase, final boolean useDBEngine) {
        super(testcase);
        if (useDBEngine) {
            setEngine(ExpectedResult.ENGINE_SAPDB);
        }
    }

    /**
     * 
     * @return Test
     * @throws Exception exception
     */
    public static Test suite() throws Exception {
        TestSuite suite = new TestSuite("All org.castor.ddl.sapdb tests");

        // schema test
        suite.addTest(new SapdbGeneratorTest("testCreateSchema", true));
        // drop test
        suite.addTest(new SapdbGeneratorTest("testDropTable", false));
        
        // table test
        suite.addTest(new SapdbGeneratorTest("testSingleTable", false));
        suite.addTest(new SapdbGeneratorTest("testMultipleTable", false));
        suite.addTest(new SapdbGeneratorTest("testIgnoredTable", false));
        suite.addTest(new SapdbGeneratorTest("testNoTable", false));

        //field test
        suite.addTest(new SapdbGeneratorTest("testSingleField", false));
        suite.addTest(new SapdbGeneratorTest("testSingleFieldForAll", true));
        suite.addTest(new SapdbGeneratorTest("testIgnoredField", false));
        suite.addTest(new SapdbGeneratorTest("testNoField", false));
        suite.addTest(new SapdbGeneratorTest("testManyKeysReference", false));
        suite.addTest(new SapdbGeneratorTest("testManyClassKeysReference", false));
        suite.addTest(new SapdbGeneratorTest("test2LevelsReference", false));
        
        // primary key test
        suite.addTest(new SapdbGeneratorTest("testClassId", false));
        suite.addTest(new SapdbGeneratorTest("testClassMultipleId", false));
        suite.addTest(new SapdbGeneratorTest("testFieldId", false));
        suite.addTest(new SapdbGeneratorTest("testFieldMultipleId", false));
        suite.addTest(new SapdbGeneratorTest("testOverwriteFieldId", false));
        suite.addTest(new SapdbGeneratorTest("testNoId", false));

        // foreign key test
        suite.addTest(new SapdbGeneratorTest("testOneOneRelationship", false));
        suite.addTest(new SapdbGeneratorTest("testOneManyRelationship", false));
        suite.addTest(new SapdbGeneratorTest("testManyManyRelationship", false));

        // index test - 
        suite.addTest(new SapdbGeneratorTest("testCreateIndex", false));        
        
        // key generator test
        suite.addTest(new SapdbGeneratorTest("testKeyGenIdentity", false));
        suite.addTest(new SapdbGeneratorTest("testKeyGenHighLow", false));
        suite.addTest(new SapdbGeneratorTest("testKeyGenMax", false));
        suite.addTest(new SapdbGeneratorTest("testKeyGenSequence", true));
        suite.addTest(new SapdbGeneratorTest("testKeyGenUUID", false));
        
        // trigger test - not yet

        return suite;
    }

    /** 
     * @see junit.framework.TestCase#setUp()
     * {@inheritDoc}
     */
    protected void setUp() throws Exception {
        super.setUp();
        setDbConf(SapdbGeneratorTest.class.getResource("sapdb.properties").getFile());
        setGenerator(new SapdbGenerator(getGlobalConf(), getDbConf()));
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
     * @see utf.org.castor.ddl.BaseGeneratorTest#testKeyGenIdentity()
     * {@inheritDoc}
     */
    public void testKeyGenIdentity() {
        try {
            // load test data
            loadData("key_gen_identity.xml", "key_gen_identity.exp.xml");

            // setup
            Configuration conf = getGenerator().getConf();
            TypeMapper typeMapper = new SapdbTypeMapper(conf);
            getGenerator().setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger(PARAM_PREFIX + "integer"
                            + PARAM_POSTFIX_PRECISION),
                    conf.getInteger(PARAM_PREFIX + "char"
                            + PARAM_POSTFIX_LENGTH) };

                String ddl = getGenerator().generateCreate();
                boolean b = getExpectedDDL().match(getEngine(), 0, ddl, params);
                assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                        + getExpectedDDL().getMessage(), b);

                ddl = getGenerator().generateKeyGenerator();
                b = getExpectedDDL().match(getEngine(), 1, ddl, params);
                assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                        + getExpectedDDL().getMessage(), b);


        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testKeyGenIdentity: " + e.getMessage(), false);
        }
    }

}

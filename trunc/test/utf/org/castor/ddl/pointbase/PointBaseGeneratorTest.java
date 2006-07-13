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

package utf.org.castor.ddl.pointbase;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.castor.ddl.Configuration;
import org.castor.ddl.KeyGenNotSupportException;
import org.castor.ddl.TypeMapper;
import org.castor.ddl.pointbase.PointBaseGenerator;
import org.castor.ddl.pointbase.PointBaseTypeMapper;

import utf.org.castor.ddl.BaseGeneratorTest;
import utf.org.castor.ddl.ExpectedResult;

/**
 * 
 * Created on Jun 13, 2006 - 6:56:15 PM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public final class PointBaseGeneratorTest extends BaseGeneratorTest {
    /**
     * Constructor for PointBaseGeneratorTest
     * 
     * @param testcase test case
     */
    public PointBaseGeneratorTest(final String testcase) {
        super(testcase);
    }

    /**
     * this constructor aims to reuse all test scenerios, except _engine
     * Constructor for PointBaseGeneratorTest
     * 
     * @param testcase test case
     * @param useDBlEngine is use mysql engine
     */
    public PointBaseGeneratorTest(final String testcase, final boolean useDBlEngine) {
        super(testcase);
        if (useDBlEngine) {
            setEngine(ExpectedResult.ENGINE_POINTBASE);
        }
    }

    /**
     * 
     * @return Test
     * @throws Exception exception
     */
    public static Test suite() throws Exception {
        TestSuite suite = new TestSuite("All org.castor.ddl.pointbase tests");

         //schema test
        suite.addTest(new PointBaseGeneratorTest("testCreateSchema", true));
        // drop test
        suite.addTest(new PointBaseGeneratorTest("testDropTable", false));
        
        // table test
        suite.addTest(new PointBaseGeneratorTest("testSingleTable", false));
        suite.addTest(new PointBaseGeneratorTest("testMultipleTable", false));
        suite.addTest(new PointBaseGeneratorTest("testIgnoredTable", false));
        suite.addTest(new PointBaseGeneratorTest("testNoTable", false));

        //field test
        suite.addTest(new PointBaseGeneratorTest("testSingleField", false));
        suite.addTest(new PointBaseGeneratorTest("testSingleFieldForAll", true));
        suite.addTest(new PointBaseGeneratorTest("testIgnoredField", false));
        suite.addTest(new PointBaseGeneratorTest("testNoField", false));
        suite.addTest(new PointBaseGeneratorTest("testManyKeysReference", false));
        suite.addTest(new PointBaseGeneratorTest("testManyClassKeysReference", false));
        suite.addTest(new PointBaseGeneratorTest("test2LevelsReference", false));
        
        // primary key test
        suite.addTest(new PointBaseGeneratorTest("testClassId", true));
        suite.addTest(new PointBaseGeneratorTest("testClassMultipleId", true));
        suite.addTest(new PointBaseGeneratorTest("testFieldId", true));
        suite.addTest(new PointBaseGeneratorTest("testFieldMultipleId", true));
        suite.addTest(new PointBaseGeneratorTest("testOverwriteFieldId", false));
        suite.addTest(new PointBaseGeneratorTest("testNoId", false));

        // foreign key test
        suite.addTest(new PointBaseGeneratorTest("testOneOneRelationship", false));
        suite.addTest(new PointBaseGeneratorTest("testOneManyRelationship", false));
        suite.addTest(new PointBaseGeneratorTest("testManyManyRelationship", false));

        // index test - 
        suite.addTest(new PointBaseGeneratorTest("testCreateIndex", false));        
        
        // key generator test
        suite.addTest(new PointBaseGeneratorTest("testKeyGenIdentity", true));
        suite.addTest(new PointBaseGeneratorTest("testKeyGenHighLow", false));
        suite.addTest(new PointBaseGeneratorTest("testKeyGenMax", false));
        suite.addTest(new PointBaseGeneratorTest("testKeyGenSequence", false));
        suite.addTest(new PointBaseGeneratorTest("testKeyGenUUID", false));
        
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
        setDbConf("conf/mssql.properties");
        setGenerator(new PointBaseGenerator(getGlobalConf(), getDbConf()));
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
            TypeMapper typeMapper = new PointBaseTypeMapper(conf);
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

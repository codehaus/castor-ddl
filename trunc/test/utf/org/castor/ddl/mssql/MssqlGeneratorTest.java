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

package utf.org.castor.ddl.mssql;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.castor.ddl.Configuration;
import org.castor.ddl.TypeMapper;
import org.castor.ddl.engine.mssql.MssqlGenerator;
import org.castor.ddl.engine.mssql.MssqlTypeMapper;

import utf.org.castor.ddl.BaseGeneratorTest;
import utf.org.castor.ddl.ExpectedResult;

/**
 * Mssql generator test
 * <br/>Created on Jun 13, 2006 - 6:56:15 PM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public final class MssqlGeneratorTest extends BaseGeneratorTest {
    /**
     * Constructor for MssqlGeneratorTest
     * 
     * @param testcase test case
     */
    public MssqlGeneratorTest(final String testcase) {
        super(testcase);
    }

    /**
     * this constructor aims to reuse all test scenerios, except _engine
     * Constructor for MssqlGeneratorTest
     * 
     * @param testcase test case
     * @param useDBlEngine is use mysql engine
     */
    public MssqlGeneratorTest(final String testcase, final boolean useDBlEngine) {
        super(testcase);
        if (useDBlEngine) {
            setEngine(ExpectedResult.ENGINE_MSSQL);
        }
    }

    /**
     * 
     * @return Test
     * @throws Exception exception
     */
    public static Test suite() throws Exception {
        TestSuite suite = new TestSuite("All org.castor.ddl.mssql tests");

         //schema test
        suite.addTest(new MssqlGeneratorTest("testCreateSchema", true));
        // drop test
        suite.addTest(new MssqlGeneratorTest("testDropTable", false));
        
        // table test
        suite.addTest(new MssqlGeneratorTest("testSingleTable", false));
        suite.addTest(new MssqlGeneratorTest("testMultipleTable", false));
        suite.addTest(new MssqlGeneratorTest("testIgnoredTable", false));
        suite.addTest(new MssqlGeneratorTest("testNoTable", false));

        //field test
        suite.addTest(new MssqlGeneratorTest("testSingleField", false));
        suite.addTest(new MssqlGeneratorTest("testSingleFieldForAll", true));
        suite.addTest(new MssqlGeneratorTest("testIgnoredField", false));
        suite.addTest(new MssqlGeneratorTest("testNoField", false));
        suite.addTest(new MssqlGeneratorTest("testManyKeysReference", false));
        suite.addTest(new MssqlGeneratorTest("testManyClassKeysReference", false));
        suite.addTest(new MssqlGeneratorTest("test2LevelsReference", false));
        
        // primary key test
        suite.addTest(new MssqlGeneratorTest("testClassId", true));
        suite.addTest(new MssqlGeneratorTest("testClassMultipleId", true));
        suite.addTest(new MssqlGeneratorTest("testFieldId", true));
        suite.addTest(new MssqlGeneratorTest("testFieldMultipleId", true));
        suite.addTest(new MssqlGeneratorTest("testOverwriteFieldId", false));
        suite.addTest(new MssqlGeneratorTest("testNoId", false));

        // foreign key test
        suite.addTest(new MssqlGeneratorTest("testOneOneRelationship", false));
        suite.addTest(new MssqlGeneratorTest("testOneManyRelationship", false));
        suite.addTest(new MssqlGeneratorTest("testManyManyRelationship", false));

        // index test - 
        suite.addTest(new MssqlGeneratorTest("testCreateIndex", false));        
        
        // key generator test
        suite.addTest(new MssqlGeneratorTest("testKeyGenIdentity", true));
        suite.addTest(new MssqlGeneratorTest("testKeyGenHighLow", false));
        suite.addTest(new MssqlGeneratorTest("testKeyGenMax", false));
        suite.addTest(new MssqlGeneratorTest("testKeyGenSequence", false));
        suite.addTest(new MssqlGeneratorTest("testKeyGenUUID", false));
        
        // trigger test - not yet

        return suite;
    }

    /** 
     * @see junit.framework.TestCase#setUp()
     * {@inheritDoc}
     */
    protected void setUp() throws Exception {
        super.setUp();
        setDbConf(MssqlGeneratorTest.class.getResource("mssql.properties").getFile());
        setGenerator(new MssqlGenerator(getGlobalConf(), getDbConf()));
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
            TypeMapper typeMapper = new MssqlTypeMapper(conf);
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

            ddl = getGenerator().generateKeyGenerator();
            b = getExpectedDDL().match(getEngine(), 1, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + getExpectedDDL().getMessage(), b);

        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("testKeyGenSequence: " + e.getMessage(), false);
        }
    }

}

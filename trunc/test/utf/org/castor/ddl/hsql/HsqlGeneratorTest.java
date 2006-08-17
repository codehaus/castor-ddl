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

package utf.org.castor.ddl.hsql;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.castor.ddl.Configuration;
import org.castor.ddl.engine.hsql.HsqlGenerator;

import utf.org.castor.ddl.BaseGeneratorTest;
import utf.org.castor.ddl.ExpectedResult;

/**
 * Hsql Generator test
 * <br/>Created on Jun 13, 2006 - 6:56:15 PM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public final class HsqlGeneratorTest extends BaseGeneratorTest {
    /**
     * Constructor for Db2GeneratorTest
     * 
     * @param testcase test case
     */
    public HsqlGeneratorTest(final String testcase) {
        super(testcase);
    }

    /**
     * this constructor aims to reuse all test scenerios, except _engine
     * Constructor for Db2GeneratorTest
     * 
     * @param testcase test case
     * @param useDBEngine is use mysql engine
     */
    public HsqlGeneratorTest(final String testcase, final boolean useDBEngine) {
        super(testcase);
        if (useDBEngine) {
            setEngine(ExpectedResult.ENGINE_HSQL);
        }
    }

    /**
     * 
     * @return Test
     * @throws Exception exception
     */
    public static Test suite() throws Exception {
        TestSuite suite = new TestSuite("All org.castor.ddl.PostgreSQL tests");

        // schema test
        suite.addTest(new HsqlGeneratorTest("testCreateSchema", true));
        // drop test
        suite.addTest(new HsqlGeneratorTest("testDropTable", true));
        
        // table test
        suite.addTest(new HsqlGeneratorTest("testSingleTable", false));
        suite.addTest(new HsqlGeneratorTest("testMultipleTable", false));
        suite.addTest(new HsqlGeneratorTest("testIgnoredTable", false));
        suite.addTest(new HsqlGeneratorTest("testNoTable", false));

        //field test
        suite.addTest(new HsqlGeneratorTest("testSingleField", false));
        suite.addTest(new HsqlGeneratorTest("testSingleFieldForAll", true));
        suite.addTest(new HsqlGeneratorTest("testIgnoredField", false));
        suite.addTest(new HsqlGeneratorTest("testNoField", false));
        suite.addTest(new HsqlGeneratorTest("testManyKeysReference", false));
        suite.addTest(new HsqlGeneratorTest("testManyClassKeysReference", false));
        suite.addTest(new HsqlGeneratorTest("test2LevelsReference", false));
        
        // primary key test
        suite.addTest(new HsqlGeneratorTest("testClassId", true));
        suite.addTest(new HsqlGeneratorTest("testClassMultipleId", true));
        suite.addTest(new HsqlGeneratorTest("testFieldId", true));
        suite.addTest(new HsqlGeneratorTest("testFieldMultipleId", true));
        suite.addTest(new HsqlGeneratorTest("testOverwriteFieldId", false));
        suite.addTest(new HsqlGeneratorTest("testNoId", false));

        // foreign key test
        suite.addTest(new HsqlGeneratorTest("testOneOneRelationship", false));
        suite.addTest(new HsqlGeneratorTest("testOneManyRelationship", false));
        suite.addTest(new HsqlGeneratorTest("testManyManyRelationship", false));

        // index test - 
        suite.addTest(new HsqlGeneratorTest("testCreateIndex", false));        
        
        // key generator test
        suite.addTest(new HsqlGeneratorTest("testKeyGenIdentity", true));
        suite.addTest(new HsqlGeneratorTest("testKeyGenHighLow", false));
        suite.addTest(new HsqlGeneratorTest("testKeyGenMax", false));
        suite.addTest(new HsqlGeneratorTest("testKeyGenSequence", true));
        suite.addTest(new HsqlGeneratorTest("testKeyGenUUID", false));

        return suite;
    }

    /** 
     * @see junit.framework.TestCase#setUp()
     * {@inheritDoc}
     */
    protected void setUp() throws Exception {
        super.setUp();
        setDbConf(HsqlGeneratorTest.class.getResource("hsql.properties").getFile());

        Configuration conf = new Configuration();
        conf.addProperties(getGlobalConf());
        conf.addProperties(getDbConf());
        setGenerator(new HsqlGenerator(conf));
        getGenerator().initialize();
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

}

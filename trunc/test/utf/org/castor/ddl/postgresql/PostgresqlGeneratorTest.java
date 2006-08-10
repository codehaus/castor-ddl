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

package utf.org.castor.ddl.postgresql;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.castor.ddl.postgresql.PostgresqlGenerator;

import utf.org.castor.ddl.BaseGeneratorTest;
import utf.org.castor.ddl.ExpectedResult;

/**
 * PostgreSQL generator test
 * <br/>Created on Jun 13, 2006 - 6:56:15 PM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public final class PostgresqlGeneratorTest extends BaseGeneratorTest {
    /**
     * Constructor for Db2GeneratorTest
     * 
     * @param testcase test case
     */
    public PostgresqlGeneratorTest(final String testcase) {
        super(testcase);
    }

    /**
     * this constructor aims to reuse all test scenerios, except _engine
     * Constructor for Db2GeneratorTest
     * 
     * @param testcase test case
     * @param useDBEngine is use mysql engine
     */
    public PostgresqlGeneratorTest(final String testcase, final boolean useDBEngine) {
        super(testcase);
        if (useDBEngine) {
            setEngine(ExpectedResult.ENGINE_POSTGRESQL);
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
        suite.addTest(new PostgresqlGeneratorTest("testCreateSchema", true));
        // drop test
        suite.addTest(new PostgresqlGeneratorTest("testDropTable", false));
        
        // table test
        suite.addTest(new PostgresqlGeneratorTest("testSingleTable", false));
        suite.addTest(new PostgresqlGeneratorTest("testMultipleTable", false));
        suite.addTest(new PostgresqlGeneratorTest("testIgnoredTable", false));
        suite.addTest(new PostgresqlGeneratorTest("testNoTable", false));

        //field test
        suite.addTest(new PostgresqlGeneratorTest("testSingleField", false));
        suite.addTest(new PostgresqlGeneratorTest("testSingleFieldForAll", true));
        suite.addTest(new PostgresqlGeneratorTest("testIgnoredField", false));
        suite.addTest(new PostgresqlGeneratorTest("testNoField", false));
        suite.addTest(new PostgresqlGeneratorTest("testManyKeysReference", false));
        suite.addTest(new PostgresqlGeneratorTest("testManyClassKeysReference", false));
        suite.addTest(new PostgresqlGeneratorTest("test2LevelsReference", false));
        
        // primary key test
        suite.addTest(new PostgresqlGeneratorTest("testClassId", false));
        suite.addTest(new PostgresqlGeneratorTest("testClassMultipleId", false));
        suite.addTest(new PostgresqlGeneratorTest("testFieldId", false));
        suite.addTest(new PostgresqlGeneratorTest("testFieldMultipleId", false));
        suite.addTest(new PostgresqlGeneratorTest("testOverwriteFieldId", false));
        suite.addTest(new PostgresqlGeneratorTest("testNoId", false));

        // foreign key test
        suite.addTest(new PostgresqlGeneratorTest("testOneOneRelationship", false));
        suite.addTest(new PostgresqlGeneratorTest("testOneManyRelationship", false));
        suite.addTest(new PostgresqlGeneratorTest("testManyManyRelationship", false));

        // index test - 
        suite.addTest(new PostgresqlGeneratorTest("testCreateIndex", false));        
        
        // key generator test
        suite.addTest(new PostgresqlGeneratorTest("testKeyGenIdentity", true));
        suite.addTest(new PostgresqlGeneratorTest("testKeyGenHighLow", false));
        suite.addTest(new PostgresqlGeneratorTest("testKeyGenMax", false));
        suite.addTest(new PostgresqlGeneratorTest("testKeyGenSequence", true));
        suite.addTest(new PostgresqlGeneratorTest("testKeyGenUUID", false));
       
        return suite;
    }

    /** 
     * @see junit.framework.TestCase#setUp()
     * {@inheritDoc}
     */
    protected void setUp() throws Exception {
        super.setUp();
        setDbConf(PostgresqlGeneratorTest.class.
                getResource("postgresql.properties").getFile());
        setGenerator(new PostgresqlGenerator(getGlobalConf(), getDbConf()));
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

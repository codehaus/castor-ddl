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

package utf.org.castor.ddl.oracle;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.castor.ddl.Configuration;
import org.castor.ddl.KeyGeneratorRegistry;
import org.castor.ddl.engine.oracle.OracleGenerator;

import utf.org.castor.ddl.BaseGeneratorTest;
import utf.org.castor.ddl.ExpectedResult;

/**
 * Oracle generator test
 * <br/>Created on Jun 13, 2006 - 6:56:15 PM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public final class OracleGeneratorTest extends BaseGeneratorTest {
    /**
     * Constructor for DerbyGeneratorTest
     * 
     * @param testcase test case
     */
    public OracleGeneratorTest(final String testcase) {
        super(testcase);
    }

    /**
     * this constructor aims to reuse all test scenerios, except _engine
     * Constructor for DerbyGeneratorTest
     * 
     * @param testcase test case
     * @param useDBEngine is use mysql engine
     */
    public OracleGeneratorTest(final String testcase, final boolean useDBEngine) {
        super(testcase);
        if (useDBEngine) {
            setEngine(ExpectedResult.ENGINE_ORACLE);
        }
    }

    /**
     * 
     * @return Test
     * @throws Exception exception
     */
    public static Test suite() throws Exception {
        TestSuite suite = new TestSuite("All org.castor.ddl.oracle tests");

        // schema test
        suite.addTest(new OracleGeneratorTest("testCreateSchema", true));
        // drop test
        suite.addTest(new OracleGeneratorTest("testDropTable", false));
        
        // table test
        suite.addTest(new OracleGeneratorTest("testSingleTable", false));
        suite.addTest(new OracleGeneratorTest("testMultipleTable", false));
        suite.addTest(new OracleGeneratorTest("testIgnoredTable", false));
        suite.addTest(new OracleGeneratorTest("testNoTable", false));

        //field test
        suite.addTest(new OracleGeneratorTest("testSingleField", false));
        suite.addTest(new OracleGeneratorTest("testSingleFieldForAll", true));
        suite.addTest(new OracleGeneratorTest("testIgnoredField", false));
        suite.addTest(new OracleGeneratorTest("testNoField", false));
        suite.addTest(new OracleGeneratorTest("testManyKeysReference", false));
        suite.addTest(new OracleGeneratorTest("testManyClassKeysReference", false));
        suite.addTest(new OracleGeneratorTest("test2LevelsReference", false));
        
        // primary key test
        suite.addTest(new OracleGeneratorTest("testClassId", false));
        suite.addTest(new OracleGeneratorTest("testClassMultipleId", false));
        suite.addTest(new OracleGeneratorTest("testFieldId", false));
        suite.addTest(new OracleGeneratorTest("testFieldMultipleId", false));
        suite.addTest(new OracleGeneratorTest("testOverwriteFieldId", false));
        suite.addTest(new OracleGeneratorTest("testNoId", false));

        // foreign key test
        suite.addTest(new OracleGeneratorTest("testOneOneRelationship", false));
        suite.addTest(new OracleGeneratorTest("testOneManyRelationship", false));
        suite.addTest(new OracleGeneratorTest("testManyManyRelationship", false));

        // index test - 
        suite.addTest(new OracleGeneratorTest("testCreateIndex", false));        
        
        // key generator test
        suite.addTest(new OracleGeneratorTest("testKeyGenHighLow", false));
        suite.addTest(new OracleGeneratorTest("testKeyGenMax", false));
        suite.addTest(new OracleGeneratorTest("testKeyGenSequence", true));
        suite.addTest(new OracleGeneratorTest("testKeyGenUUID", false));

        return suite;
    }

    /** 
     * {@inheritDoc}
     */
    protected void setUp() throws Exception {
        super.setUp();
        setDbConf(OracleGeneratorTest.class.getResource("oracle.properties").getFile());

        Configuration conf = new Configuration();
        conf.addProperties(getGlobalConf());
        conf.addProperties(getDbConf());
        setGenerator(new OracleGenerator(conf));

        KeyGeneratorRegistry keyGenRegistry = new KeyGeneratorRegistry(conf);
        getGenerator().setKeyGenRegistry(keyGenRegistry);
        
        getGenerator().initialize();
        getGenerator().setMapping(getMapping());
    }

    /** 
     * {@inheritDoc}
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        setGenerator(null);
    }
}

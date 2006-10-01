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
import org.castor.ddl.KeyGeneratorRegistry;
import org.castor.ddl.engine.sapdb.SapdbGenerator;

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
        suite.addTest(new SapdbGeneratorTest("testKeyGenHighLow", false));
        suite.addTest(new SapdbGeneratorTest("testKeyGenMax", false));
        suite.addTest(new SapdbGeneratorTest("testKeyGenSequence", true));
        suite.addTest(new SapdbGeneratorTest("testKeyGenUUID", false));
        
        // trigger test - not yet

        return suite;
    }

    /** 
     * {@inheritDoc}
     */
    protected void setUp() throws Exception {
        super.setUp();
        setDbConf(SapdbGeneratorTest.class.getResource("sapdb.properties").getFile());

        Configuration conf = new Configuration();
        conf.addProperties(getGlobalConf());
        conf.addProperties(getDbConf());
        setGenerator(new SapdbGenerator(conf));

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

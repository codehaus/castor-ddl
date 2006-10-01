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

package utf.org.castor.ddl.sybase;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.castor.ddl.Configuration;
import org.castor.ddl.KeyGeneratorRegistry;
import org.castor.ddl.engine.sybase.SybaseGenerator;

import utf.org.castor.ddl.BaseGeneratorTest;
import utf.org.castor.ddl.ExpectedResult;

/**
 * 
 * <br/>Created on Jun 13, 2006 - 6:56:15 PM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public final class SybaseGeneratorTest extends BaseGeneratorTest {
    /**
     * Constructor for SybaseGeneratorTest
     * 
     * @param testcase test case
     */
    public SybaseGeneratorTest(final String testcase) {
        super(testcase);
    }

    /**
     * this constructor aims to reuse all test scenerios, except _engine
     * Constructor for SybaseGeneratorTest
     * 
     * @param testcase test case
     * @param useDBlEngine is use mysql engine
     */
    public SybaseGeneratorTest(final String testcase, final boolean useDBlEngine) {
        super(testcase);
        if (useDBlEngine) {
            setEngine(ExpectedResult.ENGINE_SYBASE);
        }
    }

    /**
     * 
     * @return Test
     * @throws Exception exception
     */
    public static Test suite() throws Exception {
        TestSuite suite = new TestSuite("All org.castor.ddl.sybase tests");

         //schema test
        suite.addTest(new SybaseGeneratorTest("testCreateSchema", true));
        // drop test
        suite.addTest(new SybaseGeneratorTest("testDropTable", false));
        
        // table test
        suite.addTest(new SybaseGeneratorTest("testSingleTable", false));
        suite.addTest(new SybaseGeneratorTest("testMultipleTable", false));
        suite.addTest(new SybaseGeneratorTest("testIgnoredTable", false));
        suite.addTest(new SybaseGeneratorTest("testNoTable", false));

        //field test
        suite.addTest(new SybaseGeneratorTest("testSingleField", false));
        suite.addTest(new SybaseGeneratorTest("testSingleFieldForAll", true));
        suite.addTest(new SybaseGeneratorTest("testIgnoredField", false));
        suite.addTest(new SybaseGeneratorTest("testNoField", false));
        suite.addTest(new SybaseGeneratorTest("testManyKeysReference", false));
        suite.addTest(new SybaseGeneratorTest("testManyClassKeysReference", false));
        suite.addTest(new SybaseGeneratorTest("test2LevelsReference", false));
        
        // primary key test
        suite.addTest(new SybaseGeneratorTest("testClassId", false));
        suite.addTest(new SybaseGeneratorTest("testClassMultipleId", false));
        suite.addTest(new SybaseGeneratorTest("testFieldId", false));
        suite.addTest(new SybaseGeneratorTest("testFieldMultipleId", false));
        suite.addTest(new SybaseGeneratorTest("testOverwriteFieldId", false));
        suite.addTest(new SybaseGeneratorTest("testNoId", false));

        // foreign key test
        suite.addTest(new SybaseGeneratorTest("testOneOneRelationship", false));
        suite.addTest(new SybaseGeneratorTest("testOneManyRelationship", false));
        suite.addTest(new SybaseGeneratorTest("testManyManyRelationship", false));

        // index test - 
        suite.addTest(new SybaseGeneratorTest("testCreateIndex", false));        
        
        // key generator test
        suite.addTest(new SybaseGeneratorTest("testKeyGenIdentity", true));
        suite.addTest(new SybaseGeneratorTest("testKeyGenHighLow", false));
        suite.addTest(new SybaseGeneratorTest("testKeyGenMax", false));
        suite.addTest(new SybaseGeneratorTest("testKeyGenUUID", false));
        
        // trigger test - not yet

        return suite;
    }

    /** 
     * {@inheritDoc}
     */
    protected void setUp() throws Exception {
        super.setUp();
        setDbConf(SybaseGeneratorTest.class.getResource("sybase.properties").getFile());

        Configuration conf = new Configuration();
        conf.addProperties(getGlobalConf());
        conf.addProperties(getDbConf());
        setGenerator(new SybaseGenerator(conf));

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

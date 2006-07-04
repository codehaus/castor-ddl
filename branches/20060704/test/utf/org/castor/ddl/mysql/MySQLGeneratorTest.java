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

import org.castor.ddl.mysql.MySQLGenerator;

import utf.org.castor.ddl.BaseGeneratorTest;
import utf.org.castor.ddl.ExpectedResult;

/**
 * 
 * Created on Jun 13, 2006 - 6:56:15 PM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class MySQLGeneratorTest extends BaseGeneratorTest {
    /**
     * Constructor for MySQLGeneratorTest
     * 
     * @param testcase
     */
    public MySQLGeneratorTest(String testcase) {
        super(testcase);
    }

    /**
     * this constructor aims to reuse all test scenerios, except _engine
     * Constructor for MySQLGeneratorTest
     * 
     * @param testcase
     * @param mySqlEngine
     */
    public MySQLGeneratorTest(String testcase, boolean useMySqlEngine) {
        super(testcase);
        if (useMySqlEngine) {
            _engine = ExpectedResult.ENGINE_MYSQL;
        }
    }

    public static Test suite() throws Exception {
        TestSuite suite = new TestSuite("All org.castor.ddl.mysql tests");

        // schema test
        suite.addTest(new MySQLGeneratorTest("testCreateSchema", true));
        // drop test
        suite.addTest(new MySQLGeneratorTest("testDropTable", true));
        
        // table test
        suite.addTest(new MySQLGeneratorTest("testSingleTable", true));
        suite.addTest(new MySQLGeneratorTest("testMultipleTable", true));
        suite.addTest(new MySQLGeneratorTest("testIgnoredTable", true));
        suite.addTest(new MySQLGeneratorTest("testNoTable", false));

        //field test
        suite.addTest(new MySQLGeneratorTest("testSingleField", false));
        suite.addTest(new MySQLGeneratorTest("testSingleFieldForAll", true));
        suite.addTest(new MySQLGeneratorTest("testIgnoredField", true));
        suite.addTest(new MySQLGeneratorTest("testNoField", true));
        suite.addTest(new MySQLGeneratorTest("testManyKeysReference", true));
        suite.addTest(new MySQLGeneratorTest("testManyClassKeysReference", true));
        suite.addTest(new MySQLGeneratorTest("test2LevelsReference", true));
        
        // primary key test
        suite.addTest(new MySQLGeneratorTest("testClassId", true));
        suite.addTest(new MySQLGeneratorTest("testClassMultipleId", true));
        suite.addTest(new MySQLGeneratorTest("testFieldId", true));
        suite.addTest(new MySQLGeneratorTest("testFieldMultipleId", true));
        suite.addTest(new MySQLGeneratorTest("testOverwriteFieldId", true));
        suite.addTest(new MySQLGeneratorTest("testNoId", true));

        // foreign key test
        suite.addTest(new MySQLGeneratorTest("testOneOneRelationship", true));
        suite.addTest(new MySQLGeneratorTest("testOneManyRelationship", true));
        suite.addTest(new MySQLGeneratorTest("testManyManyRelationship", true));

        // index test - 
        suite.addTest(new MySQLGeneratorTest("testCreateIndex", true));        
        
        // key generator test
        suite.addTest(new MySQLGeneratorTest("testKeyGenIdentity", true));
        suite.addTest(new MySQLGeneratorTest("testKeyGenHighLow", true));
        suite.addTest(new MySQLGeneratorTest("testKeyGenMax", true));
        suite.addTest(new MySQLGeneratorTest("testKeyGenSequence", true));
        suite.addTest(new MySQLGeneratorTest("testKeyGenUUID", true));
        
        // trigger test - not yet

        return suite;
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        _globalConf = "conf/ddl.properties";
        _dbConf = "conf/mysql.properties";
        _generator = new MySQLGenerator(_globalConf, _dbConf);
        _generator.setMapping(_mapping);
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        // TODO Auto-generated method stub
        super.tearDown();
        _generator = null;
    }

}

/*
 * Copyright 2006 Le Duc Bao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package utf.org.castor.ddl;

import org.castor.ddl.GeneratorException;
import org.castor.ddl.GeneratorFactory;
import org.castor.ddl.mysql.MySQLGenerator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 * Created on Jun 20, 2006 - 11:56:16 AM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public final class GeneratorFactoryTest extends TestCase {

    /**
     * Constructor for GeneratorFactoryTest
     * @param testcase  test case
     */
    public GeneratorFactoryTest(final String testcase) {
        super(testcase);
    }

    /**
     * @see TestCase#setUp()
     * {@inheritDoc}
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * @see TestCase#tearDown()
     * {@inheritDoc}
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test method for 'org.castor.ddl.GeneratorFactory.getMySQLDDLGenerator()'
     * {@inheritDoc}
     */
    public void testGetMySQLDDLGenerator() {
        
        try {
            MySQLGenerator generator = (MySQLGenerator) GeneratorFactory
                .createDDLGenerator("mysql", null, null);
            assertEquals(generator.getClass(), MySQLGenerator.class);
        } catch (GeneratorException e) {
            assertTrue(e.getMessage(), false);
        } 
    }
    
    /**
     * 
     * @return  Test
     * @throws Exception exception
     */
    public static Test suite() throws Exception {
        TestSuite suite = new TestSuite("DDL generator factory tests");
        suite.addTest(new GeneratorFactoryTest("testGetMySQLDDLGenerator"));
        
        return suite;
    }

}

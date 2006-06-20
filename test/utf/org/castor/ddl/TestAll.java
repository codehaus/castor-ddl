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

import utf.org.castor.ddl.mysql.MySQLGeneratorTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 * Created on Jun 13, 2006 - 6:50:48 PM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class TestAll extends TestCase {

    /**
     * Constructor for TestAll
     * @param arg0
     */
    public TestAll(final String name) {super(name); }

    public static Test suite() throws Exception {
        TestSuite suite = new TestSuite("All org.castor.ddl tests");
        
        //GeneratorFactory test
        suite.addTest(GeneratorFactoryTest.suite());
        
        //MySQL generator
        suite.addTest(MySQLGeneratorTest.suite());

        return suite;
    }
    
}

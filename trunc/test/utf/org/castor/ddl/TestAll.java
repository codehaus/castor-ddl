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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.log4j.xml.DOMConfigurator;

import utf.org.castor.ddl.db2.Db2GeneratorTest;
import utf.org.castor.ddl.derby.DerbyGeneratorTest;
import utf.org.castor.ddl.hsql.HsqlGeneratorTest;
import utf.org.castor.ddl.mssql.MssqlGeneratorTest;
import utf.org.castor.ddl.mysql.MysqlGeneratorTest;
import utf.org.castor.ddl.oracle.OracleGeneratorTest;
import utf.org.castor.ddl.pointbase.PointBaseGeneratorTest;
import utf.org.castor.ddl.postgresql.PostgresqlGeneratorTest;
import utf.org.castor.ddl.sapdb.SapdbGeneratorTest;
import utf.org.castor.ddl.sybase.SybaseGeneratorTest;

/**
 * 
 * Created on Jun 13, 2006 - 6:50:48 PM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class TestAll extends TestCase {

    /**log file*/
    private static final String LOG_FILE = "log4j-test.xml";

    /**
     * Constructor for TestAll
     * @param name name
     */
    public TestAll(final String name) { super(name); }

    /**
     * 
     * @param args params
     */
    public static void main(final String[] args) {
        try {
            DOMConfigurator.configure(LOG_FILE);
            
//            TestResult result =  
            junit.textui.TestRunner.run(TestAll.suite());
//            junit.swingui.TestRunner.run(TestAll.class);
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }


    /**
     * 
     * @return Test
     * @throws Exception exception
     */
    public static Test suite() throws Exception {
        TestSuite suite = new TestSuite("All org.castor.ddl tests");
        
        //GeneratorFactory test
        suite.addTest(GeneratorFactoryTest.suite());
        
        //MySQL generator test
        suite.addTest(MysqlGeneratorTest.suite());

        //Oracle generator test
        suite.addTest(OracleGeneratorTest.suite());

        //PostgreSQL generator test
        suite.addTest(PostgresqlGeneratorTest.suite());

        //Derby generator test
        suite.addTest(DerbyGeneratorTest.suite());

        //Mssql generator test
        suite.addTest(MssqlGeneratorTest.suite());

        //Sapdb generator test
        suite.addTest(SapdbGeneratorTest.suite());

        //db2 generator test
        suite.addTest(Db2GeneratorTest.suite());

        //db2 generator test
        suite.addTest(SybaseGeneratorTest.suite());

        //hsql generator test
        suite.addTest(HsqlGeneratorTest.suite());

        //pointbase generator test
        suite.addTest(PointBaseGeneratorTest.suite());

        return suite;
    }
    
}

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

package utf.org.castor.ddl;

import java.io.File;
import java.net.URL;

import junit.framework.TestCase;

import org.castor.ddl.AbstractGenerator;
import org.castor.ddl.Configuration;
import org.castor.ddl.TypeMapper;
import org.castor.ddl.mysql.MySQLTypeMapper;
import org.exolab.castor.mapping.Mapping;

/**
 * This class handles all testcase for all database. The specific database will inherite 
 * this class. Expecting that all testcase use the same scenarios for all database. The 
 * expected result maybe differ within each other. The _engine defines which expected result
 * will be loaded to the testcase. The inherited class may redefine this variable to 
 * reuse the test scenarios, but _engine. 
 *  
 * Created on Jun 13, 2006 - 6:15:36 PM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public abstract class BaseGeneratorTest extends TestCase {
    /** expected result for specific database engine*/
    protected String _engine = ExpectedResult.ENGINE_GENERIC;
    
    /** handle the mapping for test*/
    protected Mapping _mapping = null;

    /** handle expected ddl*/
    protected ExpectedResult _expectedDDL = null;

    /** handle the generator*/
    protected AbstractGenerator _generator = null;

    /** handle the global configuration     */
    protected String _globalConf = null;

    /** handle the database configuration*/
    protected String _dbConf = null;

    /**
     * Constructor for BaseGeneratorTest
     * @param testcase
     */
    public BaseGeneratorTest(final String testcase) {
        super(testcase);
    }

    /**
     * load data for test including mapping file and expected result
     * @param mappingFile
     * @param expectedFile
     * @throws Exception
     */
    protected void loadData(String mappingFile, String expectedFile)
            throws Exception {
        String dataDir = "data" + File.separator;

        try {
            URL url = BaseGeneratorTest.class.getResource(dataDir
                    + expectedFile);
            _expectedDDL = ExpectedResult.getExpectedResult(url);
            _mapping = new Mapping();
            _mapping.loadMapping(BaseGeneratorTest.class.getResource(dataDir
                    + mappingFile));
            _generator.setMapping(_mapping);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    /**
     * load mapping file 
     * @param mappingFile
     * @throws Exception
     */
    protected void loadData(String mappingFile)
            throws Exception {
        String dataDir = "data" + File.separator;

        try {
            _mapping = new Mapping();
            _mapping.loadMapping(BaseGeneratorTest.class.getResource(dataDir
                    + mappingFile));
            _generator.setMapping(_mapping);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * test single table
     * 
     */
    public void testSingleTable() {
        try {
            // load test data
            loadData("single_table.xml", "single_table.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger("default_integer_precision"),
                    conf.getInteger("default_char_length")};
            String ddl = _generator.generateTableDDL();

            boolean b = _expectedDDL.match(_engine, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            assertTrue("test exception: " + e.getMessage(), false);
        }
    }

    public void testFieldBit() {
        try {
            // load test data
            loadData("single_field_bit.xml", "single_field_bit.exp.xml");

            String ddl = _generator.generateTableDDL();
            boolean b = _expectedDDL.match(_engine, ddl);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            assertTrue("test exception: " + e.getMessage(), false);
        }
    }


    /**
     * test the identity is defined in the class tag
     * 
     */
    public void testClassId() {
        try {
            // load test data
            loadData("class_id.xml", "class_id.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger("default_integer_precision"),
                    conf.getInteger("default_char_length")};
            String ddl = _generator.generateTableDDL();

            boolean b = _expectedDDL.match(_engine, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            assertTrue("test exception: " + e.getMessage(), false);
        }
    }

    /**
     * test the identity is defined in the class tag and there are multiple ids
     * 
     */
    public void testClassMultipleId() {
        try {
            // load test data
            loadData("class_multiple_id.xml", "class_multiple_id.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger("default_integer_precision"),
                    conf.getInteger("default_char_length")};
            String ddl = _generator.generateTableDDL();

            boolean b = _expectedDDL.match(_engine, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            assertTrue("test exception: " + e.getMessage(), false);
        }
    }

    /**
     * test the identity is defined in the field tag 
     * 
     */
    public void testFieldId() {
        try {
            // load test data
            loadData("field_id.xml", "field_id.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger("default_integer_precision"),
                    conf.getInteger("default_char_length")};
            String ddl = _generator.generateTableDDL();

            boolean b = _expectedDDL.match(_engine, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            assertTrue("test exception: " + e.getMessage(), false);
        }
    }
    
    /**
     * test the identity is defined in the field tag and there are multiple ids
     * 
     */
    public void testFieldMultipleId() {
        try {
            // load test data
            loadData("field_multiple_id.xml", "field_multiple_id.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger("default_integer_precision"),
                    conf.getInteger("default_char_length")};
            String ddl = _generator.generateTableDDL();

            boolean b = _expectedDDL.match(_engine, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            assertTrue("test exception: " + e.getMessage(), false);
        }
    }

    /**
     * test to create many table in a mapping
     * 
     */
    public void testMultipleTable() {
        try {
            // load test data
            loadData("multiple_table.xml", "multiple_table.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger("default_integer_precision"),
                    conf.getInteger("default_char_length")};
            String ddl = _generator.generateTableDDL();

            boolean b = _expectedDDL.match(_engine, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            assertTrue("test exception: " + e.getMessage(), false);
        }
    }

    /**
     * Create a table with 23 fields represented to each data type
     * 
     */
    public void testSingleFieldForAll() {
        try {
            // load test data
            loadData("single_field_for_all.xml", "single_field_for_all.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger("default_tinyint_precision"),
                    conf.getInteger("default_smallint_precision"),
                    conf.getInteger("default_integer_precision"),
                    conf.getInteger("default_bigint_precision"),
                    
                    conf.getInteger("default_float_precision"),
                    conf.getInteger("default_float_decimals"),
                    conf.getInteger("default_double_precision"),
                    conf.getInteger("default_double_decimals"),
                    conf.getInteger("default_real_precision"),
                    conf.getInteger("default_real_decimals"),
                    conf.getInteger("default_numeric_precision"),
                    conf.getInteger("default_numeric_decimals"),
                    conf.getInteger("default_decimal_precision"),
                    conf.getInteger("default_decimal_decimals"),
                    
                    conf.getInteger("default_char_length"),
                    conf.getInteger("default_varchar_length"),
                    conf.getInteger("default_longvarchar_length"),
                    
                    conf.getInteger("default_timestamp_precision"),
                    
                    conf.getInteger("default_binary_length"),
                    conf.getInteger("default_varbinary_length"),
                    conf.getInteger("default_longvarbinary_length")};
            
            String ddl = _generator.generateTableDDL();

            boolean b = _expectedDDL.match(_engine, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            assertTrue("test exception: " + e.getMessage(), false);
        }
    }

    /**
     * test to create identity for key generator
     * 
     */
    public void testKeyGenIdentity() {
        try {
            // load test data
            loadData("key_gen_identity.xml", "key_gen_identity.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger("default_integer_precision")};
            String ddl = _generator.generateTableDDL();

            boolean b = _expectedDDL.match(_engine, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            assertTrue("test exception: " + e.getMessage(), false);
        }
    }

    /**
     * test to create high-low key generator
     * 
     */
    public void testKeyGenHighLow() {
        try {
            // load test data
            loadData("key_gen_high-low.xml", "key_gen_high-low.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger("default_integer_precision")};
            String ddl = _generator.generateTableDDL();

            boolean b = _expectedDDL.match(_engine, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            assertTrue("test exception: " + e.getMessage(), false);
        }
    }

    /**
     * test to create MAX key generator
     * 
     */
    public void testKeyGenMax() {
        try {
            // load test data
            loadData("key_gen_max.xml", "key_gen_max.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            Object[] params = new Object[] {
                    conf.getInteger("default_integer_precision")};
            String ddl = _generator.generateTableDDL();

            boolean b = _expectedDDL.match(_engine, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            assertTrue("test exception: " + e.getMessage(), false);
        }
    }
    
    /**
     * test to create Sequence key generator
     * 
     */
    public void testKeyGenSequence() {
        try {
            // load test data
            loadData("key_gen_sequence.xml", "key_gen_sequence.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            //test
            Object[] params = new Object[] {
                    conf.getInteger("default_integer_precision")};
                     
            String ddl = _generator.generateTableDDL();
            
            boolean b = _expectedDDL.match(_engine, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            assertTrue("test exception: " + e.getMessage(), false);
        }
    }

     /**
     * test to create Sequence key generator
     * 
     */
    public void testKeyGenUUID() {
        try {
            // load test data
            loadData("key_gen_uuid.xml", "key_gen_uuid.exp.xml");

            // setup
            Configuration conf = _generator.getConf();
            TypeMapper typeMapper = new MySQLTypeMapper(conf);
            _generator.setTypeMapper(typeMapper);

            //test
            Object[] params = new Object[] {
                    conf.getInteger("default_integer_precision")};
                     
            String ddl = _generator.generateTableDDL();
            
            boolean b = _expectedDDL.match(_engine, ddl, params);
            assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                    + _expectedDDL.getMessage(), b);
        } catch (Exception e) {
            assertTrue("test exception: " + e.getMessage(), false);
        }
    }

    /**
    * test to create one-one relationship
    * 
    */
   public void testOneOneRelationship() {
       try {
           // load test data
           loadData("relationship_1_1.xml", "relationship_1_1.exp.xml");

           // setup
           Configuration conf = _generator.getConf();
           TypeMapper typeMapper = new MySQLTypeMapper(conf);
           _generator.setTypeMapper(typeMapper);

           //test
           Object[] params = new Object[] {
                   conf.getInteger("default_integer_precision"),
                   conf.getInteger("default_char_length")};
                    
           String ddl = _generator.generateTableDDL();           
           boolean b = _expectedDDL.match(_engine, 0, ddl, params);
           assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                   + _expectedDDL.getMessage(), b);
           
           ddl = _generator.generateForeignKey();           
           b = _expectedDDL.match(_engine, 1, ddl, params);
           assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                   + _expectedDDL.getMessage(), b);
           
       } catch (Exception e) {
           assertTrue("test exception: " + e.getMessage(), false);
       }
   }

   /**
   * test to create one-many relationship
   * 
   */
  public void testOneManyRelationship() {
      try {
          // load test data
          loadData("relationship_1_n.xml", "relationship_1_n.exp.xml");

          // setup
          Configuration conf = _generator.getConf();
          TypeMapper typeMapper = new MySQLTypeMapper(conf);
          _generator.setTypeMapper(typeMapper);

          //test
          Object[] params = new Object[] {
                  conf.getInteger("default_integer_precision"),
                  conf.getInteger("default_char_length")};
                   
          String ddl = _generator.generateTableDDL();
          
          boolean b = _expectedDDL.match(_engine, 0, ddl, params);
          assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                  + _expectedDDL.getMessage(), b);
          
          ddl = _generator.generateForeignKey();           
          b = _expectedDDL.match(_engine, 1, ddl, params);
          assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                  + _expectedDDL.getMessage(), b);
      } catch (Exception e) {
          assertTrue("test exception: " + e.getMessage(), false);
      }
  }
  
  /**
   * test to create many-many relationship
   * 
   */
  public void testManyManyRelationship() {
      try {
          // load test data
          loadData("relationship_m_n.xml", "relationship_m_n.exp.xml");

          // setup
          Configuration conf = _generator.getConf();
          TypeMapper typeMapper = new MySQLTypeMapper(conf);
          _generator.setTypeMapper(typeMapper);

          //test
          Object[] params = new Object[] {
                  conf.getInteger("default_integer_precision"),
                  conf.getInteger("default_char_length")};
                   
          String ddl = _generator.generateTableDDL();          
          boolean b = _expectedDDL.match(_engine, 0, ddl, params);
          assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                  + _expectedDDL.getMessage(), b);

          ddl = _generator.generateResolvingTable();          
          b = _expectedDDL.match(_engine, 1, ddl, params);
          assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                  + _expectedDDL.getMessage(), b);
          
          
          ddl = _generator.generateForeignKey();           
          b = _expectedDDL.match(_engine, 2, ddl, params);
          assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                  + _expectedDDL.getMessage(), b);
      } catch (Exception e) {
          assertTrue("test exception: " + e.getMessage(), false);
      }
  }
  
  /**
   * test to create one-one relationship
   * 
   */
  public void test2LevelsReference() {
      try {
          // load test data
          loadData("2levels_reference.xml", "2levels_reference.exp.xml");

          // setup
          Configuration conf = _generator.getConf();
          TypeMapper typeMapper = new MySQLTypeMapper(conf);
          _generator.setTypeMapper(typeMapper);

          //test
          Object[] params = new Object[] {
                  conf.getInteger("default_integer_precision"),
                  conf.getInteger("default_char_length")};
                   
          String ddl = _generator.generateTableDDL();
          
          boolean b = _expectedDDL.match(_engine, ddl, params);
          assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                  + _expectedDDL.getMessage(), b);
      } catch (Exception e) {
          assertTrue("test exception: " + e.getMessage(), false);
      }
  }    

  /**
   * test single table
   * 
   */
  public void testIgnoreField() {
      try {
          // load test data
          loadData("ignore_field.xml", "ignore_field.exp.xml");

          // setup
          Configuration conf = _generator.getConf();
          TypeMapper typeMapper = new MySQLTypeMapper(conf);
          _generator.setTypeMapper(typeMapper);

          Object[] params = new Object[] {
                  conf.getInteger("default_integer_precision")};
          String ddl = _generator.generateTableDDL();

          boolean b = _expectedDDL.match(_engine, ddl, params);
          assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                  + _expectedDDL.getMessage(), b);
      } catch (Exception e) {
          assertTrue("test exception: " + e.getMessage(), false);
      }
  }
  
  /**
   * test overwrite identity by field'id
   * 
   */
  public void testOverwriteFieldId() {
      try {
          // load test data
          loadData("overwrite_field_id.xml", "overwrite_field_id.exp.xml");

          // setup
          Configuration conf = _generator.getConf();
          TypeMapper typeMapper = new MySQLTypeMapper(conf);
          _generator.setTypeMapper(typeMapper);

          Object[] params = new Object[] {
                  conf.getInteger("default_integer_precision"),
                  conf.getInteger("default_char_length")};
          String ddl = _generator.generateTableDDL();

          boolean b = _expectedDDL.match(_engine, ddl, params);
          assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                  + _expectedDDL.getMessage(), b);
      } catch (Exception e) {
          assertTrue("test exception: " + e.getMessage(), false);
      }
  }
  /**
   * test to many keys reference
   * 
   */
  public void testManyKeysReference() {
      try {
          // load test data
          loadData("many_keys_reference.xml", "many_keys_reference.exp.xml");

          // setup
          Configuration conf = _generator.getConf();
          TypeMapper typeMapper = new MySQLTypeMapper(conf);
          _generator.setTypeMapper(typeMapper);

          //test
          Object[] params = new Object[] {
                  conf.getInteger("default_integer_precision"),
                  conf.getInteger("default_char_length")};
                   
          String ddl = _generator.generateTableDDL();
          
          boolean b = _expectedDDL.match(_engine, ddl, params);
          assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                  + _expectedDDL.getMessage(), b);
      } catch (Exception e) {
          assertTrue("test exception: " + e.getMessage(), false);
      }
  }

  /**
   * test to many class's keys reference
   * 
   */
  public void testManyClassKeysReference() {
      try {
          // load test data
          loadData("many_class_keys_reference.xml", "many_class_keys_reference.exp.xml");

          // setup
          Configuration conf = _generator.getConf();
          TypeMapper typeMapper = new MySQLTypeMapper(conf);
          _generator.setTypeMapper(typeMapper);

          //test
          Object[] params = new Object[] {
                  conf.getInteger("default_integer_precision"),
                  conf.getInteger("default_char_length")};
                   
          String ddl = _generator.generateTableDDL();
          
          boolean b = _expectedDDL.match(_engine, ddl, params);
          assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                  + _expectedDDL.getMessage(), b);
      } catch (Exception e) {
          assertTrue("test exception: " + e.getMessage(), false);
      }
  }
  

  /**
   * test to many class's keys reference
   * 
   */
  public void testCreateSchema() {
      try {
          loadData("empty.xml");
          // setup
          Configuration conf = _generator.getConf();
          TypeMapper typeMapper = new MySQLTypeMapper(conf);
          _generator.setTypeMapper(typeMapper);
          
          String schema = "test";
          Ddl expectedDDL = new Ddl("use " + schema, Ddl.MATCHTYPE_REGEXP, false);
          conf.setProperty(Configuration.SCHEMA_NAME_KEY, schema);
          String ddl = _generator.generateSchemaDDL();
          
          assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                  + expectedDDL.toString(), expectedDDL.match(ddl));

          schema = "";
          expectedDDL = new Ddl("", Ddl.MATCHTYPE_EXACT, false);
          conf.setProperty(Configuration.SCHEMA_NAME_KEY, schema);
          ddl = _generator.generateSchemaDDL();
          
          assertTrue("Generated DDL:\n" + ddl + "\nExpected DDL:\n"
                  + expectedDDL.toString(), expectedDDL.match(ddl));

      } catch (Exception e) {
          assertTrue("test exception: " + e.getMessage(), false);
      }
  }  
}

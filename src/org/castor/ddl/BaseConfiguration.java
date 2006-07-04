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

package org.castor.ddl;


/**
 * this class handles some basic configuration information for DDL generator
 * Created on Jun 12, 2006 - 12:11:52 AM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class BaseConfiguration {

    /**
     * Constructor for BaseConfiguration
     */
    public BaseConfiguration() {
        super();
        // TODO Auto-generated constructor stub
    }

    /** option for generate DDL for CREATE statement*/
    public static final String GENERATE_DDL_FOR_SCHEMA_KEY = "generate_ddl_for_schema";

    /** option for generate DDL for CREATE statement*/
    public static final String GENERATE_DDL_FOR_CREATE_KEY = "generate_ddl_for_create";

    /** option for generate DDL for DROP statement*/
    public static final String GENERATE_DDL_FOR_DROP_KEY = "generate_ddl_for_drop";

    /** option for generate DDL for PRIMARY KEY statement*/
    public static final String GENERATE_DDL_FOR_PRIMARYKEY_KEY = "generate_ddl_for_primarykey";

    /** option for generate DDL for FOREIGN KEY statement*/
    public static final String GENERATE_DDL_FOR_FOREIRNKEY_KEY = "generate_ddl_for_foreignkey";

    /** option for generate DDL for INDEX statement*/
    public static final String GENERATE_DDL_FOR_INDEX_KEY = "generate_ddl_for_index";

    /** option for generate DDL for KEY GENERATOR statement*/
    public static final String GENERATE_DDL_FOR_KEYGENERATOR_KEY = "generate_ddl_for_keygenerator";

    /** option for generate DDL for TRIGGER statement*/
    public static final String GENERATE_DDL_FOR_TRIGGER_KEY = "generate_ddl_for_trigger";
    
    /** define database engine*/
    public static final String DATABASE_ENGINE_KEY = "database_engine";

    /** define ddl generator configuration file*/
    public static final String DDLGEN_CONFIGURATION_KEY = "ddlgen_config_file";
    /**true string*/
    public final static String TRUE = "true";

    /**false string*/
    public final static String FALSE = "false";

    /**schema name*/
    public final static String SCHEMA_NAME_KEY = "schema_name";

    /** sql field delimeter key*/
    public static final String SQL_FIELD_DELIMETER_KEY = "sql_field_delimeter";
    
    /**field delimeter*/
    public static String SQL_FIELD_DELIMETER = ",";
    

    /** sql field delimeter key*/
    public static final String SQL_STAT_DELIMETER_KEY = "sql_stat_delimeter";

    /**statement delimeter*/
    public static String SQL_STAT_DELIMETER = ";";
    
    /**line separator key*/
    public static String LINE_SEPARATOR_KEY = "line.separator";

    /**line separator*/
    public static String LINE_SEPARATOR = System.getProperty("line.separator");
    
    /**line separator key*/
    public static String LINE_INDENT_KEY = "line_indent";

    /**Line indent*/
    public static String LINE_INDENT = "    ";
    
    
    /**ddl format case key*/
    public static String DDL_FORMAT_CASE_KEY = "ddl_format_case";

    /**ddl format case sensitive option*/
    public static int DDL_FORMAT_CASESENSITIVE = 1;

    /**ddl format case sensitive option*/
    public static String DDL_FORMAT_CASESENSITIVE_STRING = "casesensitive";

    /**ddl format case sensitive option*/
    public static int DDL_FORMAT_UPPERCASE = 2;

    /**ddl format case sensitive option*/
    public static String DDL_FORMAT_UPPERCASE_STRING = "upper";

    /**ddl format case sensitive option*/
    public static int DDL_FORMAT_LOWERCASE = 3;

    /**ddl format case sensitive option*/
    public static String DDL_FORMAT_LOWERCASE_STRING = "lower";

    /**Line indent*/
    public static int DDL_FORMAT_CASE = DDL_FORMAT_CASESENSITIVE;
    
    /**header comment text key*/
    public static String HEADER_COMMENT_TEXT_KEY = "header_comment_text";
    
    /**group ddl by key*/
    public static String GROUP_DDL_BY_KEY = "group_ddl_by";
            
    /**group ddl by table*/
    public static String GROUP_DDL_BY_TABLE = "table";

    /**group ddl by ddltype*/
    public static String GROUP_DDL_BY_DDLTYPE = "ddltype";
}

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
package org.castor.ddl;

/**
 * BaseConfiguration handles configuration's key definition.
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 * @version $Revision: 5951 $ $Date: 2006-04-25 16:09:10 -0600 (Tue, 25 Apr 2006) $
 */

public class BaseConfiguration {
    //--------------------------------------------------------------------------
    
    /** Generator classes of supported database engines. */
    public static final String GENERATORS_KEY = "org.castor.ddl.Generators";

    /** Default database engine. */
    public static final String DEFAULT_ENGINE_KEY = "org.castor.ddl.DefaultEngine";

    /** Key generator factory classes of supported database engines. */
    public static final String KEYGEN_FACTORIES_KEY =
        "org.castor.ddl.KeyGeneratorFactories";

    //--------------------------------------------------------------------------
    
    /** option for generate DDL for CREATE statement */
    public static final String GENERATE_DDL_FOR_SCHEMA_KEY = "generate_ddl_for_schema";

    /** option for generate DDL for CREATE statement */
    public static final String GENERATE_DDL_FOR_CREATE_KEY = "generate_ddl_for_create";

    /** option for generate DDL for DROP statement */
    public static final String GENERATE_DDL_FOR_DROP_KEY = "generate_ddl_for_drop";

    /** option for generate DDL for PRIMARY KEY statement */
    public static final String GENERATE_DDL_FOR_PRIMARYKEY_KEY = 
        "generate_ddl_for_primarykey";

    /** option for generate DDL for FOREIGN KEY statement */
    public static final String GENERATE_DDL_FOR_FOREIRNKEY_KEY = 
        "generate_ddl_for_foreignkey";

    /** option for generate DDL for INDEX statement */
    public static final String GENERATE_DDL_FOR_INDEX_KEY = "generate_ddl_for_index";

    /** option for generate DDL for KEY GENERATOR statement */
    public static final String GENERATE_DDL_FOR_KEYGENERATOR_KEY = 
        "generate_ddl_for_keygenerator";

    /** option for generate DDL for TRIGGER statement */
    public static final String GENERATE_DDL_FOR_TRIGGER_KEY = "generate_ddl_for_trigger";

    /** define ddl generator configuration file */
    public static final String DDLGEN_CONFIGURATION_KEY = "ddlgen_config_file";

    //--------------------------------------------------------------------------
    
    /** true string */
    public static final String TRUE = "true";

    /** false string */
    public static final String FALSE = "false";

    /** schema name */
    public static final String SCHEMA_NAME_KEY = "schema_name";

    /** sql field delimeter key */
    public static final String SQL_FIELD_DELIMETER_KEY = "sql_field_delimeter";

    /** field delimeter */
    private String _sqlFieldDelimeter = ",";

    /** sql field delimeter key */
    public static final String SQL_STAT_DELIMETER_KEY = "sql_stat_delimeter";

    /** statement delimeter */
    private String _sqlStatDelimeter = ";";

    /** line separator key */
    public static final String LINE_SEPARATOR_KEY = "line.separator";

    /** line separator */
    private String _lineSeparator = System.getProperty("line.separator");

    /** line separator key */
    public static final String LINE_INDENT_KEY = "line_indent";

    /** Line indent */
    private String _lineIndent = "    ";
    
    /** sql bloc separator key */
    public static final String SQL_BLOC_SEPARATOR_KEY = "sql_bloc_separator";
    
    /** Line indent */
    private String _sqlBlocSeparator = "\n";

    /** ddl format case key */
    public static final String DDL_FORMAT_CASE_KEY = "ddl_format_case";

    /** ddl format case sensitive option */
    public static final int DDL_FORMAT_CASESENSITIVE = 1;

    /** ddl format case sensitive option */
    public static final String DDL_FORMAT_CASESENSITIVE_STRING = "casesensitive";

    /** ddl format case sensitive option */
    public static final int DDL_FORMAT_UPPERCASE = 2;

    /** ddl format case sensitive option */
    public static final String DDL_FORMAT_UPPERCASE_STRING = "upper";

    /** ddl format case sensitive option */
    public static final int DDL_FORMAT_LOWERCASE = 3;

    /** ddl format case sensitive option */
    public static final String DDL_FORMAT_LOWERCASE_STRING = "lower";

    /** Line indent */
    private int _ddlFormatCase = DDL_FORMAT_CASESENSITIVE;

    /** header comment text key */
    public static final String HEADER_COMMENT_TEXT_KEY = "header_comment_text";

    /** group ddl by key */
    public static final String GROUP_DDL_BY_KEY = "group_ddl_by";

    /** group ddl by table */
    public static final String GROUP_DDL_BY_TABLE = "table";

    /** group ddl by ddltype */
    public static final String GROUP_DDL_BY_DDLTYPE = "ddltype";

    /**trigger template*/
    public static final String TRIGGER_TEMPLATE = "trigger_template";
    
    /**
     * 
     * @return Returns the _sqlFieldDelimeter.
     */
    public final String getSqlFieldDelimeter() {
        return _sqlFieldDelimeter;
    }

    /**
     * Set the _sqlFieldDelimeter by _sqlFieldDelimeter.
     * @param sqlFieldDelimeter sql field delimeter
     */
    public final void setSqlFieldDelimeter(final String sqlFieldDelimeter) {
        this._sqlFieldDelimeter = sqlFieldDelimeter;
    }

    /**
     * 
     * @return Returns the ddlFormatCase.
     */
    public final int getDdlFormatCase() {
        return _ddlFormatCase;
    }

    /**
     * Set the ddlFormatCase by _ddlFormatCase.
     * @param ddlFormatCase  ddl format case
     */
    public final void setDdlFormatCase(final int ddlFormatCase) {
        _ddlFormatCase = ddlFormatCase;
    }

    /**
     * 
     * @return Returns the lineIndent.
     */
    public final String getLineIndent() {
        return _lineIndent;
    }

    /**
     * Set the lineIndent by _lineIndent.
     * @param lineIndent line indent 
     */
    public final void setLineIndent(final String lineIndent) {
        _lineIndent = lineIndent;
    }

    /**
     * 
     * @return Returns the lineSeparator.
     */
    public final String getLineSeparator() {
        return _lineSeparator;
    }

    /**
     * Set the lineSeparator by _lineSeparator.
     * @param lineSeparator line separator
     */
    public final void setLineSeparator(final String lineSeparator) {
        _lineSeparator = lineSeparator;
    }

    /**
     * 
     * @return Returns the sqlStatDelimeter.
     */
    public final String getSqlStatDelimeter() {
        return _sqlStatDelimeter;
    }

    /**
     * Set the sqlStatDelimeter by _sqlStatDelimeter.
     * @param sqlStatDelimeter sql stat delimeter 
     */
    public final void setSqlStatDelimeter(final String sqlStatDelimeter) {
        _sqlStatDelimeter = sqlStatDelimeter;
    }

    /**
     * 
     * @return Returns the sqlBlockSeparator.
     */
    public final String getSqlBlocSeparator() {
        return _sqlBlocSeparator;
    }

    /**
     * Set the sqlBlockSeparator by _sqlBlocSeparator.
     * @param sqlBlocSeparator sql bloc separator 
     */
    public final void setSqlBlocSeparator(final String sqlBlocSeparator) {
        _sqlBlocSeparator = sqlBlocSeparator;
    }

    //--------------------------------------------------------------------------
}

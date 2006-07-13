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

import java.io.IOException;
import java.util.Properties;

import org.castor.ddl.db2.Db2Generator;
import org.castor.ddl.derby.DerbyGenerator;
import org.castor.ddl.hsql.HsqlGenerator;
import org.castor.ddl.mssql.MssqlGenerator;
import org.castor.ddl.mysql.MysqlGenerator;
import org.castor.ddl.oracle.OracleGenerator;
import org.castor.ddl.pointbase.PointBaseGenerator;
import org.castor.ddl.postgresql.PostgresqlGenerator;
import org.castor.ddl.sapdb.SapdbGenerator;
import org.castor.ddl.sybase.SybaseGenerator;

/**
 * This class handles the creation for specific databse generator Created on Jun
 * 7, 2006 - 3:22:36 PM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public final class GeneratorFactory {

    /**
     * Constructor for GeneratorFactory
     */
    private GeneratorFactory() {
        super();
    }

    /**
     * create an DDL generator for MySQL
     * @param globalConf global configuration file
     * @param mysqlConf mysql configuration file
     * @return MySQL generator
     * @throws GeneratorException
     *             generator error
     */
    public static MysqlGenerator createMySQLDDLGenerator(
            final String globalConf, final String mysqlConf)
            throws GeneratorException {
        /** define the configuration file name of DDL Generator */
        MysqlGenerator mySQLDDLGenerator = new MysqlGenerator(globalConf, mysqlConf);
        return mySQLDDLGenerator;
    }

    /**
     * create an DDL generator for Oracle
     * @param globalConf global configuration file
     * @param dbConf db configuration file
     * @return Oracle DDL generator
     * @throws GeneratorException
     *             generator error
     */
    public static OracleGenerator createOracleDDLGenerator(final String globalConf,
            final String dbConf) throws GeneratorException {
        /** define the configuration file name of DDL Generator */
        OracleGenerator oracleDDLGenerator = new OracleGenerator(globalConf,
                dbConf);
        return oracleDDLGenerator;
    }

    /**
     * create an DDL generator for PostgreSQL
     * @param globalConf global configuration file
     * @param dbConf db configuration file
     * @return PostgreSQL DDL generator
     * @throws GeneratorException
     *             generator error
     */
    public static PostgresqlGenerator createPostgreSQLDDLGenerator(final String 
            globalConf, final String dbConf) throws GeneratorException {
        /** define the configuration file name of DDL Generator */
        PostgresqlGenerator ddlGenerator = new PostgresqlGenerator(globalConf,
                dbConf);
        return ddlGenerator;
    }

    /**
     * create an DDL generator for Derby
     * @param globalConf global configuration file
     * @param dbConf db configuration file
     * @return PostgreSQL DDL generator
     * @throws GeneratorException
     *             generator error
     */
    public static DerbyGenerator createDerbyDDLGenerator(final String 
            globalConf, final String dbConf) throws GeneratorException {
        /** define the configuration file name of DDL Generator */
        DerbyGenerator ddlGenerator = new DerbyGenerator(globalConf,
                dbConf);
        return ddlGenerator;
    }

    /**
     * create an DDL generator for Microsoft SQL Server
     * @param globalConf global configuration file
     * @param dbConf db configuration file
     * @return MSSQL DDL generator
     * @throws GeneratorException
     *             generator error
     */
    public static MssqlGenerator createMssqlDDLGenerator(final String 
            globalConf, final String dbConf) throws GeneratorException {
        /** define the configuration file name of DDL Generator */
        MssqlGenerator ddlGenerator = new MssqlGenerator(globalConf,
                dbConf);
        return ddlGenerator;
    }


    /**
     * create an DDL generator for SapDB
     * @param globalConf global configuration file
     * @param dbConf db configuration file
     * @return SapDB DDL generator
     * @throws GeneratorException
     *             generator error
     */
    public static SapdbGenerator createSapdbDDLGenerator(final String 
            globalConf, final String dbConf) throws GeneratorException {
        /** define the configuration file name of DDL Generator */
        SapdbGenerator ddlGenerator = new SapdbGenerator(globalConf,
                dbConf);
        return ddlGenerator;
    }

    /**
     * create an DDL generator for Db2
     * @param globalConf global configuration file
     * @param dbConf db configuration file
     * @return Db2 DDL generator
     * @throws GeneratorException
     *             generator error
     */
    public static Db2Generator createDb2DDLGenerator(final String 
            globalConf, final String dbConf) throws GeneratorException {
        /** define the configuration file name of DDL Generator */
        Db2Generator ddlGenerator = new Db2Generator(globalConf,
                dbConf);
        return ddlGenerator;
    }

    /**
     * create an DDL generator for Sybase
     * @param globalConf global configuration file
     * @param dbConf db configuration file
     * @return Sybase DDL generator
     * @throws GeneratorException
     *             generator error
     */
    public static SybaseGenerator createSybaseDDLGenerator(final String 
            globalConf, final String dbConf) throws GeneratorException {
        /** define the configuration file name of DDL Generator */
        SybaseGenerator ddlGenerator = new SybaseGenerator(globalConf,
                dbConf);
        return ddlGenerator;
    }

    /**
     * create an DDL generator for Hsql
     * @param globalConf global configuration file
     * @param dbConf db configuration file
     * @return Hsql DDL generator
     * @throws GeneratorException
     *             generator error
     */
    public static HsqlGenerator createHsqlDDLGenerator(final String 
            globalConf, final String dbConf) throws GeneratorException {
        /** define the configuration file name of DDL Generator */
        HsqlGenerator ddlGenerator = new HsqlGenerator(globalConf,
                dbConf);
        return ddlGenerator;
    }

    /**
     * create an DDL generator for PointBase
     * @param globalConf global configuration file
     * @param dbConf db configuration file
     * @return PointBase DDL generator
     * @throws GeneratorException
     *             generator error
     */
    public static PointBaseGenerator createPointBaseDDLGenerator(final String 
            globalConf, final String dbConf) throws GeneratorException {
        /** define the configuration file name of DDL Generator */
        PointBaseGenerator ddlGenerator = new PointBaseGenerator(globalConf,
                dbConf);
        return ddlGenerator;
    }

    /**
     * create the DDL Generator for specific engine with the configurations if
     * the globalConf already defines specific db _conf, pass null for
     * specificConf
     * 
     * @param engine
     *            db engine
     * @param globalConf
     *            global configuration file
     * @param specificConf
     *            db configuration file
     * @return DDL generator ddl generator
     * @throws GeneratorException
     *             generator error
     */
    public static Generator createDDLGenerator(final String engine,
            final String globalConf, final String specificConf)
            throws GeneratorException {
        /** define the configuration file name of DDL Generator */
        String globConf = "conf/ddl.properties";
        String eng = engine;

        // verify the global configuration
        if (globalConf != null) {
            globConf = globalConf;
        }

        // load engine and specific database config
        Properties prop = new Properties();
        try {
            prop.load(new java.io.FileInputStream(globConf));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // adjust database engine
        if (eng == null) {
            eng = prop.getProperty(BaseConfiguration.DATABASE_ENGINE_KEY);
        }

        // no db engine
        if (eng == null) {
            throw new GeneratorException("database engine is not defined");
        }

        // mysql ddl generator
        if (eng.toLowerCase().equalsIgnoreCase("mysql")) {
            // adjust specific db config
            String specConf = "conf/mysql.properties";
            if (specificConf != null) {
                specConf = specificConf;
            }
            return createMySQLDDLGenerator(globConf, specConf);
        }

        // oracle ddl generator
        if (eng.toLowerCase().equalsIgnoreCase("oracle")) {
            // adjust specific db config
            String specConf = "conf/oracle.properties";
            if (specificConf != null) {
                specConf = specificConf;
            }

            return createOracleDDLGenerator(globConf, specConf);
        }

        // postgresql ddl generator
        if (eng.toLowerCase().equalsIgnoreCase("postgresql")) {
            // adjust specific db config
            String specConf = "conf/postgresql.properties";
            if (specificConf != null) {
                specConf = specificConf;
            }

            return createPostgreSQLDDLGenerator(globConf, specConf);
        }

        // derby ddl generator
        if (eng.toLowerCase().equalsIgnoreCase("derby")) {
            // adjust specific db config
            String specConf = "conf/derby.properties";
            if (specificConf != null) {
                specConf = specificConf;
            }

            return createDerbyDDLGenerator(globConf, specConf);
        }

        // mssql ddl generator
        if (eng.toLowerCase().equalsIgnoreCase("mssql")) {
            // adjust specific db config
            String specConf = "conf/mssql.properties";
            if (specificConf != null) {
                specConf = specificConf;
            }

            return createMssqlDDLGenerator(globConf, specConf);
        }

        // SapDB ddl generator
        if (eng.toLowerCase().equalsIgnoreCase("sapdb")) {
            // adjust specific db config
            String specConf = "conf/sapdb.properties";
            if (specificConf != null) {
                specConf = specificConf;
            }

            return createSapdbDDLGenerator(globConf, specConf);
        }

        // DB2 ddl generator
        if (eng.toLowerCase().equalsIgnoreCase("db2")) {
            // adjust specific db config
            String specConf = "conf/db2.properties";
            if (specificConf != null) {
                specConf = specificConf;
            }

            return createDb2DDLGenerator(globConf, specConf);
        }

        // Sybase ddl generator
        if (eng.toLowerCase().equalsIgnoreCase("sybase")) {
            // adjust specific db config
            String specConf = "conf/sybase.properties";
            if (specificConf != null) {
                specConf = specificConf;
            }

            return createSybaseDDLGenerator(globConf, specConf);
        }

        // Hsql ddl generator
        if (eng.toLowerCase().equalsIgnoreCase("hsql")) {
            // adjust specific db config
            String specConf = "conf/hsql.properties";
            if (specificConf != null) {
                specConf = specificConf;
            }

            return createHsqlDDLGenerator(globConf, specConf);
        }

        // PointBase ddl generator
        if (eng.toLowerCase().equalsIgnoreCase("pointbase")) {
            // adjust specific db config
            String specConf = "conf/pointbase.properties";
            if (specificConf != null) {
                specConf = specificConf;
            }

            return createPointBaseDDLGenerator(globConf, specConf);
        }

        // engine is not found
        throw new GeneratorException("can not create DDL generator for " + eng);
    }
}

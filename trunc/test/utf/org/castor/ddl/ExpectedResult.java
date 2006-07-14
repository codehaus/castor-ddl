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

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Vector;

import org.castor.ddl.Configuration;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.InputSource;

/**
 * this class represent the expected result for various database Created on Jun
 * 15, 2006 - 10:51:46 AM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public final class ExpectedResult {
    /** expected result mapping file  */
    public static final String EXPECTED_RESULT_MAPPING = "expected-mapping.xml";

    /** generic database engine */
    public static final String ENGINE_GENERIC = null;

    /** Mysql database engine */
    public static final String ENGINE_MYSQL = "mysql";

    /** PostgreSQL database engine */
    public static final String ENGINE_POSTGRESQL = "postgresql";

    /** HSQL database engine */
    public static final String ENGINE_HSQL = "hsql";

    /** PostgreSQL database engine */
    public static final String ENGINE_DB2 = "db2";

    /** Oracle database engine */
    public static final String ENGINE_ORACLE = "oracle";

    /** Oracle database engine */
    public static final String ENGINE_SAPDB = "sapdb";

    /** Derby database engine */
    public static final String ENGINE_DERBY = "derby";

    /** Mssql database engine */
    public static final String ENGINE_MSSQL = "mssql";

    /** PointBase database engine */
    public static final String ENGINE_POINTBASE = "pointbase";

    /** Sybase database engine */
    public static final String ENGINE_SYBASE = "sybase";

    /** hanlde expected ddls for various databsed */
    private Vector _ddls;

    /** hanlde error message */
    private String _message;

    /**configuration*/
    private Configuration _conf;

    /**
     * 
     * @return Returns the conf.
     */
    public Configuration getConf() {
        return _conf;
    }

    /**
     * Set the conf by _conf.
     * 
     * @param conf configuration
     */
    public void setConf(final Configuration conf) {
        _conf = conf;
        for (Iterator i = _ddls.iterator(); i.hasNext();) {
            Ddl ddl = (Ddl) i.next();
            ddl.setConf(conf);
        }
    }

    /**
     * Constructor for Expected
     */
    public ExpectedResult() {
        super();
    }

    /**
     * 
     * @param ddls ddls
     */
    public void setDdlsList(final Vector ddls) {
        _ddls = ddls;
    }

    /**
     * 
     * @return ddl list
     */
    public Vector getDdlsList() {
        return _ddls;
    }

    /**
     * 
     * @return ddl count
     */
    public int getDdlCount() {
        return _ddls.size();
    }

    /**
     * 
     * @param url url
     * @return expected result
     * @throws Exception exception
     */
    public static ExpectedResult getExpectedResult(final URL url)
            throws Exception {
        return getExpectedResult(url.toExternalForm());
    }

    /**
     * 
     * @param expFile expected file
     * @return expected result 
     * @throws Exception exception
     */
    public static ExpectedResult getExpectedResult(final String expFile)
            throws Exception {
        Mapping mapping = new Mapping();
        ExpectedResult er = null;

        // 1. Load the mapping information from the file
        try {
            mapping.loadMapping(ExpectedResult.class
                    .getResource(EXPECTED_RESULT_MAPPING));
            // 2. Unmarshal the data
            Unmarshaller unmar = new Unmarshaller(mapping);
            er = (ExpectedResult) unmar.unmarshal(new InputSource(expFile));

        } catch (MappingException e) {
            e.printStackTrace();
            throw e;
        } catch (MarshalException e) {
            e.printStackTrace();
            throw e;
        } catch (ValidationException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

        return er;
    }

    /**
     * 
     * @param engine db engine
     * @return ddl 
     */
    private Ddl getDdlByEngine(final String engine) {
        for (int i = 0; i < _ddls.size(); i++) {
            Ddl d = (Ddl) _ddls.get(i);
            if (d.getEngine() == null) {
                if (engine == null) {
                    return d;
                }
                continue;
            }

            if (d.getEngine().equals(engine)) {
                return d;
            }
        }
        return null;
    }

    /**
     * 
     * @param engine db engine
     * @param index index
     * @return ddl
     */
    private Ddl getDdlByEngineandIndex(final String engine, final int index) {
        for (int i = 0; i < _ddls.size(); i++) {
            Ddl d = (Ddl) _ddls.get(i);
            if (d.getEngine() == null) {
                if (engine == null && index == d.getIndex()) {
                    return d;
                }
                continue;
            }

            if (d.getEngine().equals(engine) && d.getIndex() == index) {
                return d;
            }
        }
        return null;
    }

    /**
     * match
     * 
     * @param actualResult actual result
     * @return is matched
     */
    public boolean match(final String actualResult) {
        return match(ENGINE_GENERIC, actualResult);
    }

    /**
     * match
     * 
     * @param engine db engine
     * @param actualResult actual result
     * @return is matched
     */
    public boolean match(final String engine, final String actualResult) {
        return match(engine, actualResult, new Object[0]);
    }

    /**
     * default engine is ENGINE_GENERIC
     * 
     * @param actualResult actual result
     * @param params params
     * @return is matched
     */
    public boolean match(final String actualResult, final Object[] params) {
        return match(ENGINE_GENERIC, actualResult, params);
    }

    /**
     * format expected result before matching
     * 
     * @param engine db engine
     * @param actualResult actual result
     * @param params params
     * @return is matched
     */
    public boolean match(final String engine, final String actualResult,
            final Object[] params) {

        _message = "";
        Ddl ddl = getDdlByEngine(engine);

        ddl.format(params);

        boolean r = false;
        try {
            r = ddl.match(actualResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        _message = ddl.toString();
        return r;
    }

    /**
     * 
     * @param engine db engine
     * @param index index
     * @param actualResult actual result
     * @param params params
     * @return is matched
     */
    public boolean match(final String engine, final int index,
            final String actualResult, final Object[] params) {

        _message = "";
        Ddl ddl = getDdlByEngineandIndex(engine, index);

        ddl.format(params);

        boolean r = false;
        try {
            r = ddl.match(actualResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        _message = ddl.toString();
        return r;
    }

    /**
     * 
     * @return Returns the message.
     */
    public String getMessage() {
        return _message;
    }

}

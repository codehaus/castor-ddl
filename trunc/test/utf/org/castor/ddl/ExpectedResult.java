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
 * this class represent the expected result for various database
 * Created on Jun 15, 2006 - 10:51:46 AM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public final class ExpectedResult {
    /** expected result mapping file*/
    public static final String EXPECTED_RESULT_MAPPING = "expected-mapping.xml";

    /** generic database engine*/
    public static final String ENGINE_GENERIC = null;

    /** mysql database engine*/
    public static final String ENGINE_MYSQL = "mysql";

    /** postgreSQL database engine*/
    public static final String ENGINE_POSTGRESQL = "postgresql";

    /** hanlde expected ddls  for various databsed*/
    private Vector _ddls;

    /** hanlde error message*/
    private String _message;
    
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
     * @param conf 
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
     * @param ddls
     */
    public void setDdlsList(final Vector ddls) {
        _ddls = ddls;
    }

    /**
     * 
     * @return
     */
    public Vector getDdlsList() {
        return _ddls;
    }

    /**
     * 
     * @return
     */
    public int getDdlCount() {
        return _ddls.size();
    }

    /**
     * 
     * @param className
     * @param expFile
     * @return
     * @throws Exception
     */
    public static ExpectedResult getExpectedResult(final URL url) throws Exception {
        return getExpectedResult(url.toExternalForm());
    }

    /**
     * 
     * @param expFile
     * @return
     * @throws Exception
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

    private Ddl getDdlByEngine(final String engine) {
        for (int i = 0; i < _ddls.size(); i++) {
            Ddl d = (Ddl) _ddls.get(i);
            if (d._engine == null) {
                if (engine == null) { return d; }
                continue;
            }

            if (d._engine.equals(engine)) { return d; }
        }
        return null;
    }

    private Ddl getDdlByEngineandIndex(final String engine, final int index) {
        for (int i = 0; i < _ddls.size(); i++) {
            Ddl d = (Ddl) _ddls.get(i);
            if (d._engine == null) {
                if (engine == null && index == d._index) { return d; }
                continue;
            }

            if (d._engine.equals(engine) && d._index == index) { return d; }
        }
        return null;
    }
    
    /**
     * match
     * @param actualResult
     * @return
     */
    public boolean match(final String actualResult) {
        return match(ENGINE_GENERIC, actualResult);
    }

    /**
     * match
     * @param engine
     * @param actualResult
     * @return
     */
    public boolean match(final String engine, final String actualResult) {
        return match(engine, actualResult, new Object[0]);
    }

    /**
     * default engine is ENGINE_GENERIC
     * @param actualResult
     * @param params
     * @return
     */
    public boolean match(final String actualResult, final Object []params) {
        return match(ENGINE_GENERIC, actualResult, params);
    }

    /**
     * format expected result before matching
     * @param engine
     * @param actualResult
     * @param params
     * @return
     */
    public boolean match(final String engine, final String actualResult,
            final Object[]params) {
        
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
     * @param engine
     * @param index
     * @param actualResult
     * @param params
     * @return
     */
    public boolean match(final String engine, final int index, final String actualResult,
            final Object[]params) {
        
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

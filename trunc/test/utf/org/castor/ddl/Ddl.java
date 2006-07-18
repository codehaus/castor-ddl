
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

import java.text.MessageFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.castor.ddl.Configuration;

/**
 * This class represents the expected ddl entry.
 * <br/>Created on Jun 15, 2006 - 11:22:45 AM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class Ddl {
    /**logging*/
    private static final Log LOG = LogFactory.getLog(Ddl.class);

    /** matching type, regular expression*/
    public static final String MATCHTYPE_REGEXP = "regexp";

    /** matching type, plain text */
    public static final String MATCHTYPE_PLAIN = "plain";

    /** matching type, exact match*/
    public static final String MATCHTYPE_EXACT = "exact";

    /** ddl string*/    
    private java.lang.String _ddl;

    /** matching type*/    
    private java.lang.String _matchtype;

    /** database engine*/
    private java.lang.String _engine;

    /** is case sensitive*/
    private boolean _casesensitive;

    /** index*/
    private int _index;
    
    /***configuration*/
    private Configuration _conf;
    
    /**
     * Constructor for Ddl
     */
    public Ddl() {
        super();
    }
    
    
    /**
     * 
     * Constructor for Ddl
     * @param ddl ddl 
     * @param matchtype match type
     * @param casesensitive is case sensitive
     */
    public Ddl(final String ddl, final String matchtype, final boolean casesensitive) {
        super();
        _ddl = ddl;
        _matchtype = matchtype;
        _casesensitive = casesensitive;
    }


    /**
     * @param actualResult actual result
     * @return true if matched
     * @throws Exception exception
     */
    public boolean match(final String actualResult) throws Exception {
        if (_matchtype == null || _matchtype.equals(MATCHTYPE_REGEXP)) {
            return matchRegExp(actualResult);
        }
        
        if (_matchtype.equals(MATCHTYPE_PLAIN)) {
            return matchPlain(actualResult);
        }
        
        if (_matchtype.equals(MATCHTYPE_EXACT)) {
            return matchExact(actualResult);            
        }
        
        throw new Exception("don't support match type " + _matchtype);
    }

    /**
     * 
     * @param actualResult actual result
     * @return true if matched
     */
    private boolean matchExact(final String actualResult) {
        if (_ddl == null) { 
            _ddl = "";
        }
        if (actualResult == null && _ddl == null) {
            return true;
        }
        
        if (actualResult == null || _ddl == null) {
            return false;
        }

        String expDDL = _ddl;
        String actual = actualResult;
        if (!_casesensitive) {
            actual = actualResult.toLowerCase();
            expDDL = expDDL.toLowerCase();
        }
        
        return actual.equals(expDDL);
    }

    /**
     * 
     * @param actualResult actual result
     * @return true if matched
     */
    private boolean matchPlain(final String actualResult) {
        if (_ddl == null) {
            _ddl = "";
        }
        if (actualResult == null && _ddl == null) {
            return true;
        }
        
        if (actualResult == null || _ddl == null) {
            return false;
        }
        
        String expDDL = _ddl;
        String actual = actualResult;
        if (!_casesensitive) {
            actual = actualResult.toLowerCase();
            expDDL = expDDL.toLowerCase();
        }
        
        actual = actual.replaceAll(_conf.getLineSeparator(), " ");
        actual = actual.replaceAll("[ \t]+", " ").trim();
        
        expDDL = expDDL.replaceAll(_conf.getLineSeparator(), " ");
        expDDL = expDDL.replaceAll("[ \t]+", " ");
        return actual.equals(expDDL);
    }

    /**
     * 
     * @param actualResult actual result
     * @return true if matched
     */
    private boolean matchRegExp(final String actualResult) {
        if (_ddl == null && actualResult == null) {
            return true;
        }
        
        if (_ddl == null) {
            _ddl = "";
        }

//        LOG.info("actual result: " + actualResult);
        System.out.println("actual result: " + actualResult);
        String expDDL = _ddl;
        String actual = actualResult;
        if (!_casesensitive) {
            actual = actualResult.toLowerCase().trim();
            expDDL = expDDL.toLowerCase().trim();
        }
        
        actual = actual.replaceAll(_conf.getLineSeparator(), " ");
        actual = actual.replaceAll(_conf.getLineIndent(), " ");
        actual = actual.replaceAll("[ \t]+", " ");
//        LOG.info("actual result converted: " + actual);
        System.out.println("actual result converted: " + actual);
        
        expDDL = expDDL.replaceAll(_conf.getLineSeparator(), " ");
        expDDL = expDDL.replaceAll("[ \t]+", " ");
//        LOG.info("expected result: " + expDDL);
        System.out.println("expected result: " + expDDL);
        return actual.matches(expDDL);
    }
    
    /**
     * toString
     * @return string
     */
    public String toString() {
        return " ddl: " + _ddl + "\n matchtype: " + _matchtype + "\n engine: " 
            + _engine + "\n casesensitive: " + _casesensitive;
    }
    
    /**
     * 
     * @param args params
     */
    public void format(final Object[]args) {
        if (_ddl == null || "".equals(_ddl)) { 
            return;
        }
        _ddl = MessageFormat.format(_ddl, args);
    }


    /**
     * 
     * @return Returns the conf.
     */
    public final Configuration getConf() {
        return _conf;
    }


    /**
     * Set the conf by _conf.
     * @param conf configuration
     */
    public final void setConf(final Configuration conf) {
        _conf = conf;
    }


    /**
     * 
     * @return Returns the casesensitive.
     */
    public final boolean isCasesensitive() {
        return _casesensitive;
    }


    /**
     * Set the casesensitive by _casesensitive.
     * @param casesensitive case sensitive
     */
    public final void setCasesensitive(final boolean casesensitive) {
        _casesensitive = casesensitive;
    }


    /**
     * 
     * @return Returns the ddl.
     */
    public final java.lang.String getDdl() {
        return _ddl;
    }


    /**
     * Set the ddl by _ddl.
     * @param ddl ddl 
     */
    public final void setDdl(final String ddl) {
        _ddl = ddl;
    }


    /**
     * 
     * @return Returns the engine.
     */
    public final java.lang.String getEngine() {
        return _engine;
    }


    /**
     * Set the engine by _engine.
     * @param engine engine
     */
    public final void setEngine(final String engine) {
        _engine = engine;
    }


    /**
     * 
     * @return Returns the index.
     */
    public final int getIndex() {
        return _index;
    }


    /**
     * Set the index by _index.
     * @param index index
     */
    public final void setIndex(final int index) {
        _index = index;
    }


    /**
     * 
     * @return Returns the matchtype.
     */
    public final String getMatchtype() {
        return _matchtype;
    }


    /**
     * Set the matchtype by _matchtype.
     * @param matchtype match type
     */
    public final void setMatchtype(final String matchtype) {
        _matchtype = matchtype;
    }        
}

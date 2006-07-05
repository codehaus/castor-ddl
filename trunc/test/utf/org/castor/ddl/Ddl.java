
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

import org.castor.ddl.BaseConfiguration;

/**
 * This class represents the expected ddl entry.
 * Created on Jun 15, 2006 - 11:22:45 AM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public final class Ddl {
    /** matching type, regular expression*/
    public static final String MATCHTYPE_REGEXP = "regexp";

    /** matching type, plain text*/
    public static final String MATCHTYPE_PLAIN = "plain";

    /** matching type, exact match*/
    public static final String MATCHTYPE_EXACT = "exact";

    /** ddl string*/    
    private String _ddl;

    /** matching type*/    
    private String _matchtype;

    /** database engine*/
    public String _engine;

    /** is case sensitive*/
    private boolean _casesensitive;

    /** index*/
    public int _index;
    
    /**
     * Constructor for Ddl
     */
    public Ddl() {
        super();
    }
    
    
    /**
     * Constructor for Ddl
     * @param ddl
     * @param matchtype
     * @param engine
     * @param casesensitive
     */
    public Ddl(final String ddl, final String matchtype, final boolean casesensitive) {
        super();
        // TODO Auto-generated constructor stub
        _ddl = ddl;
        _matchtype = matchtype;
        _casesensitive = casesensitive;
    }


    /**
     * matching with actual result
     * @param actualResult
     * @return
     * @throws Exception
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
     * @param actualResult
     * @return
     */
    private boolean matchExact(final String result) {
        String actualResult = result;
        
        if (_ddl == null) { _ddl = ""; }
        if (actualResult == null || _ddl == null) {
            return (actualResult == _ddl);
        }

        String expDDL = _ddl;
        if (!_casesensitive) {
            actualResult = actualResult.toLowerCase();
            expDDL = expDDL.toLowerCase();
        }
        
        return actualResult.equals(expDDL);
    }

    /**
     * @param actualResult
     * @return
     */
    private boolean matchPlain(final String result) {
        String actualResult = result;
        
        if (_ddl == null) { _ddl = ""; }
        
        if (actualResult == null || _ddl == null) {
            return (actualResult == _ddl);
        }
        
        String expDDL = _ddl;
        if (!_casesensitive) {
            actualResult = actualResult.toLowerCase();
            expDDL = expDDL.toLowerCase();
        }
        
        actualResult = actualResult.replaceAll(BaseConfiguration.LINE_SEPARATOR, " ");
        actualResult = actualResult.replaceAll("[ \t]+", " ").trim();
        
        expDDL = expDDL.replaceAll(BaseConfiguration.LINE_SEPARATOR, " ");
        expDDL = expDDL.replaceAll("[ \t]+", " ");
        return actualResult.equals(expDDL);
    }

    /**
     * @param actualResult
     * @return
     */
    private boolean matchRegExp(final String result) {
        String actualResult = result;
        
        if (_ddl == null) { _ddl = ""; }
        if (_ddl == null) {
            return (actualResult == _ddl);
        }

        if (actualResult == null) { return _ddl.matches(null); }
        
        System.out.println(actualResult);
        String expDDL = _ddl;
        if (!_casesensitive) {
            actualResult = actualResult.toLowerCase().trim();
            expDDL = expDDL.toLowerCase().trim();
        }
        
        actualResult = actualResult.replaceAll(BaseConfiguration.LINE_SEPARATOR, " ");
        actualResult = actualResult.replaceAll(BaseConfiguration.LINE_INDENT, " ");
        actualResult = actualResult.replaceAll("[ \t]+", " ");
        System.out.println(actualResult);
        
        expDDL = expDDL.replaceAll(BaseConfiguration.LINE_SEPARATOR, " ");
        expDDL = expDDL.replaceAll("[ \t]+", " ");
        System.out.println(expDDL);
        return actualResult.matches(expDDL);
    }
    
    /**
     * toString
     */
    public String toString() {
        return " ddl: " + _ddl + "\n matchtype: " + _matchtype + "\n engine: "
            + _engine + "\n casesensitive: " + _casesensitive;
    }
    
    public void format(final Object[]args) {
        if (_ddl == null || "".equals(_ddl)) { return; }
        _ddl = MessageFormat.format(_ddl, args);
    }
}


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

import org.castor.ddl.Configuration;

/**
 * This class represents the expected ddl entry.
 * Created on Jun 15, 2006 - 11:22:45 AM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class Ddl {
    /** matching type, regular expression*/
    public final static String MATCHTYPE_REGEXP = "regexp";

    /** matching type, plain text*/
    public final static String MATCHTYPE_PLAIN = "plain";

    /** matching type, exact match*/
    public final static String MATCHTYPE_EXACT = "exact";

    /** ddl string*/    
    public java.lang.String _ddl;

    /** matching type*/    
    public java.lang.String _matchtype;

    /** database engine*/
    public java.lang.String _engine;

    /** is case sensitive*/
    public boolean _casesensitive;

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
    public Ddl(String ddl, String matchtype, boolean casesensitive) {
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
    public boolean match(String actualResult) throws Exception{
        if(_matchtype == null || _matchtype.equals(MATCHTYPE_REGEXP)) {
            return matchRegExp(actualResult);
        }
        
        if(_matchtype.equals(MATCHTYPE_PLAIN)) {
            return matchPlain(actualResult);
        }
        
        if(_matchtype.equals(MATCHTYPE_EXACT)) {
            return matchExact(actualResult);            
        }
        
        throw new Exception("don't support match type " + _matchtype);
    }

    /**
     * @param actualResult
     * @return
     */
    private boolean matchExact(String actualResult) {
        if(_ddl == null ) _ddl = "";
        if(actualResult == null || _ddl == null) {
            if( actualResult == _ddl)
                return true;
            else 
                return false;
        }

        String expDDL = _ddl;
        if(!_casesensitive) {
            actualResult = actualResult.toLowerCase();
            expDDL = expDDL.toLowerCase();
        }
        
        return actualResult.equals(expDDL);
    }

    /**
     * @param actualResult
     * @return
     */
    private boolean matchPlain(String actualResult) {
        if(_ddl == null ) _ddl = "";
        
        if(actualResult == null || _ddl == null) {
            if( actualResult == _ddl)
                return true;
            else 
                return false;
        }
        
        String expDDL = _ddl;
        if(!_casesensitive) {
            actualResult = actualResult.toLowerCase();
            expDDL = expDDL.toLowerCase();
        }
        
        actualResult = actualResult.replaceAll(Configuration.LINE_SEPARATOR, " ");
        actualResult = actualResult.replaceAll("[ \t]+", " ").trim();
        
        expDDL = expDDL.replaceAll(Configuration.LINE_SEPARATOR, " ");
        expDDL = expDDL.replaceAll("[ \t]+", " ");
        return actualResult.equals(expDDL);
    }

    /**
     * @param actualResult
     * @return
     */
    private boolean matchRegExp(String actualResult) {
        if(_ddl == null ) _ddl = "";
        if(_ddl == null) {
            if( actualResult == _ddl)
                return true;
            else 
                return false;
        }

        if(actualResult == null)
            return _ddl.matches(null);
        
        System.out.println(actualResult);
        String expDDL = _ddl;
        if(!_casesensitive) {
            actualResult = actualResult.toLowerCase().trim();
            expDDL = expDDL.toLowerCase().trim();
        }
        
//        actualResult = actualResult.replaceAll(AbstractGenerator.LINE_SEPARATOR, " ");
        actualResult = actualResult.replaceAll(Configuration.LINE_SEPARATOR, " ");
        actualResult = actualResult.replaceAll(Configuration.LINE_INDENT, " ");
        actualResult = actualResult.replaceAll("[ \t]+", " ");
        System.out.println(actualResult);
        
        expDDL = expDDL.replaceAll(Configuration.LINE_SEPARATOR, " ");
        expDDL = expDDL.replaceAll("[ \t]+", " ");
        System.out.println(expDDL);
        return actualResult.matches(expDDL);
    }
    
    /**
     * toString
     */
    public String toString() {
        return " ddl: " + _ddl + "\n matchtype: " + _matchtype + "\n engine: " + _engine + "\n casesensitive: " + _casesensitive;
    }
    
    public void format(Object[]args) {
        if(_ddl == null || "".equals(_ddl))
            return;
        _ddl = MessageFormat.format(_ddl, args);
    }
}

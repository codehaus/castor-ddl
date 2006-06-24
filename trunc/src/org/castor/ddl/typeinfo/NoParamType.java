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

package org.castor.ddl.typeinfo;


/**
 * 
 * Created on Jun 23, 2006 - 11:06:38 PM
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class NoParamType implements TypeInfo {

    /** SQL type */
    private String _sqlName;

    /**
     * Construct a new TypeInfo instance with given SQL type name, SQL type
     * name,
     * 
     * @param name
     */
    public NoParamType(String name) {
        super();
        _sqlName = name;
    }

    /**
     * @return Returns the _sqlName.
     */
    public final String getSqlName() {
        return _sqlName;
    }

    /**
     * Set the sqlName by _sqlName.
     * 
     * @param sqlName
     */
    public void setSqlName(String sqlName) {
        _sqlName = sqlName;
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.castor.ddl.typeinfo.TypeInfo#getDDLString()
     */
    public String getDDLString() {
        return _sqlName;
    }

}

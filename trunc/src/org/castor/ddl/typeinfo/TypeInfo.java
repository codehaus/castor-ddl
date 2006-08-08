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

package org.castor.ddl.typeinfo;

import org.castor.ddl.GeneratorException;
import org.castor.ddl.schemaobject.Field;

/**
 * Interface associates JDBC to SQL type and its parameters.
 *  
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 * @version $Revision: 5951 $ $Date: 2006-04-25 16:09:10 -0600 (Tue, 25 Apr 2006) $
 */
public interface TypeInfo {
    /**
     * Get JDBC type.
     * 
     * @return The JDBC type.
     */
    String getJdbcType();
    
    /**
     * Get SQL type.
     * 
     * @return The SQL type.
     */
    String getSqlType();
    
    /**
     * Build DDL string with SQL type and parameters.
     * 
     * @param field The field to get specific parameters from.
     * @return Type string for DDL.
     * @throws GeneratorException If required parameters is not defined.
     */
    String toDDL(Field field) throws GeneratorException;

    /**
     * @param type type infor
     * @throws GeneratorException exception
     */
    void merge(TypeInfo type) throws GeneratorException;
}

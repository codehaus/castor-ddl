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
package org.castor.ddl.keygenerator;

import org.castor.ddl.schemaobject.KeyGenerator;
import org.exolab.castor.mapping.xml.KeyGeneratorDef;

/**
 * IDENTITY key generator can be used only with autoincrement primary key columns 
 * (identities) with Sybase ASE/ASA, MS SQL Server, MySQL and Hypersonic SQL. 
 * After the insert the key generator selects system variable @@identity which 
 * contains the last identity value for the current database connection. In the case 
 * of MySQL and Hypersonic SQL the system functions LAST_INSERT_ID() and IDENTITY() 
 * are called, respectively.
 * 
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public final class IdentityKeyGenerator extends KeyGenerator {
    //--------------------------------------------------------------------------

    /** Name of key generator algorithm. */
    public static final String ALGORITHM_NAME = "IDENTITY";

    //--------------------------------------------------------------------------

    /**
     * Constructor for default IDENTITY key generator.
     */
    public IdentityKeyGenerator() {
        super(ALGORITHM_NAME, ALGORITHM_NAME);
    }
    
    /**
     * Constructor for IDENTITY key generator specified by given defintion.
     * 
     * @param definition Key generator definition.
     */
    public IdentityKeyGenerator(final KeyGeneratorDef definition) {
        super(ALGORITHM_NAME, definition.getAlias());
    }

    //--------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public String toCreateDDL() { return ""; }

    /**
     * {@inheritDoc}
     */
    public String toDropDDL() { return ""; }

    //--------------------------------------------------------------------------
}

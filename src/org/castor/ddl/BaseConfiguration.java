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

package org.castor.ddl;

/**
 * this class handles some basic configuration information for DDL generator
 * Created on Jun 12, 2006 - 12:11:52 AM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public class BaseConfiguration {

    /**
     * Constructor for BaseConfiguration
     */
    public BaseConfiguration() {
        super();
        // TODO Auto-generated constructor stub
    }

    /** option for generate DDL for CREATE statement*/
    public static final String GENERATE_DDL_FOR_CREATE = "generate_ddl_for_create";

    /** option for generate DDL for ALTER statement*/
    public static final String GENERATE_DDL_FOR_ALTER = "generate_ddl_for_alter";

    /** option for generate DDL for DROP statement*/
    public static final String GENERATE_DDL_FOR_DROP = "generate_ddl_for_drop";


}

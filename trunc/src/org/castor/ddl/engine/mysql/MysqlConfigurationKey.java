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

package org.castor.ddl.engine.mysql;

/**
 * My SQL configuration key
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public final class MysqlConfigurationKey {

    /** storage engine*/
    public static final String STORAGE_ENGINE = "engine";

    /** on delete*/
    public static final String FOREIGN_KEY_ON_DELETE = "foreign_key_on_delete";

    /** storage engine*/
    public static final String FOREIGN_KEY_ON_UPDATE = "foreign_key_on_update";
}

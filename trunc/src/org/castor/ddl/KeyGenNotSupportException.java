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
 * Key generator exception
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */

public final class KeyGenNotSupportException extends GeneratorException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1270070878985011180L;

    /**
     * Constructor for KeyGenNotSupportException
     * @param arg0 message
     */
    public KeyGenNotSupportException(final String arg0) {
        super(arg0);
    }

    /**
     * Constructor for KeyGenNotSupportException
     * @param message exception message
     * @param e original exception
     */
    public KeyGenNotSupportException(final String message, final Exception e) {
        super(message, e);
    }

}

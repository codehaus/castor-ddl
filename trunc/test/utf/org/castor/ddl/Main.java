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

/**
 * 
 * Created on Jun 15, 2006 - 3:46:59 PM
 * @author <a href="mailto:leducbao@gmail.com">Le Duc Bao</a>
 */
public final class Main {

    /**
     * @param args
     */
    public static void main(final String[] args) {
        String msg = "Hello {0}, and {1}";
        Object[]arg = new Object[] {"Bao", new Integer(1)};
        
        System.out.println(MessageFormat.format(msg, arg));
    }

    private Main() { }
}

/*
 * Copyright 2014, Kaazing Corporation. All rights reserved.
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
package org.kaazing.netx.ws.specification.ext.primary;

import java.nio.CharBuffer;

import org.kaazing.netx.ws.WsURLConnection;
import org.kaazing.netx.ws.internal.ext.WebSocketExtensionHooks;
import org.kaazing.netx.ws.internal.ext.function.WebSocketFrameSupplier;

public class PrimaryExtensionHooks extends WebSocketExtensionHooks {
    public PrimaryExtensionHooks() {
        super();

        super.whenTextFrameReceived = new WebSocketFrameSupplier<WsURLConnection, CharBuffer>() {

            @Override
            public CharBuffer apply(WsURLConnection connection, CharBuffer payload) {
                String str = "Hello, " + payload.toString();
                char[] cbuf = str.toCharArray();
                return CharBuffer.wrap(cbuf);
            }
        };

        super.whenTextFrameIsBeingSent = new WebSocketFrameSupplier<WsURLConnection, CharBuffer>() {

            @Override
            public CharBuffer apply(WsURLConnection connection, CharBuffer payload) {
                String str = payload.toString();
                if (str.startsWith("Hello, ")) {
                    str = str.substring("Hello,  ".length() - 1);
                }
                return CharBuffer.wrap(str.toCharArray());
            }
        };
    }
}

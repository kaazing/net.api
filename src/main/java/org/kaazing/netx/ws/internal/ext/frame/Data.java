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
package org.kaazing.netx.ws.internal.ext.frame;

import org.kaazing.netx.ws.internal.ext.agrona.DirectBuffer;
import org.kaazing.netx.ws.internal.util.Utf8Util;

public class Data extends Frame {
    private final int maxWsMessageSize;

    Data(int maxWsMessageSize)
    {
        this.maxWsMessageSize = maxWsMessageSize;
    }

    @Override
    public Payload getPayload() {
        Payload payload = super.getPayload();
        if (getOpCode() == OpCode.TEXT) {
            if (!Utf8Util.validBytesUTF8(payload.buffer(), payload.offset(), getLength())) {
                protocolError("Invalid UTF-8 byte");
            }

        }
        return payload;
    }

    @Override
    protected int getMaxPayloadLength() {
        return maxWsMessageSize;
    }

    public Data wrap(DirectBuffer buffer, int offset) {
        super.wrap(buffer, offset, false);
        return this;
    }
}

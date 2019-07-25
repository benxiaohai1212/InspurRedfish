/*
 * Copyright (c) 2015-2018 Intel Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.inspur.redfish.common.types.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.inspur.redfish.common.types.net.MacAddress;

import java.io.IOException;

public class MacAddressDeserializer extends JsonDeserializer<MacAddress> {
    @Override
    public MacAddress deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        String macAddressString = jsonParser.getValueAsString();
        return MacAddress.macAddressOrNull(macAddressString);
    }
}

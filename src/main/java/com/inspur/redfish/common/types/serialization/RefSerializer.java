/*
 * Copyright (c) 2017-2018 inspur Corporation
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

package com.inspur.redfish.common.types.serialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.inspur.redfish.common.types.Ref;

public class RefSerializer extends JsonSerializer<Ref> {

    @Override
    public void serialize(Ref value, JsonGenerator jgen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            if (value.isAssigned()) {
                jgen.writeObject(value.get());
            }
        } else {
            jgen.writeNull();
        }
    }
}

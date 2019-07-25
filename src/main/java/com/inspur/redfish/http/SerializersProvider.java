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

package com.inspur.redfish.http;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.inspur.redfish.common.types.NeighborInfo;
import com.inspur.redfish.common.types.Ref;
import com.inspur.redfish.common.types.Status;
import com.inspur.redfish.common.types.deserialization.BooleanDeserializer;
import com.inspur.redfish.common.types.deserialization.EnumeratedTypeDeserializer;
import com.inspur.redfish.common.types.deserialization.MacAddressDeserializer;
import com.inspur.redfish.common.types.deserialization.NeighborInfoDeserializer;
import com.inspur.redfish.common.types.deserialization.RefDeserializer;
import com.inspur.redfish.common.types.deserialization.StatusDeserializer;
import com.inspur.redfish.common.types.net.MacAddress;
import com.inspur.redfish.common.types.serialization.EnumeratedTypeSerializer;
import com.inspur.redfish.common.types.serialization.OptionalSerializer;
import com.inspur.redfish.common.types.serialization.RefSerializer;

import java.util.Optional;

import static com.inspur.redfish.common.types.EnumeratedType.SUB_TYPES;

public class SerializersProvider {
    @SuppressWarnings({"unchecked"})
    public SimpleModule getSerializersModule() {
        SimpleModule serializersModule = new SimpleModule();

        for (Class subType : SUB_TYPES) {
            serializersModule.addSerializer(subType, new EnumeratedTypeSerializer<>());
            serializersModule.addDeserializer(subType, new EnumeratedTypeDeserializer<>(subType));
        }

        serializersModule.addDeserializer(MacAddress.class, new MacAddressDeserializer());
        serializersModule.addDeserializer(NeighborInfo.class, new NeighborInfoDeserializer());
        serializersModule.addDeserializer(Status.class, new StatusDeserializer());
        serializersModule.addDeserializer(Ref.class, new RefDeserializer());
        serializersModule.addDeserializer(Boolean.class, new BooleanDeserializer());

        serializersModule.addSerializer(Optional.class, new OptionalSerializer(Optional.class));
        serializersModule.addSerializer(Ref.class, new RefSerializer());
        return serializersModule;
    }
}

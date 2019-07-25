/*
 * Copyright (c) 2016-2018 Intel Corporation
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

package com.inspur.redfish.south.resources.redfish;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.inspur.redfish.http.OdataTypes;
import com.inspur.redfish.south.typeidresolver.ResourceResolver;

@OdataTypes("OemVendor")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
    defaultImpl = OemVendor.class,
    use = NAME,
    include = PROPERTY,
    property = "@odata.type",
    visible = true
)
@JsonTypeIdResolver(ResourceResolver.class)
public class OemVendor {
    @JsonProperty("@odata.id")
    private String oDataId;
}

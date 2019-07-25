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

package com.inspur.redfish.south.resources.redfish;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.inspur.redfish.common.types.AddressState;
import com.inspur.redfish.common.types.IpV6AddressOrigin;
import com.inspur.redfish.common.types.redfish.IgnoreAutomaticOem;
import com.inspur.redfish.south.resources.UnknownOemsHelper;

public class IpV6AddressObject {
    @JsonProperty("Address")
    private String address;
    @JsonProperty("PrefixLength")
    private Integer prefixLength;
    @JsonProperty("AddressOrigin")
    private IpV6AddressOrigin addressOrigin;
    @JsonProperty("AddressState")
    private AddressState addressState;
    @IgnoreAutomaticOem
    private String oem;

    public String getAddress() {
        return address;
    }

    public Integer getPrefixLength() {
        return prefixLength;
    }

    public IpV6AddressOrigin getAddressOrigin() {
        return addressOrigin;
    }

    public AddressState getAddressState() {
        return addressState;
    }

    public String getOem() {
        return oem;
    }

    @JsonProperty("Oem")
    public void setOem(JsonNode jsonNode) throws JsonProcessingException {
        oem = UnknownOemsHelper.convertJsonNodeToString(jsonNode);
    }
}

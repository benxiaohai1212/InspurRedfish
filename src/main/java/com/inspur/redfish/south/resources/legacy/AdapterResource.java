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

package com.inspur.redfish.south.resources.legacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inspur.redfish.common.types.Protocol;
import com.inspur.redfish.common.types.Status;
import com.inspur.redfish.common.types.redfish.OdataTypeVersions;
import com.inspur.redfish.http.LinkName;
import com.inspur.redfish.http.OdataTypes;
import com.inspur.redfish.http.WebClientRequestException;
import com.inspur.redfish.south.reader.ResourceSupplier;
import com.inspur.redfish.south.resources.ExternalServiceResource;
import com.inspur.redfish.south.resources.ExternalServiceResourceImpl;
import com.inspur.redfish.south.resources.ODataId;

@OdataTypes({
    "#Adapter" + OdataTypeVersions.VERSION_PATTERN + "Adapter"
})
public class AdapterResource extends ExternalServiceResourceImpl implements ExternalServiceResource {
    @JsonProperty("Interface")
    private Protocol scInterface;
    @JsonProperty("Manufacturer")
    private String manufacturer;
    @JsonProperty("Model")
    private String model;
    @JsonProperty("Status")
    private Status status;
    @JsonProperty("Devices")
    private ODataId devices;
    @JsonProperty("Links")
    private Links links = new Links();

    public Protocol getInterface() {
        return scInterface;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public Status getStatus() {
        return status;
    }

    @LinkName("devices")
    public Iterable<ResourceSupplier> getDevices() throws WebClientRequestException {
        return processMembersListResource(devices);
    }

    public ResourceSupplier getComputerSystem() {
        return toSupplier(links.containedBy);
    }

    public class Links extends RedfishLinks {
        @JsonProperty("ContainedBy")
        private ODataId containedBy;
    }
}

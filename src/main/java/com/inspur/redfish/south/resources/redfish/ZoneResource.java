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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    "#Zone" + OdataTypeVersions.VERSION_PATTERN + "Zone"
})
public class ZoneResource extends ExternalServiceResourceImpl implements ExternalServiceResource {
    @JsonProperty("Status")
    private Status status;

    @JsonProperty("Links")
    private Links links = new Links();

    public Status getStatus() {
        return status;
    }

    @LinkName("endpointInZone")
    public Iterable<ResourceSupplier> getEndpoints() throws WebClientRequestException {
        return toSuppliers(links.endpoints);
    }

    @LinkName("switchInZone")
    public Iterable<ResourceSupplier> getInvolvedSwitches() throws WebClientRequestException {
        return toSuppliers(links.involvedSwitches);
    }

    public class Links extends RedfishLinks {
        @JsonProperty("Endpoints")
        private List<ODataId> endpoints;
        @JsonProperty("InvolvedSwitches")
        private List<ODataId> involvedSwitches;
    }
}

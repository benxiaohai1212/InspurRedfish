/*
 * Copyright (c) 2017-2018 Intel Corporation
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

import static com.fasterxml.jackson.annotation.Nulls.AS_EMPTY;
import static com.inspur.redfish.common.types.Ref.unassigned;
import static com.inspur.redfish.common.types.annotations.AsUnassigned.Strategy.WHEN_NULL;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.inspur.redfish.common.types.Ref;
import com.inspur.redfish.common.types.Status;
import com.inspur.redfish.common.types.annotations.AsUnassigned;
import com.inspur.redfish.common.types.redfish.OdataTypeVersions;
import com.inspur.redfish.http.LinkName;
import com.inspur.redfish.http.OdataTypes;
import com.inspur.redfish.http.WebClientRequestException;
import com.inspur.redfish.south.reader.ResourceSupplier;
import com.inspur.redfish.south.resources.ExternalServiceResource;
import com.inspur.redfish.south.resources.ExternalServiceResourceImpl;
import com.inspur.redfish.south.resources.ODataId;

@OdataTypes({
    "#NetworkInterface" + OdataTypeVersions.VERSION_PATTERN + "NetworkInterface"
})
public class NetworkInterfaceResource extends ExternalServiceResourceImpl implements ExternalServiceResource {
    @JsonSetter(value = "Status", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<Status> status = unassigned();

    @JsonProperty("NetworkDeviceFunctions")
    private ODataId networkDeviceFunctions;

    public Ref<Status> getStatus() {
        return status;
    }

    @LinkName("networkDeviceFunctions")
    public Iterable<ResourceSupplier> getNetworkDeviceFunctions() throws WebClientRequestException {
        return processMembersListResource(networkDeviceFunctions);
    }
}

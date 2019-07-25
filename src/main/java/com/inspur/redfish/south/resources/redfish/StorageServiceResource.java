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

import static com.inspur.redfish.common.types.redfish.OdataTypeVersions.VERSION_PATTERN;
import static com.inspur.redfish.common.types.redfish.OemType.Type.OEM_IN_LINKS;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.inspur.redfish.common.types.Status;
import com.inspur.redfish.common.types.redfish.OemType;
import com.inspur.redfish.http.LinkName;
import com.inspur.redfish.http.OdataTypes;
import com.inspur.redfish.http.WebClientRequestException;
import com.inspur.redfish.south.reader.ResourceSupplier;
import com.inspur.redfish.south.resources.ExternalServiceResource;
import com.inspur.redfish.south.resources.ExternalServiceResourceImpl;
import com.inspur.redfish.south.resources.ODataId;

@OdataTypes({
    "#StorageService" + VERSION_PATTERN + "StorageService"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public final class StorageServiceResource extends ExternalServiceResourceImpl implements ExternalServiceResource {
    @JsonProperty("Status")
    private Status status;
    @JsonProperty("Volumes")
    private ODataId volumes;
    @JsonProperty("Drives")
    private ODataId drives;
    @JsonProperty("Endpoints")
    private ODataId endpoints;
    @JsonProperty("StoragePools")
    private ODataId storagePools;

    @JsonProperty("Links")
    private Links links = new Links();

    public Status getStatus() {
        return status;
    }

    @LinkName("physicalDrives")
    public Iterable<ResourceSupplier> getDrives() throws WebClientRequestException {
        return processMembersListResource(drives);
    }

    @LinkName("endpoints")
    public Iterable<ResourceSupplier> getEndpoints() throws WebClientRequestException {
        return processMembersListResource(endpoints);
    }

    @LinkName("storagePools")
    public Iterable<ResourceSupplier> getStoragePools() throws WebClientRequestException {
        return processMembersListResource(storagePools);
    }

    @LinkName("volumes")
    public Iterable<ResourceSupplier> getVolumes() throws WebClientRequestException {
        return processMembersListResource(volumes);
    }

    @LinkName("managersInStorage")
    public Iterable<ResourceSupplier> getManagedBy() throws WebClientRequestException {
        return toSuppliers(links.oem.rackScaleOem.managedBy);
    }

    @LinkName("hostingSystemInStorage")
    public ResourceSupplier getHostingSystem() {
        if (links.hostingSystem == null) {
            return null;
        }
        return toSupplier(links.hostingSystem);
    }

    public class Links extends RedfishLinks {
        @JsonProperty("HostingSystem")
        private ODataId hostingSystem;

        @JsonProperty("Oem")
        private Oem oem = new Oem();

        @OemType(OEM_IN_LINKS)
        public class Oem extends RedfishOem {
            @JsonProperty("Intel_RackScale")
            private RackScaleOem rackScaleOem = new RackScaleOem();

            @JsonPropertyOrder({"managedBy"})
            public class RackScaleOem {
                private Set<ODataId> managedBy = new HashSet<>();
            }
        }
    }
}

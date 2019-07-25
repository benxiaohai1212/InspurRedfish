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

import static com.inspur.redfish.common.types.redfish.OdataTypeVersions.VERSION_PATTERN;
import static com.inspur.redfish.common.types.redfish.OemType.Type.TOP_LEVEL_OEM;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    "#StoragePool" + VERSION_PATTERN + "StoragePool"
})
public class StoragePoolResource extends ExternalServiceResourceImpl implements ExternalServiceResource {

    @JsonProperty("Identifier")
    private IdentifierObject identifier;

    @JsonProperty("Status")
    private Status status;

    @JsonProperty("BlockSizeBytes")
    private BigDecimal blockSizeBytes;

    @JsonProperty("Capacity")
    private CapacityResource capacity;

    @JsonProperty("CapacitySources")
    private List<CapacitySourcesResource> capacitySources = new ArrayList<>();

    @JsonProperty("AllocatedVolumes")
    private ODataId allocatedVolumes;

    @JsonProperty("AllocatedPools")
    private ODataId allocatedPools;

    public IdentifierObject getIdentifier() {
        return identifier;
    }

    public void setIdentifier(IdentifierObject identifier) {
        this.identifier = identifier;
    }

    public BigDecimal getBlockSizeBytes() {
        return blockSizeBytes;
    }

    public void setBlockSizeBytes(BigDecimal blockSizeBytes) {
        this.blockSizeBytes = blockSizeBytes;
    }

    public CapacityResource getCapacity() {
        return capacity;
    }

    public void setCapacity(CapacityResource capacity) {
        this.capacity = capacity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @LinkName("allocatedVolumes")
    public Iterable<ResourceSupplier> getAllocatedVolumes() throws WebClientRequestException {
        return processMembersListResource(allocatedVolumes);
    }

    @LinkName("allocatedPools")
    public Iterable<ResourceSupplier> getAllocatedPools() throws WebClientRequestException {
        return processMembersListResource(allocatedPools);
    }

    @LinkName("capacitySourcesInStoragePool")
    public Iterable<ResourceSupplier> getCapacitySources() throws WebClientRequestException {
        return toSuppliersFromEmbeddableResourceElement(capacitySources, "capacitySources");
    }

    @OemType(TOP_LEVEL_OEM)
    public class Oem extends RedfishOem {
        @JsonProperty("Intel_RackScale")
        private RackScaleOem rackScaleOem = new RackScaleOem();

        public class RackScaleOem {
            @JsonProperty("@odata.type")
            private final String oDataType = "#Intel.Oem.StoragePool";
        }
    }
}

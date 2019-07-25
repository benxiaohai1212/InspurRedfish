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
import static com.inspur.redfish.common.types.annotations.AsUnassigned.Strategy.WHEN_EMPTY_COLLECTION;
import static com.inspur.redfish.common.types.annotations.AsUnassigned.Strategy.WHEN_NULL;
import static com.inspur.redfish.common.types.redfish.OdataTypeVersions.VERSION_PATTERN;
import static com.inspur.redfish.common.types.redfish.OemType.Type.OEM_IN_LINKS;
import static com.inspur.redfish.common.types.redfish.OemType.Type.TOP_LEVEL_OEM;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.inspur.redfish.common.types.EncryptionTypes;
import com.inspur.redfish.common.types.Ref;
import com.inspur.redfish.common.types.Status;
import com.inspur.redfish.common.types.StorageAccessCapability;
import com.inspur.redfish.common.types.VolumeType;
import com.inspur.redfish.common.types.actions.InitializeType;
import com.inspur.redfish.common.types.annotations.AsUnassigned;
import com.inspur.redfish.common.types.redfish.OemType;
import com.inspur.redfish.http.LinkName;
import com.inspur.redfish.http.OdataTypes;
import com.inspur.redfish.http.WebClientRequestException;
import com.inspur.redfish.south.reader.ResourceSupplier;
import com.inspur.redfish.south.resources.ExternalServiceResourceImpl;
import com.inspur.redfish.south.resources.ExternalServiceResourceImpl.RedfishActions;
import com.inspur.redfish.south.resources.ExternalServiceResourceImpl.RedfishLinks;
import com.inspur.redfish.south.resources.ExternalServiceResourceImpl.RedfishOem;
import com.inspur.redfish.south.resources.ODataId;

@OdataTypes({
    "#Volume" + VERSION_PATTERN + "Volume"
})
@SuppressWarnings({"checkstyle:MethodCount"})
public class VolumeResource extends ExternalServiceResourceImpl {
    @JsonProperty("Status")
    private Status status;
    @JsonProperty("CapacityBytes")
    private Number capacityBytes;
    @JsonProperty("Capacity")
    private CapacityResource capacity;
    @JsonProperty("CapacitySources")
    private List<CapacitySourcesResource> capacitySources;
    @JsonProperty("VolumeType")
    private VolumeType volumeType;
    @JsonProperty("Encrypted")
    private Boolean encrypted;
    @JsonProperty("EncryptionTypes")
    private EncryptionTypes encryptionTypes;
    @JsonProperty("Identifiers")
    private Set<IdentifierObject> identifiers;
    @JsonProperty("BlockSizeBytes")
    private Number blockSizeBytes;
    @JsonProperty("Operations")
    private Set<OperationResource> operations;
    @JsonProperty("OptimumIOSizeBytes")
    private Number optimumIOSizeBytes;
    @JsonProperty("Manufacturer")
    private String manufacturer;
    @JsonProperty("Model")
    private String model;
    @JsonProperty("AccessCapabilities")
    private List<StorageAccessCapability> accessCapabilities;
    @JsonProperty("ReplicaInfos")
    private List<ReplicaInfoResource> replicaInfos;
    @JsonProperty("Links")
    private Links links = new Links();
    @JsonProperty("Actions")
    private Actions actions = new Actions();
    @JsonProperty("Oem")
    private Oem oem = new Oem();

    public Status getStatus() {
        return status;
    }

    public Number getCapacityBytes() {
        return capacityBytes;
    }

    public CapacityResource getCapacity() {
        return capacity;
    }

    public VolumeType getVolumeType() {
        return volumeType;
    }

    public Boolean getEncrypted() {
        return encrypted;
    }

    public EncryptionTypes getEncryptionTypes() {
        return encryptionTypes;
    }

    public Set<IdentifierObject> getIdentifiers() {
        return identifiers;
    }

    public Number getBlockSizeBytes() {
        return blockSizeBytes;
    }

    public Set<OperationResource> getOperations() {
        return operations;
    }

    public Number getOptimumIOSizeBytes() {
        return optimumIOSizeBytes;
    }

    public Actions getActions() {
        return actions;
    }

    public Boolean getBootable() {
        return oem.rackScaleOem.bootable;
    }

    public Boolean getErased() {
        return oem.rackScaleOem.erased;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public List<StorageAccessCapability> getAccessCapabilities() {
        return accessCapabilities;
    }

    @LinkName("replicaInfosInVolume")
    public Iterable<ResourceSupplier> getReplicaInfos() {
        return toSuppliersFromEmbeddableResourceElement(replicaInfos, "replicaInfos");
    }

    @LinkName("capacitySourcesInVolume")
    public Iterable<ResourceSupplier> getCapacitySources() {
        return toSuppliersFromEmbeddableResourceElement(capacitySources, "capacitySources");
    }

    @LinkName("drives")
    public Iterable<ResourceSupplier> getDrives() throws WebClientRequestException {
        return toSuppliers(links.drives);
    }

    @LinkName("volumeMetrics")
    public ResourceSupplier getVolumeMetrics() {
        if (oem.rackScaleOem.volumeMetrics == null) {
            return null;
        }
        return toSupplier(oem.rackScaleOem.volumeMetrics);
    }

    public final class Links extends RedfishLinks {
        @JsonProperty("Drives")
        private Set<ODataId> drives = new HashSet<>();

        @JsonProperty("Oem")
        private Oem oem = new Oem();

        @OemType(OEM_IN_LINKS)
        public class Oem extends RedfishOem {
            @JsonProperty("Intel_RackScale")
            private RackScaleOem rackScaleOem = new RackScaleOem();

            public class RackScaleOem {
                @JsonProperty("Endpoints")
                private Set<ODataId> endpoints = new HashSet<>();
            }
        }
    }

    public class Actions extends RedfishActions {
        @JsonProperty("#Volume.Initialize")
        private VolumeResource.Actions.Initialize initialize = new VolumeResource.Actions.Initialize();

        public Initialize getInitialize() {
            return initialize;
        }

        public class Initialize {
            @JsonSetter(value = "target", nulls = AS_EMPTY)
            private Ref<String> target = unassigned();

            @JsonSetter(value = "InitializeType@Redfish.AllowableValues", nulls = AS_EMPTY)
            @AsUnassigned({WHEN_NULL, WHEN_EMPTY_COLLECTION})
            private Ref<Set<InitializeType>> allowableValues = unassigned();

            public Ref<Set<InitializeType>> getAllowableValues() {
                return allowableValues;
            }

            public Ref<String> getTarget() {
                return target;
            }
        }
    }

    @OemType(TOP_LEVEL_OEM)
    public class Oem extends RedfishOem {
        @JsonProperty("Intel_RackScale")
        private RackScaleOem rackScaleOem = new RackScaleOem();

        public class RackScaleOem {
            @JsonProperty("Bootable")
            private Boolean bootable;

            @JsonProperty("Erased")
            private Boolean erased;

            @JsonProperty("Metrics")
            private ODataId volumeMetrics;
        }
    }
}

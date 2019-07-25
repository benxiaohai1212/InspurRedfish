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

import static com.fasterxml.jackson.annotation.Nulls.AS_EMPTY;
import static com.inspur.redfish.common.types.DiscoveryState.BASIC;
import static com.inspur.redfish.common.types.Ref.unassigned;
import static com.inspur.redfish.common.types.annotations.AsUnassigned.Strategy.WHEN_EMPTY_COLLECTION;
import static com.inspur.redfish.common.types.annotations.AsUnassigned.Strategy.WHEN_NULL;
import static com.inspur.redfish.common.types.redfish.OdataTypeVersions.VERSION_PATTERN;
import static com.inspur.redfish.common.types.redfish.OemType.Type.OEM_IN_ACTIONS;
import static com.inspur.redfish.common.types.redfish.OemType.Type.TOP_LEVEL_OEM;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.inspur.redfish.common.types.DiscoveryState;
import com.inspur.redfish.common.types.HostingRole;
import com.inspur.redfish.common.types.IndicatorLed;
import com.inspur.redfish.common.types.InterfaceType;
import com.inspur.redfish.common.types.PowerState;
import com.inspur.redfish.common.types.Ref;
import com.inspur.redfish.common.types.Status;
import com.inspur.redfish.common.types.SystemType;
import com.inspur.redfish.common.types.actions.ResetType;
import com.inspur.redfish.common.types.annotations.AsUnassigned;
import com.inspur.redfish.common.types.redfish.OemType;
import com.inspur.redfish.common.types.redfish.RedfishComputerSystem;
import com.inspur.redfish.http.LinkName;
import com.inspur.redfish.http.OdataTypes;
import com.inspur.redfish.http.WebClientRequestException;
import com.inspur.redfish.south.reader.ResourceSupplier;
import com.inspur.redfish.south.resources.ExternalServiceResource;
import com.inspur.redfish.south.resources.ExternalServiceResourceImpl;
import com.inspur.redfish.south.resources.ODataId;

@OdataTypes({
    "#ComputerSystem" + VERSION_PATTERN + "ComputerSystem"
})
@SuppressWarnings({"checkstyle:ClassFanOutComplexity", "checkstyle:MethodCount"})
public class ComputerSystemResource extends ExternalServiceResourceImpl implements ExternalServiceResource {
    @JsonSetter(value = "UUID", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<UUID> uuid = unassigned();
    @JsonSetter(value = "Manufacturer", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<String> manufacturer = unassigned();
    @JsonSetter(value = "Model", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<String> model = unassigned();
    @JsonSetter(value = "SerialNumber", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<String> serialNumber = unassigned();
    @JsonSetter(value = "SystemType", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<SystemType> systemType = unassigned();
    @JsonSetter(value = "AssetTag", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<String> assetTag = unassigned();
    @JsonSetter(value = "BiosVersion", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<String> biosVersion = unassigned();
    @JsonSetter(value = "SKU", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<String> sku = unassigned();
    @JsonSetter(value = "HostName", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<String> hostName = unassigned();
    @JsonSetter(value = "IndicatorLED", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<IndicatorLed> indicatorLed = unassigned();
    @JsonSetter(value = "Status", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<Status> status = unassigned();
    @JsonSetter(value = "PowerState", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<PowerState> powerState = unassigned();
    @JsonSetter(value = "PartNumber", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<String> partNumber = unassigned();
    @JsonProperty("Memory")
    private ODataId memory;
    @JsonProperty("ProcessorSummary")
    private ProcessorSummaryObject processorSummary = new ProcessorSummaryObject();
    @JsonProperty("MemorySummary")
    private MemorySummaryObject memorySummary = new MemorySummaryObject();
    @JsonProperty("Processors")
    private ODataId processors;
    @JsonProperty("EthernetInterfaces")
    private ODataId ethernetInterfaces;
    @JsonProperty("SimpleStorage")
    private ODataId simpleStorages;
    @JsonProperty("Storage")
    private ODataId storages;
    @JsonProperty("PCIeDevices")
    private Set<ODataId> pcieDevices = new LinkedHashSet<>();
    @JsonProperty("PCIeFunctions")
    private Set<ODataId> pcieFunctions = new LinkedHashSet<>();
    @JsonProperty("Boot")
    private BootObject boot = new BootObject();
    @JsonProperty("NetworkInterfaces")
    private ODataId networkInterfaces;
    @JsonSetter(value = "TrustedModules", nulls = AS_EMPTY)
    @AsUnassigned({WHEN_NULL, WHEN_EMPTY_COLLECTION})
    private Ref<List<TrustedModuleObject>> trustedModules = unassigned();
    @JsonSetter(value = "HostingRoles", nulls = AS_EMPTY)
    @AsUnassigned({WHEN_NULL, WHEN_EMPTY_COLLECTION})
    private Ref<List<HostingRole>> hostingRoles = unassigned();
    @JsonProperty("Oem")
    private Oem oem = new Oem();
    @JsonProperty("Actions")
    private Actions actions = new Actions();
    @JsonProperty("Links")
    private Links links = new Links();
    @JsonProperty("HostedServices")
    private HostedServices hostedServices = new HostedServices();

    public Ref<UUID> getUuid() {
        return uuid;
    }

    public Ref<String> getManufacturer() {
        return manufacturer;
    }

    public Ref<String> getModel() {
        return model;
    }

    public Ref<String> getSerialNumber() {
        return serialNumber;
    }

    public Ref<SystemType> getSystemType() {
        return systemType;
    }

    public Ref<String> getAssetTag() {
        return assetTag;
    }

    public Ref<String> getBiosVersion() {
        return biosVersion;
    }

    public Ref<String> getSku() {
        return sku;
    }

    public Ref<String> getHostName() {
        return hostName;
    }

    public Ref<IndicatorLed> getIndicatorLed() {
        return indicatorLed;
    }

    public Ref<Status> getStatus() {
        return status;
    }

    public Ref<PowerState> getPowerState() {
        return powerState;
    }

    public Ref<String> getPartNumber() {
        return partNumber;
    }

    public BootObject getBootObject() {
        return boot;
    }

    public ProcessorSummaryObject getProcessorSummary() {
        return processorSummary;
    }

    public MemorySummaryObject getMemorySummary() {
        return memorySummary;
    }

    public Ref<List<String>> getPcieConnectionId() {
        return oem.rackScaleOem.pcieConnectionId;
    }

    public Ref<Integer> getMemorySockets() {
        return oem.rackScaleOem.memorySockets;
    }

    public Ref<Integer> getProcessorSockets() {
        return oem.rackScaleOem.processorSockets;
    }

    public Ref<List<RedfishComputerSystem.TrustedModule>> getTrustedModules() {
        return (Ref) trustedModules;
    }

    public Ref<List<HostingRole>> getHostingRoles() {
        return hostingRoles;
    }

    public Ref<Boolean> getUserModeEnabled() {
        return oem.rackScaleOem.userModeEnabled;
    }

    public Ref<Boolean> getTrustedExecutionTechnologyEnabled() {
        return oem.rackScaleOem.trustedExecutionTechnologyEnabled;
    }

    public boolean isBasic() {
        return BASIC.equals(oem.rackScaleOem.discoveryState);
    }

    public Ref<LinkedHashSet<ResetType>> getAllowableResetTypes() {
        return actions.reset.allowableValues;
    }

    public Ref<LinkedHashSet<InterfaceType>> getAllowableInterfaceTypes() {
        return actions.oem.changeTpmState.allowableValues;
    }

    public Ref<LinkedHashSet<RedfishComputerSystem.Device>> getPciDevices() {
        return (Ref) oem.rackScaleOem.pciDevices;
    }

    @LinkName("storage")
    public Iterable<ResourceSupplier> getStorages() throws WebClientRequestException {
        return processMembersListResource(storages);
    }

    @LinkName("endpoints")
    public Iterable<ResourceSupplier> getEndpoints() throws WebClientRequestException {
        return toSuppliers(links.endpoints);
    }

    @LinkName("processors")
    public Iterable<ResourceSupplier> getProcessors() throws WebClientRequestException {
        return processMembersListResource(processors);
    }

    @LinkName("ethernetInterfaces")
    public Iterable<ResourceSupplier> getEthernetInterfaces() throws WebClientRequestException {
        return processMembersListResource(ethernetInterfaces);
    }

    @LinkName("memoryModules")
    public Iterable<ResourceSupplier> getMemoryModules() throws WebClientRequestException {
        return processMembersListResource(memory);
    }

    @LinkName("simpleStorages")
    public Iterable<ResourceSupplier> getSimpleStorages() throws WebClientRequestException {
        return processMembersListResource(simpleStorages);
    }

    @LinkName("pcieDevices")
    public Iterable<ResourceSupplier> getPcieDevices() throws WebClientRequestException {
        return toSuppliers(pcieDevices);
    }

    @LinkName("pcieFunctions")
    public Iterable<ResourceSupplier> getPcieFunctions() throws WebClientRequestException {
        return toSuppliers(pcieFunctions);
    }

    @LinkName("adapters")
    public Iterable<ResourceSupplier> getAdapters() throws WebClientRequestException {
        return processMembersListResource(oem.rackScaleOem.adapters);
    }

    @LinkName("networkInterfaces")
    public Iterable<ResourceSupplier> getNetworkInterfaces() throws WebClientRequestException {
        return processMembersListResource(networkInterfaces);
    }

    @LinkName("computerSystemMetrics")
    public ResourceSupplier getComputerSystemMetrics() throws WebClientRequestException {
        if (oem.rackScaleOem.computerSystemMetrics == null) {
            return null;
        }

        return toSupplier(oem.rackScaleOem.computerSystemMetrics);
    }

    @LinkName("storageServices")
    public Iterable<ResourceSupplier> getStorageServices() throws WebClientRequestException {
        return toSuppliers(hostedServices.storageServices);
    }

    @OemType(TOP_LEVEL_OEM)
    public class Oem extends RedfishOem {
        @JsonProperty("Intel_RackScale")
        private RackScaleOem rackScaleOem = new RackScaleOem();

        public class RackScaleOem {
            @JsonProperty("DiscoveryState")
            private DiscoveryState discoveryState;

            @JsonSetter(value = "ProcessorSockets", nulls = AS_EMPTY)
            @AsUnassigned(WHEN_NULL)
            private Ref<Integer> processorSockets = unassigned();

            @JsonSetter(value = "MemorySockets", nulls = AS_EMPTY)
            @AsUnassigned(WHEN_NULL)
            private Ref<Integer> memorySockets = unassigned();

            @JsonSetter(value = "PciDevices", nulls = AS_EMPTY)
            @AsUnassigned({WHEN_NULL, WHEN_EMPTY_COLLECTION})
            private Ref<LinkedHashSet<ComputerSystemDeviceObject>> pciDevices = unassigned();

            @JsonSetter(value = "PCIeConnectionId", nulls = AS_EMPTY)
            @AsUnassigned({WHEN_NULL, WHEN_EMPTY_COLLECTION})
            private Ref<List<String>> pcieConnectionId = unassigned();

            @JsonProperty("Adapters")
            private ODataId adapters;

            @JsonSetter(value = "UserModeEnabled", nulls = AS_EMPTY)
            private Ref<Boolean> userModeEnabled = unassigned();

            @JsonSetter(value = "TrustedExecutionTechnologyEnabled", nulls = AS_EMPTY)
            private Ref<Boolean> trustedExecutionTechnologyEnabled = unassigned();

            @JsonProperty("Metrics")
            private ODataId computerSystemMetrics;
        }
    }

    public class Actions extends RedfishActions {
        @JsonProperty("#ComputerSystem.Reset")
        private Reset reset = new Reset();

        @JsonProperty("Oem")
        private Oem oem = new Oem();

        public class Reset {
            @JsonSetter(value = "ResetType@Redfish.AllowableValues", nulls = AS_EMPTY)
            @AsUnassigned({WHEN_NULL, WHEN_EMPTY_COLLECTION})
            private Ref<LinkedHashSet<ResetType>> allowableValues = unassigned();
        }

        @OemType(OEM_IN_ACTIONS)
        public class Oem extends RedfishOem {
            @JsonProperty("#Intel.Oem.StartDeepDiscovery")
            private StartDeepDiscovery startDeepDiscovery = new StartDeepDiscovery();

            @JsonProperty("#Intel.Oem.ChangeTPMState")
            private ChangeTpmState changeTpmState = new ChangeTpmState();

            public class StartDeepDiscovery {
            }

            public class ChangeTpmState {
                @JsonSetter(value = "InterfaceType@Redfish.AllowableValues", nulls = AS_EMPTY)
                @AsUnassigned({WHEN_NULL, WHEN_EMPTY_COLLECTION})
                private Ref<LinkedHashSet<InterfaceType>> allowableValues = unassigned();

                public ChangeTpmState() {
                }
            }
        }
    }

    public class Links extends RedfishLinks {
        @JsonProperty("Endpoints")
        private Set<ODataId> endpoints;
    }

    public class HostedServices {
        @JsonProperty("StorageServices")
        private Set<ODataId> storageServices = new HashSet<>();
    }
}

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
import static com.inspur.redfish.common.types.Ref.unassigned;
import static com.inspur.redfish.common.types.annotations.AsUnassigned.Strategy.WHEN_NULL;
import static com.inspur.redfish.common.types.redfish.OdataTypeVersions.VERSION_PATTERN;
import static com.inspur.redfish.common.types.redfish.OemType.Type.OEM_IN_LINKS;
import static com.inspur.redfish.common.types.redfish.OemType.Type.TOP_LEVEL_OEM;
import static com.inspur.redfish.south.resources.UnknownOemsHelper.convertObjectNodeToObject;
import static java.lang.String.format;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.inspur.redfish.common.types.ChassisType;
import com.inspur.redfish.common.types.Id;
import com.inspur.redfish.common.types.IndicatorLed;
import com.inspur.redfish.common.types.PowerState;
import com.inspur.redfish.common.types.Ref;
import com.inspur.redfish.common.types.Status;
import com.inspur.redfish.common.types.annotations.AsUnassigned;
import com.inspur.redfish.common.types.redfish.OemType;
import com.inspur.redfish.http.LinkName;
import com.inspur.redfish.http.OdataTypes;
import com.inspur.redfish.http.WebClientRequestException;
import com.inspur.redfish.south.reader.ResourceSupplier;
import com.inspur.redfish.south.resources.ExternalServiceResource;
import com.inspur.redfish.south.resources.ExternalServiceResourceImpl;
import com.inspur.redfish.south.resources.ODataId;

@OdataTypes({
    "#Chassis" + VERSION_PATTERN + "Chassis"
})
@SuppressWarnings({"checkstyle:MethodCount", "checkstyle:ClassFanOutComplexity"})
public class ChassisResource extends ExternalServiceResourceImpl implements ExternalServiceResource {
    @JsonProperty("ChassisType")
    private ChassisType chassisType;
    @JsonSetter(value = "Manufacturer", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<String> manufacturer = unassigned();
    @JsonSetter(value = "Model", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<String> model = unassigned();
    @JsonSetter(value = "SKU", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<String> sku = unassigned();
    @JsonSetter(value = "SerialNumber", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<String> serialNumber = unassigned();
    @JsonSetter(value = "PartNumber", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<String> partNumber = unassigned();
    @JsonSetter(value = "AssetTag", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<String> assetTag = unassigned();
    @JsonSetter(value = "IndicatorLED", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<IndicatorLed> indicatorLed = unassigned();
    @JsonSetter(value = "PowerState", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<PowerState> powerState = unassigned();
    @JsonProperty("Status")
    private Status status;
    @JsonProperty("Thermal")
    private ODataId thermal;
    @JsonProperty("Power")
    private ODataId power;
    @JsonProperty("Links")
    private Links links = new Links();
    @JsonProperty("Oem")
    private Oem oem = new Oem();

    public ChassisType getChassisType() {
        return chassisType;
    }

    public Ref<String> getManufacturer() {
        return manufacturer;
    }

    public Ref<String> getModel() {
        return model;
    }

    public Ref<String> getSku() {
        return sku;
    }

    public Ref<String> getSerialNumber() {
        return serialNumber;
    }

    public Ref<String> getPartNumber() {
        return partNumber;
    }

    public Ref<String> getAssetTag() {
        return assetTag;
    }

    public Ref<IndicatorLed> getIndicatorLed() {
        return indicatorLed;
    }

    public Ref<PowerState> getPowerState() {
        return powerState;
    }

    public Status getStatus() {
        return status;
    }

    public Ref<String> getLocationId() {
        if (oem == null) {
            return unassigned();
        }

        return Stream
            .of(
                oem
                    .getOfType(RackScaleChassisOem.class)
                    .map(RackScaleChassisOem::getLocation)
                    .map(LocationObject::getId),
                oem
                    .getOfType(RmmDrawerChassisOem.class)
                    .map(RmmDrawerChassisOem::getLocationId)
            )
            .filter(Optional::isPresent)
            .map(Optional::get)
            .filter(Ref::isAssigned)
            .findFirst()
            .orElse(unassigned());
    }

    public String getLocationParentId() {
        String parentId = Optional
            .of(oem)
            .flatMap(o -> o.getOfType(RackScaleChassisOem.class))
            .map(RackScaleChassisOem::getLocation)
            .map(LocationObject::getParentId)
            .orElse(null);

        if (parentId != null) {
            return parentId;
        }

        try {
            ChassisResource parentChassis = (ChassisResource) getContainedBy().get();
            return parentChassis.getLocationId().orElse(null);
        } catch (WebClientRequestException e) {
            return null;
        }
    }

    public RackScaleRackChassisOem getRackChassisAttributes() {
        if (oem == null) {
            return null;
        }

        return oem.getOfType(RackScaleRackChassisOem.class).orElse(null);
    }

    @LinkName("thermal")
    public ResourceSupplier getThermal() throws WebClientRequestException {
        if (thermal == null) {
            return null;
        }
        return toSupplier(thermal);
    }

    @LinkName("power")
    public ResourceSupplier getPower() {
        if (power == null) {
            return null;
        }

        return toSupplier(power);
    }

    @LinkName("containsChassis")
    public Iterable<ResourceSupplier> getContained() throws WebClientRequestException {
        return toSuppliers(links.contains);
    }

    public ResourceSupplier getContainedBy() throws WebClientRequestException {
        return toSupplier(links.containedBy != null
            ? links.containedBy
            : links.oem.rackScaleOem.containedBy
        );
    }

    @LinkName("computerSystems")
    public Iterable<ResourceSupplier> getComputerSystems() throws WebClientRequestException {
        return toSuppliers(links.computerSystems);
    }

    @LinkName("containsSwitches")
    public Iterable<ResourceSupplier> getSwitches() throws WebClientRequestException {
        return toSuppliers(links.oem.rackScaleOem.switches);
    }

    @LinkName("poweredBy")
    public Iterable<ResourceSupplier> getPoweredBy() throws WebClientRequestException {
        return toSuppliers(links.poweredBy);
    }

    @LinkName("cooledBy")
    public Iterable<ResourceSupplier> getCooledBy() throws WebClientRequestException {
        return toSuppliers(links.cooledBy);
    }

    @LinkName("drives")
    public Iterable<ResourceSupplier> getDrives() throws WebClientRequestException {
        return toSuppliers(links.drives);
    }

    @LinkName("storage")
    public Iterable<ResourceSupplier> getStorage() throws WebClientRequestException {
        return toSuppliers(links.storage);
    }

    @Override
    public Id getGlobalId(Id externalServiceId) {
        switch (getChassisType()) {
            case DRAWER:
                String globalIdString = getChassisType().getValue()
                    .concat(getLocationId().map(lid -> format("-LID-%s", lid)).orElse(""))
                    .concat(format("-PID-%s", getLocationParentId()));
                return Id.fromString(Base64.getEncoder().withoutPadding().encodeToString(globalIdString.getBytes()));
            default:
                return super.getGlobalId(externalServiceId);
        }
    }

    public class Links extends RedfishLinks {
        @JsonProperty("Contains")
        private List<ODataId> contains;

        @JsonProperty("ContainedBy")
        private ODataId containedBy;

        @JsonProperty("ComputerSystems")
        private List<ODataId> computerSystems;

        @JsonProperty("ManagedBy")
        private List<ODataId> managedBy;

        @JsonProperty("ManagersInChassis")
        private List<ODataId> managersInChassis;

        @JsonProperty("PoweredBy")
        private List<ODataId> poweredBy;

        @JsonProperty("CooledBy")
        private List<ODataId> cooledBy;

        @JsonProperty("Drives")
        private List<ODataId> drives;

        @JsonProperty("Storage")
        private List<ODataId> storage;

        @JsonProperty("Oem")
        private Oem oem = new Oem();

        @OemType(OEM_IN_LINKS)
        public class Oem extends RedfishOem {
            @JsonProperty("Intel_RackScale")
            private RackScaleOem rackScaleOem = new RackScaleOem();

            public class RackScaleOem {
                @JsonProperty("Switches")
                private List<ODataId> switches = new ArrayList<>();

                @JsonProperty("ContainedBy")
                private ODataId containedBy;
            }
        }
    }

    @OemType(TOP_LEVEL_OEM)
    public class Oem extends RedfishOem {
        @JsonIgnore
        private Map<String, OemVendor> vendors = new HashMap<>();

        @Override
        public boolean handleKnownVendor(String oemName, ObjectNode oemValue) throws JsonProcessingException {
            if (oemName.equals("Intel_RackScale")) {
                OemVendor oemVendor = convertObjectNodeToObject(oemValue, OemVendor.class);
                vendors.put(oemName, oemVendor);
                return true;
            }

            return false;
        }

        public <T extends OemVendor> Optional<T> getOfType(Class<T> oemClass) {
            return vendors.values()
                .stream()
                .filter(oemClass::isInstance)
                .map(oemClass::cast)
                .findFirst();
        }
    }
}

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

import static com.inspur.redfish.common.types.redfish.OdataTypeVersions.VERSION_PATTERN;
import static com.inspur.redfish.common.types.redfish.OemType.Type.TOP_LEVEL_OEM;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.inspur.redfish.common.types.PciePortType;
import com.inspur.redfish.common.types.Protocol;
import com.inspur.redfish.common.types.Status;
import com.inspur.redfish.common.types.actions.ResetType;
import com.inspur.redfish.common.types.redfish.OemType;
import com.inspur.redfish.http.LinkName;
import com.inspur.redfish.http.OdataTypes;
import com.inspur.redfish.http.WebClientRequestException;
import com.inspur.redfish.south.reader.ResourceSupplier;
import com.inspur.redfish.south.resources.ExternalServiceResource;
import com.inspur.redfish.south.resources.ExternalServiceResourceImpl;
import com.inspur.redfish.south.resources.ODataId;

@OdataTypes({
    "#Port" + VERSION_PATTERN + "Port"
})
public class PortResource extends ExternalServiceResourceImpl implements ExternalServiceResource {
    @JsonProperty("Status")
    private Status status;
    @JsonProperty("PortId")
    private String portId;
    @JsonProperty("PortType")
    private PciePortType portType;
    @JsonProperty("PortProtocol")
    private Protocol portProtocol;
    @JsonProperty("CurrentSpeedGbps")
    private Long currentSpeedGbps;
    @JsonProperty("Width")
    private Long width;
    @JsonProperty("MaxSpeedGbps")
    private Long maxSpeedGbps;
    @JsonProperty("Oem")
    private Oem oem = new Oem();
    @JsonProperty("Actions")
    private Actions actions = new Actions();
    @JsonProperty("Links")
    private Links links = new Links();

    public Status getStatus() {
        return status;
    }

    public String getPortId() {
        return portId;
    }

    public PciePortType getPortType() {
        return portType;
    }

    public Protocol getPortProtocol() {
        return portProtocol;
    }

    public Long getCurrentSpeedGbps() {
        return currentSpeedGbps;
    }

    public Long getWidth() {
        return width;
    }

    public Long getMaxSpeedGbps() {
        return maxSpeedGbps;
    }

    public Set<String> getPcieConnectionIds() {
        return oem.rackScaleOem.pcieConnectionId;
    }

    public Set<ResetType> getAllowableResetTypes() {
        return actions.reset.allowableValues;
    }

    @LinkName("portMetrics")
    public ResourceSupplier getMetrics() throws WebClientRequestException {
        if (oem.rackScaleOem.metrics == null) {
            return null;
        }
        return toSupplier(oem.rackScaleOem.metrics);
    }

    public class Links extends RedfishLinks {
        private Set<ODataId> associatedEndpoints = new HashSet<>();
        private Set<ODataId> connectedSwitches = new HashSet<>();
        private Set<ODataId> connectedSwitchPorts = new HashSet<>();
    }

    public class Actions extends RedfishActions {
        @JsonProperty("#Port.Reset")
        private Reset reset = new Reset();

        @JsonPropertyOrder({"target", "allowableValues"})
        public class Reset {
            @JsonProperty("target")
            private String target;
            @JsonProperty("ResetType@Redfish.AllowableValues")
            private Set<ResetType> allowableValues;
        }
    }

    @OemType(TOP_LEVEL_OEM)
    public class Oem extends RedfishOem {
        @JsonProperty("Intel_RackScale")
        private RackScaleOem rackScaleOem = new RackScaleOem();

        public class RackScaleOem {
            @JsonProperty("PCIeConnectionId")
            private Set<String> pcieConnectionId;
            @JsonProperty("Metrics")
            private ODataId metrics;
        }
    }
}

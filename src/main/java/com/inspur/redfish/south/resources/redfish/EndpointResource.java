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

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.inspur.redfish.common.types.redfish.OdataTypeVersions.VERSION_PATTERN;
import static com.inspur.redfish.common.types.redfish.OemType.Type.OEM_IN_LINKS;
import static com.inspur.redfish.common.types.redfish.OemType.Type.TOP_LEVEL_OEM;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inspur.redfish.common.types.Protocol;
import com.inspur.redfish.common.types.Status;
import com.inspur.redfish.common.types.redfish.OemType;
import com.inspur.redfish.http.LinkName;
import com.inspur.redfish.http.OdataTypes;
import com.inspur.redfish.http.WebClientRequestException;
import com.inspur.redfish.south.reader.ResourceSupplier;
import com.inspur.redfish.south.resources.ExternalServiceResource;
import com.inspur.redfish.south.resources.ExternalServiceResourceImpl;
import com.inspur.redfish.south.resources.ExternalServiceResourceImpl.RedfishLinks;
import com.inspur.redfish.south.resources.ExternalServiceResourceImpl.RedfishOem;
import com.inspur.redfish.south.resources.ODataId;

@OdataTypes({
    "#Endpoint" + VERSION_PATTERN + "Endpoint"
})
public class EndpointResource extends ExternalServiceResourceImpl implements ExternalServiceResource {
    @JsonProperty("EndpointProtocol")
    private Protocol protocol;

    @JsonProperty("Status")
    private Status status;

    @JsonProperty("ConnectedEntities")
    private List<ConnectedEntityResource> connectedEntities = new ArrayList<>();

    @JsonProperty("Identifiers")
    private Set<IdentifierObject> identifiers;

    @JsonProperty("IPTransportDetails")
    private List<IpTransportDetailsResource> transports = new ArrayList<>();

    @JsonProperty("Redundancy")
    private Set<RedundancyItem> redundancies = new LinkedHashSet<>();

    @JsonProperty("PciId")
    private PciIdResource pciId;

    @JsonProperty("HostReservationMemoryBytes")
    private Integer hostReservationMemoryBytes;

    @JsonProperty("Links")
    private Links links = new Links();

    @JsonProperty("Oem")
    private Oem oem = new Oem();

    public Protocol getProtocol() {
        return protocol;
    }

    public Integer getHostReservationMemoryBytes() {
        return hostReservationMemoryBytes;
    }

    public PciIdResource getPciId() {
        return pciId;
    }

    public Set<IdentifierObject> getIdentifiers() {
        return identifiers;
    }

    public AuthenticationObject getAuthentication() {
        return oem.rackScaleOem.authentication;
    }

    public Status getStatus() {
        return status;
    }

    @LinkName("connectedEntityInEndpoint")
    public Iterable<ResourceSupplier> getConnectedEntities() throws WebClientRequestException {
        return toSuppliersFromEmbeddableResourceElement(connectedEntities, "ConnectedEntities");
    }

    public List<ConnectedEntityResource> getConnectedEntityResourceList() {
        return connectedEntities;
    }

    @LinkName("transportInEndpoint")
    public Iterable<ResourceSupplier> getTransports() throws WebClientRequestException {
        return toSuppliersFromEmbeddableResourceElement(transports, "Transports");
    }

    public List<IpTransportDetailsResource> getTransportResourceList() {
        return transports;
    }

    @LinkName("redundancy")
    public Iterable<ResourceSupplier> getRedundancies() throws WebClientRequestException {
        return toSuppliersFromResources(redundancies);
    }

    @LinkName("portsInEndpoint")
    public Iterable<ResourceSupplier> getPorts() throws WebClientRequestException {
        return toSuppliers(links.ports);
    }

    @LinkName("ethernetInterfaceInEndpoint")
    public Iterable<ResourceSupplier> getInterfaces() throws WebClientRequestException {
        return toSuppliers(links.oem.rackScaleOem.interfaces);
    }

    public class Links extends RedfishLinks {
        @JsonProperty("Ports")
        private Set<ODataId> ports = new HashSet<>();

        @JsonProperty("Oem")
        private Oem oem = new Oem();

        @OemType(OEM_IN_LINKS)
        public final class Oem extends RedfishOem {
            @JsonProperty("Intel_RackScale")
            private RackScaleOem rackScaleOem = new RackScaleOem();

            public RackScaleOem getRackScaleOem() {
                return rackScaleOem;
            }

            @JsonInclude(NON_NULL)
            public final class RackScaleOem {
                @JsonProperty("Interfaces")
                private Set<ODataId> interfaces = new HashSet<>();
            }
        }
    }

    @OemType(TOP_LEVEL_OEM)
    public class Oem extends RedfishOem {
        @JsonProperty("Intel_RackScale")
        private RackScaleOem rackScaleOem = new RackScaleOem();

        public class RackScaleOem {
            @JsonProperty("Authentication")
            private AuthenticationObject authentication = new AuthenticationObject();
        }
    }
}

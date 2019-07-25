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

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inspur.redfish.common.types.ReplicaRole;
import com.inspur.redfish.common.types.ReplicaType;
import com.inspur.redfish.http.LinkName;
import com.inspur.redfish.http.OdataTypes;
import com.inspur.redfish.http.WebClient;
import com.inspur.redfish.south.reader.ResourceLinks;
import com.inspur.redfish.south.reader.ResourceSupplier;
import com.inspur.redfish.south.resources.ExternalServiceResource;
import com.inspur.redfish.south.resources.ODataId;

@OdataTypes({
    "StorageReplicaInfo\\.ReplicaInfo"
})
public class ReplicaInfoResource implements ExternalServiceResource {
    @JsonIgnore
    private WebClient webClient;

    @JsonIgnore
    private URI uri;

    @JsonProperty("ReplicaType")
    private ReplicaType replicaType;
    @JsonProperty("ReplicaRole")
    private ReplicaRole replicaRole;
    @JsonProperty("Replica")
    private ODataId replica;

    public ReplicaType getReplicaType() {
        return replicaType;
    }

    public ReplicaRole getReplicaRole() {
        return replicaRole;
    }

    @LinkName("replica")
    public ResourceSupplier getReplica() {
        if (replica == null) {
            return null;
        }
        return toSupplier(webClient, replica);
    }

    @Override
    public ResourceLinks getLinks() {
        return new ResourceLinks(this);
    }

    @Override
    public URI getUri() {
        return uri;
    }

    @Override
    public void setUri(URI uri) {
        this.uri = uri;
    }

    @Override
    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
}

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

import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inspur.redfish.common.types.Status;
import com.inspur.redfish.http.OdataTypes;
import com.inspur.redfish.http.ReferenceableMember;
import com.inspur.redfish.http.WebClient;
import com.inspur.redfish.south.reader.ResourceLinks;
import com.inspur.redfish.south.resources.ExternalServiceResource;
import com.inspur.redfish.south.resources.ODataId;

@OdataTypes({"Redundancy"})
public class RedundancyItem implements ExternalServiceResource, ReferenceableMember {
    private WebClient webClient;
    @JsonProperty("@odata.id")
    private String oDataId;
    @JsonProperty("MemberId")
    private String memberId;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("RedundancyEnabled")
    private Boolean redundancyEnabled;
    @JsonProperty("RedundancySet")
    private List<ODataId> redundancySet;
    @JsonProperty("Mode")
    private String mode;
    @JsonProperty("Status")
    private Status status;
    @JsonProperty("MinNumNeeded")
    private Integer minNumNeeded;
    @JsonProperty("MaxNumSupported")
    private Integer maxNumSupported;

    @Override
    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    @Override
    public ResourceLinks getLinks() {
        return new ResourceLinks(this);
    }

    public Boolean getRedundancyEnabled() {
        return redundancyEnabled;
    }

    public String getMode() {
        return mode;
    }

    public Status getStatus() {
        return status;
    }

    public Integer getMinNumNeeded() {
        return minNumNeeded;
    }

    public Integer getMaxNumSupported() {
        return maxNumSupported;
    }

    @Override
    public URI getUri() {
        return URI.create(oDataId);
    }

    @Override
    public void setUri(URI uri) {

    }

    @Override
    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
}

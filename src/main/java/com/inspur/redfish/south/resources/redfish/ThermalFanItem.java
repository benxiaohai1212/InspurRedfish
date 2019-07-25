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
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inspur.redfish.common.types.PhysicalContext;
import com.inspur.redfish.common.types.Status;
import com.inspur.redfish.common.types.redfish.OdataTypeVersions;
import com.inspur.redfish.http.LinkName;
import com.inspur.redfish.http.OdataTypes;
import com.inspur.redfish.http.ReferenceableMember;
import com.inspur.redfish.http.WebClient;
import com.inspur.redfish.http.WebClientRequestException;
import com.inspur.redfish.south.reader.ResourceLinks;
import com.inspur.redfish.south.reader.ResourceSupplier;
import com.inspur.redfish.south.resources.ExternalServiceResource;
import com.inspur.redfish.south.resources.ODataId;

@OdataTypes({"ThermalResource\\.Fan",
    "#Thermal" + OdataTypeVersions.VERSION_PATTERN + "Fan"})
@SuppressWarnings({"checkstyle:MethodCount"})
public class ThermalFanItem implements ExternalServiceResource, ReferenceableMember {
    private WebClient webClient;
    @JsonProperty("@odata.id")
    private String oDataId;
    @JsonProperty("MemberId")
    private String memberId;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("PhysicalContext")
    private PhysicalContext physicalContext;
    @JsonProperty("Status")
    private Status status;
    @JsonProperty("Reading")
    private Integer reading;
    @JsonProperty("ReadingUnits")
    private String readingUnits;
    @JsonProperty("UpperThresholdNonCritical")
    private Integer upperThresholdNonCritical;
    @JsonProperty("UpperThresholdCritical")
    private Integer upperThresholdCritical;
    @JsonProperty("UpperThresholdFatal")
    private Integer upperThresholdFatal;
    @JsonProperty("LowerThresholdNonCritical")
    private Integer lowerThresholdNonCritical;
    @JsonProperty("LowerThresholdCritical")
    private Integer lowerThresholdCritical;
    @JsonProperty("LowerThresholdFatal")
    private Integer lowerThresholdFatal;
    @JsonProperty("MinReadingRange")
    private Integer minReadingRange;
    @JsonProperty("MaxReadingRange")
    private Integer maxReadingRange;
    @JsonProperty("RelatedItem")
    private List<ODataId> relatedItems = new ArrayList<>();
    @JsonProperty("Redundancy")
    private List<ODataId> redundancy = new ArrayList<>();

    @Override
    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public PhysicalContext getPhysicalContext() {
        return physicalContext;
    }

    public Status getStatus() {
        return status;
    }

    public Integer getReading() {
        return reading;
    }

    public String getReadingUnits() {
        return readingUnits;
    }

    public Integer getUpperThresholdNonCritical() {
        return upperThresholdNonCritical;
    }

    public Integer getUpperThresholdCritical() {
        return upperThresholdCritical;
    }

    public Integer getUpperThresholdFatal() {
        return upperThresholdFatal;
    }

    public Integer getLowerThresholdNonCritical() {
        return lowerThresholdNonCritical;
    }

    public Integer getLowerThresholdCritical() {
        return lowerThresholdCritical;
    }

    public Integer getLowerThresholdFatal() {
        return lowerThresholdFatal;
    }

    public Integer getMinReadingRange() {
        return minReadingRange;
    }

    public Integer getMaxReadingRange() {
        return maxReadingRange;
    }

    @LinkName("relatedItems")
    public Iterable<ResourceSupplier> relatedItems() throws WebClientRequestException {
        return toSuppliers(webClient, relatedItems);
    }

    @LinkName("fanRedundancy")
    public Iterable<ResourceSupplier> redundancy() throws WebClientRequestException {
        return toSuppliers(webClient, redundancy);
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

    public ResourceLinks getLinks() {
        return new ResourceLinks(this);
    }
}

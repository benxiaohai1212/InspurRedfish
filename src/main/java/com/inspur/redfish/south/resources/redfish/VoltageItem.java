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

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inspur.redfish.common.types.PhysicalContext;
import com.inspur.redfish.common.types.Status;
import com.inspur.redfish.http.LinkName;
import com.inspur.redfish.http.OdataTypes;
import com.inspur.redfish.http.ReferenceableMember;
import com.inspur.redfish.http.WebClient;
import com.inspur.redfish.http.WebClientRequestException;
import com.inspur.redfish.south.reader.ResourceLinks;
import com.inspur.redfish.south.reader.ResourceSupplier;
import com.inspur.redfish.south.resources.ExternalServiceResource;
import com.inspur.redfish.south.resources.ODataId;

@OdataTypes({"PowerResource\\.Voltage"})
@SuppressWarnings({"checkstyle:MethodCount"})
public class VoltageItem implements ExternalServiceResource, ReferenceableMember {
    @JsonIgnore
    private WebClient webClient;

    @JsonProperty("@odata.id")
    private String oDataId;

    @JsonProperty("MemberId")
    private String memberId;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("SensorNumber")
    private Long sensorNumber;

    @JsonProperty("Status")
    private Status status;

    @JsonProperty("ReadingVolts")
    private BigDecimal readingVolts;

    @JsonProperty("UpperThresholdNonCritical")
    private BigDecimal upperThresholdNonCritical;

    @JsonProperty("UpperThresholdCritical")
    private BigDecimal upperThresholdCritical;

    @JsonProperty("UpperThresholdFatal")
    private BigDecimal upperThresholdFatal;

    @JsonProperty("LowerThresholdNonCritical")
    private BigDecimal lowerThresholdNonCritical;

    @JsonProperty("LowerThresholdCritical")
    private BigDecimal lowerThresholdCritical;

    @JsonProperty("LowerThresholdFatal")
    private BigDecimal lowerThresholdFatal;

    @JsonProperty("MinReadingRange")
    private BigDecimal minReadingRange;

    @JsonProperty("MaxReadingRange")
    private BigDecimal maxReadingRange;

    @JsonProperty("PhysicalContext")
    private PhysicalContext physicalContext;

    @JsonProperty("RelatedItem")
    private List<ODataId> relatedItems = new ArrayList<>();

    @Override
    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public Long getSensorNumber() {
        return sensorNumber;
    }

    public Status getStatus() {
        return status;
    }

    public BigDecimal getReadingVolts() {
        return readingVolts;
    }

    public BigDecimal getUpperThresholdNonCritical() {
        return upperThresholdNonCritical;
    }

    public BigDecimal getUpperThresholdCritical() {
        return upperThresholdCritical;
    }

    public BigDecimal getUpperThresholdFatal() {
        return upperThresholdFatal;
    }

    public BigDecimal getLowerThresholdNonCritical() {
        return lowerThresholdNonCritical;
    }

    public BigDecimal getLowerThresholdCritical() {
        return lowerThresholdCritical;
    }

    public BigDecimal getLowerThresholdFatal() {
        return lowerThresholdFatal;
    }

    public BigDecimal getMinReadingRange() {
        return minReadingRange;
    }

    public BigDecimal getMaxReadingRange() {
        return maxReadingRange;
    }

    public PhysicalContext getPhysicalContext() {
        return physicalContext;
    }

    @LinkName("relatedItems")
    public Iterable<ResourceSupplier> relatedItems() throws WebClientRequestException {
        return toSuppliers(webClient, relatedItems);
    }

    @Override
    public ResourceLinks getLinks() {
        return new ResourceLinks(this);
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

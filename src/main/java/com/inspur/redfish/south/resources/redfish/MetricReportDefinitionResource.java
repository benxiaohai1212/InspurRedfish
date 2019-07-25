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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inspur.redfish.common.types.MetricReportActionType;
import com.inspur.redfish.common.types.MetricReportType;
import com.inspur.redfish.common.types.Status;
import com.inspur.redfish.common.types.TransmitFormat;
import com.inspur.redfish.http.LinkName;
import com.inspur.redfish.http.OdataTypes;
import com.inspur.redfish.http.WebClientRequestException;
import com.inspur.redfish.south.reader.ResourceSupplier;
import com.inspur.redfish.south.resources.ExternalServiceResource;
import com.inspur.redfish.south.resources.ExternalServiceResourceImpl;

@OdataTypes({
    "#MetricReportDefinition" + VERSION_PATTERN + "MetricReportDefinition"
})
public class MetricReportDefinitionResource extends ExternalServiceResourceImpl implements ExternalServiceResource {
    @JsonProperty("MetricReportType")
    private MetricReportType metricReportType;
    @JsonProperty("MetricReportDestination")
    private String metricReportDestination;
    @JsonProperty("MetricReportActions")
    private List<MetricReportActionType> metricReportActions = new ArrayList<>();
    @JsonProperty("PollingIntervalMilliSeconds")
    private BigDecimal pollingIntervalMilliSeconds;
    @JsonProperty("ReportIntervalMilliSeconds")
    private BigDecimal reportIntervalMilliSeconds;
    @JsonProperty("TransmitFormat")
    private TransmitFormat transmitFormat;
    @JsonProperty("Status")
    private Status status;
    @JsonProperty("Links")
    private Links links = new Links();

    public MetricReportType getMetricReportType() {
        return metricReportType;
    }

    public String getMetricReportDestination() {
        return metricReportDestination;
    }

    public List<MetricReportActionType> getMetricReportActions() {
        return metricReportActions;
    }

    public BigDecimal getPollingIntervalMilliSeconds() {
        return pollingIntervalMilliSeconds;
    }

    public BigDecimal getReportIntervalMilliSeconds() {
        return reportIntervalMilliSeconds;
    }

    public TransmitFormat getTransmitFormat() {
        return transmitFormat;
    }

    public Status getStatus() {
        return status;
    }

    @LinkName("metricsInMetricReportDefinition")
    public Iterable<ResourceSupplier> getMetrics() throws WebClientRequestException {
        return toSuppliersFromEmbeddableResourceElement(links.metrics, "Metrics");
    }

    public class Links extends RedfishLinks {
        @JsonProperty("Metrics")
        private List<MetricItemResource> metrics = new ArrayList<>();
    }
}

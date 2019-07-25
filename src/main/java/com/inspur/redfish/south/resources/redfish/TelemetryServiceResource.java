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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inspur.redfish.common.types.redfish.OdataTypeVersions;
import com.inspur.redfish.http.LinkName;
import com.inspur.redfish.http.OdataTypes;
import com.inspur.redfish.http.WebClientRequestException;
import com.inspur.redfish.south.reader.ResourceSupplier;
import com.inspur.redfish.south.resources.ExternalServiceResource;
import com.inspur.redfish.south.resources.ExternalServiceResourceImpl;
import com.inspur.redfish.south.resources.ODataId;

@OdataTypes({
    "#TelemetryService" + OdataTypeVersions.VERSION_PATTERN + "TelemetryService"
})
public class TelemetryServiceResource extends ExternalServiceResourceImpl implements ExternalServiceResource {
    @JsonProperty("MetricDefinitions")
    private ODataId metricDefinitions;

    @JsonProperty("MetricReportDefinitions")
    private ODataId metricReportDefinitions;

    @LinkName("metricDefinition")
    public Iterable<ResourceSupplier> getMetricDefinitions() throws WebClientRequestException {
        return processMembersListResource(metricDefinitions);
    }

    @LinkName("metricReportDefinition")
    public Iterable<ResourceSupplier> getMetricReportDefinitions() throws WebClientRequestException {
        return processMembersListResource(metricReportDefinitions);
    }
}

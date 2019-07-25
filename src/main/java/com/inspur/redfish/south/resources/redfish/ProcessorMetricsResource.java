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
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inspur.redfish.common.types.redfish.OdataTypeVersions;
import com.inspur.redfish.http.OdataTypes;
import com.inspur.redfish.south.resources.ExternalServiceResource;
import com.inspur.redfish.south.resources.ExternalServiceResourceImpl;

@OdataTypes({"#ProcessorMetrics" + OdataTypeVersions.VERSION_PATTERN + "ProcessorMetrics"})
public class ProcessorMetricsResource extends ExternalServiceResourceImpl implements ExternalServiceResource {

    @JsonProperty("BandwidthPercent")
    private BigDecimal bandwidthPercent;

    @JsonProperty("AverageFrequencyMHz")
    private Integer averageFrequencyMhz;

    @JsonProperty("ThrottlingCelsius")
    private Integer throttlingCelsius;

    @JsonProperty("TemperatureCelsius")
    private Integer temperatureCelsius;

    @JsonProperty("ConsumedPowerWatt")
    private Integer consumedPowerWatt;

    @JsonProperty("Health")
    private List<String> healthList;

    public BigDecimal getBandwidthPercent() {
        return bandwidthPercent;
    }

    public Integer getAverageFrequencyMhz() {
        return averageFrequencyMhz;
    }

    public Integer getThrottlingCelsius() {
        return throttlingCelsius;
    }

    public Integer getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public Integer getConsumedPowerWatt() {
        return consumedPowerWatt;
    }

    public List<String> getHealthList() {
        return healthList;
    }
}

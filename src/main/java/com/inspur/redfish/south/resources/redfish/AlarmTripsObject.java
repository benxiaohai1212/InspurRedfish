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

public class AlarmTripsObject {
    @JsonProperty("Temperature")
    private Boolean temperature;

    @JsonProperty("SpareBlock")
    private Boolean spareBlock;

    @JsonProperty("UncorrectableECCError")
    private Boolean uncorrectableEccError;

    @JsonProperty("CorrectableECCError")
    private Boolean correctableEccError;

    @JsonProperty("AddressParityError")
    private Boolean addressParityError;

    public Boolean getTemperature() {
        return temperature;
    }

    public Boolean getSpareBlock() {
        return spareBlock;
    }

    public Boolean getUncorrectableEccError() {
        return uncorrectableEccError;
    }

    public Boolean getCorrectableEccError() {
        return correctableEccError;
    }

    public Boolean getAddressParityError() {
        return addressParityError;
    }
}

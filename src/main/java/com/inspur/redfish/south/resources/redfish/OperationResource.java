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
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inspur.redfish.south.resources.ODataId;

public class OperationResource {
    @JsonProperty("OperationName")
    private String operationName;
    @JsonProperty("PercentageComplete")
    private BigDecimal percentageComplete;
    @JsonProperty("AssociatedTask")
    private Set<ODataId> associatedTask = new HashSet<>();

    public String getOperationName() {
        return operationName;
    }

    public BigDecimal getPercentageComplete() {
        return percentageComplete;
    }

    public Set<ODataId> getAssociatedTask() {
        return associatedTask;
    }
}

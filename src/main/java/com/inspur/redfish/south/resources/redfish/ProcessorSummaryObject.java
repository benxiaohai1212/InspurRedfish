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

import static com.fasterxml.jackson.annotation.Nulls.AS_EMPTY;
import static com.inspur.redfish.common.types.Ref.unassigned;
import static com.inspur.redfish.common.types.annotations.AsUnassigned.Strategy.WHEN_NULL;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.inspur.redfish.common.types.Ref;
import com.inspur.redfish.common.types.Status;
import com.inspur.redfish.common.types.annotations.AsUnassigned;

public class ProcessorSummaryObject {
    @JsonSetter(value = "Count", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<Integer> count = unassigned();

    @JsonSetter(value = "Model", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<String> model = unassigned();

    @JsonSetter(value = "Status", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<Status> status = unassigned();

    public Ref<Integer> getCount() {
        return count;
    }

    public Ref<String> getModel() {
        return model;
    }

    public Ref<Status> getStatus() {
        return status;
    }
}

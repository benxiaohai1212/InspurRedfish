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
import com.inspur.redfish.common.types.FpgaType;
import com.inspur.redfish.common.types.HssiConfig;
import com.inspur.redfish.common.types.HssiSideband;
import com.inspur.redfish.common.types.Ref;
import com.inspur.redfish.common.types.annotations.AsUnassigned;

public class FpgaObject {
    @JsonSetter(value = "Type", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<FpgaType> type = unassigned();
    @JsonSetter(value = "BitStreamVersion", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<String> bitStreamVersion = unassigned();
    @JsonSetter(value = "HSSIConfiguration", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<HssiConfig> hssiConfiguration = unassigned();
    @JsonSetter(value = "HSSISideband", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<HssiSideband> hssiSideband = unassigned();
    @JsonSetter(value = "ReconfigurationSlots", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<Integer> reconfigurationSlots = unassigned();

    public Ref<FpgaType> getType() {
        return type;
    }

    public Ref<String> getBitStreamVersion() {
        return bitStreamVersion;
    }

    public Ref<HssiConfig> getHssiConfiguration() {
        return hssiConfiguration;
    }

    public Ref<HssiSideband> getHssiSideband() {
        return hssiSideband;
    }

    public Ref<Integer> getReconfigurationSlots() {
        return reconfigurationSlots;
    }
}

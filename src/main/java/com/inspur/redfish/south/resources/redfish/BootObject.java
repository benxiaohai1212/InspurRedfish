/*
 * Copyright (c) 2015-2018 Intel Corporation
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

import java.util.LinkedHashSet;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.inspur.redfish.common.types.BootSourceMode;
import com.inspur.redfish.common.types.BootSourceState;
import com.inspur.redfish.common.types.BootSourceType;
import com.inspur.redfish.common.types.Ref;
import com.inspur.redfish.common.types.annotations.AsUnassigned;

public class BootObject {
    @JsonSetter(value = "BootSourceOverrideEnabled", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<BootSourceState> bootSourceOverrideEnabled = unassigned();

    @JsonSetter(value = "BootSourceOverrideTarget", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<BootSourceType> bootSourceOverrideTarget = unassigned();

    @JsonProperty("BootSourceOverrideTarget@Redfish.AllowableValues")
    private LinkedHashSet<BootSourceType> bootSourceOverrideTargetAllowableValues;

    @JsonSetter(value = "BootSourceOverrideMode", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<BootSourceMode> bootSourceOverrideMode = unassigned();

    @JsonProperty("BootSourceOverrideMode@Redfish.AllowableValues")
    private LinkedHashSet<BootSourceMode> bootSourceOverrideModeAllowableValues;

    public Ref<BootSourceState> getBootSourceOverrideEnabled() {
        return bootSourceOverrideEnabled;
    }

    public Ref<BootSourceType> getBootSourceOverrideTarget() {
        return bootSourceOverrideTarget;
    }

    public LinkedHashSet<BootSourceType> getBootSourceOverrideTargetAllowableValues() {
        if (bootSourceOverrideTargetAllowableValues == null) {
            return new LinkedHashSet<>();
        }
        return bootSourceOverrideTargetAllowableValues;
    }

    public Ref<BootSourceMode> getBootSourceOverrideMode() {
        return bootSourceOverrideMode;
    }

    public LinkedHashSet<BootSourceMode> getBootSourceOverrideModeAllowableValues() {
        if (bootSourceOverrideModeAllowableValues == null) {
            return new LinkedHashSet<>();
        }
        return bootSourceOverrideModeAllowableValues;
    }
}

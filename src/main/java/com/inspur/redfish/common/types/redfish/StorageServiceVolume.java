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

package com.inspur.redfish.common.types.redfish;

import java.util.List;
import java.util.Set;

import com.inspur.redfish.common.types.ReplicaType;
import com.inspur.redfish.common.types.StorageAccessCapability;
import com.inspur.redfish.common.types.redfish.OdataIdProvider.ODataId;

public interface StorageServiceVolume extends RedfishResource {
    Number getCapacityBytes();
    List<? extends CapacitySource> getCapacitySources();
    Set<StorageAccessCapability> getAccessCapabilities();
    List<? extends ReplicaInfo> getReplicaInfos();

    interface ReplicaInfo {
        ReplicaType getReplicaType();
        ODataId getReplica();
    }

    interface CapacitySource {
        Set<? extends ODataId> getProvidingPools();
    }
}

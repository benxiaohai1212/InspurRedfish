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

package com.inspur.redfish.common.types;

import static com.inspur.redfish.common.utils.Contracts.requires;
import static com.inspur.redfish.common.utils.StringRepresentation.fromMap;
import static java.util.Collections.unmodifiableMap;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.inspur.redfish.common.utils.StringRepresentation;

public class NeighborInfo implements Serializable {
    private static final long serialVersionUID = 4758875171334564789L;
    private static final String SWITCH_ID_NAME = "SwitchId";
    private static final String PORT_ID_NAME = "PortId";
    private static final String CABLE_ID_NAME = "CableId";

    private String switchId;
    private String portId;
    private String cableId;

    public NeighborInfo(Map<String, String> attributesMap) {
        requires(attributesMap != null, "Port attributes must not be null");
        buildFromAttributesMap(attributesMap);
    }

    public NeighborInfo(String attributes) {
        requires(attributes != null, "Port attributes must not be null");
        buildFromAttributesMap(StringRepresentation.toMap(attributes));
    }

    private void buildFromAttributesMap(Map<String, String> attributesMap) {
        switchId = attributesMap.get(SWITCH_ID_NAME);
        portId = attributesMap.get(PORT_ID_NAME);
        cableId = attributesMap.get(CABLE_ID_NAME);
    }

    public Map<String, String> toMap() {
        Map<String, String> attributesMap = new HashMap<>();

        attributesMap.put(SWITCH_ID_NAME, switchId);
        attributesMap.put(PORT_ID_NAME, portId);
        attributesMap.put(CABLE_ID_NAME, cableId);

        return unmodifiableMap(attributesMap);
    }

    @Override
    public String toString() {
        return fromMap(toMap());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NeighborInfo that = (NeighborInfo) o;
        return Objects.equals(toString(), that.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}

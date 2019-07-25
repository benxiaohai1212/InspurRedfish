/*
 * Copyright (c) 2016-2018 Intel Corporation
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

import static java.util.Collections.emptySet;

import java.util.List;
import java.util.Set;

import com.inspur.redfish.common.types.AdministrativeState;
import com.inspur.redfish.common.types.DcbxState;
import com.inspur.redfish.common.types.LinkType;
import com.inspur.redfish.common.types.NeighborInfo;
import com.inspur.redfish.common.types.OperationalState;
import com.inspur.redfish.common.types.PortClass;
import com.inspur.redfish.common.types.PortMode;
import com.inspur.redfish.common.types.PortType;
import com.inspur.redfish.common.types.Status;
import com.inspur.redfish.common.types.net.MacAddress;

@SuppressWarnings({"checkstyle:MethodCount", "checkstyle:ClassFanOutComplexity"})
public interface RedfishEthernetSwitchPort extends RedfishResource {
    @Override
    default String getName() {
        return null;
    }

    default String getPortId() {
        return null;
    }

    default Boolean getFullDuplex() {
        return null;
    }

    default MacAddress getMacAddress() {
        return null;
    }

    default PortClass getPortClass() {
        return null;
    }

    default PortMode getPortMode() {
        return null;
    }

    default PortType getPortType() {
        return null;
    }

    default Status getStatus() {
        return null;
    }

    default LinkType getLinkType() {
        return null;
    }

    default OperationalState getOperationalState() {
        return null;
    }

    default AdministrativeState getAdministrativeState() {
        return null;
    }

    default Integer getLinkSpeedMbps() {
        return null;
    }

    default NeighborInfo getNeighborInfo() {
        return null;
    }

    default MacAddress getNeighborMac() {
        return null;
    }

    default Integer getFrameSize() {
        return null;
    }

    default Boolean getAutosense() {
        return null;
    }

    default DcbxState getDcbxState() {
        return null;
    }

    default Boolean getLldpEnabled() {
        return null;
    }

    default PriorityFlowControl getPriorityFlowControl() {
        return null;
    }

    default Links getLinks() {
        return null;
    }

    interface PriorityFlowControl {
        Boolean getEnabled();
        List<Integer> getEnabledPriorities();
    }

    interface Links {
        default <T extends NavigationProperty> T getPrimaryVlan() {
            return null;
        }

        default <T extends NavigationProperty> T getSwitchContext() {
            return null;
        }

        default <T extends NavigationProperty> T getMemberOfPort() {
            return null;
        }

        default <T extends NavigationProperty> Set<T> getPortMembers() {
            return emptySet();
        }
    }
}

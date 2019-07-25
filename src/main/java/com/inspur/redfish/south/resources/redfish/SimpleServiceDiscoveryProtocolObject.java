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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inspur.redfish.common.types.NotifyIpV6Scope;

public class SimpleServiceDiscoveryProtocolObject {
    @JsonProperty("ProtocolEnabled")
    private Boolean protocolEnabled;

    @JsonProperty("Port")
    private Integer port;

    @JsonProperty("NotifyMulticastIntervalSeconds")
    private Integer notifyMulticastIntervalSeconds;

    @JsonProperty("NotifyTTL")
    private Integer notifyTtl;

    @JsonProperty("NotifyIPv6Scope")
    private NotifyIpV6Scope notifyIpV6Scope;

    public Boolean isProtocolEnabled() {
        return protocolEnabled;
    }

    public Integer getPort() {
        return port;
    }

    public Integer getNotifyMulticastIntervalSeconds() {
        return notifyMulticastIntervalSeconds;
    }

    public Integer getNotifyTtl() {
        return notifyTtl;
    }

    public NotifyIpV6Scope getNotifyIpV6Scope() {
        return notifyIpV6Scope;
    }
}

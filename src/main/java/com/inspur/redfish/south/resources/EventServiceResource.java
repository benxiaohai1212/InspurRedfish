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

package com.inspur.redfish.south.resources;

import static java.util.Collections.emptySet;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inspur.redfish.common.types.redfish.OdataTypeVersions;
import com.inspur.redfish.http.OdataTypes;
import com.inspur.redfish.http.WebClientRequestException;
import com.inspur.redfish.south.reader.ResourceSupplier;

@OdataTypes({
    "#EventService" + OdataTypeVersions.VERSION_PATTERN + "EventService"
})
public class EventServiceResource extends ExternalServiceResourceImpl {
    @JsonProperty("EventTypesForSubscription")
    private Set<String> eventTypesForSubscription;

    @JsonProperty("Subscriptions")
    private ODataId subscriptions;

    public Set<String> getEventTypesForSubscription() {
        if (eventTypesForSubscription == null) {
            return emptySet();
        }
        return eventTypesForSubscription;
    }

    public Iterable<ResourceSupplier> getSubscriptions() throws WebClientRequestException {
        return processMembersListResource(subscriptions);
    }
}

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

import static com.inspur.redfish.common.types.redfish.OdataTypeVersions.VERSION_PATTERN;
import static java.util.EnumSet.noneOf;

import java.net.URI;
import java.util.EnumSet;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inspur.redfish.common.types.events.EventType;
import com.inspur.redfish.http.OdataTypes;

@OdataTypes({
    "#EventService" + VERSION_PATTERN + "EventDestination",
    "#EventDestination" + VERSION_PATTERN + "EventDestination",
    "#EventServiceSubscription" + VERSION_PATTERN + "EventServiceSubscription"
})
public class EventSubscriptionResource extends ExternalServiceResourceImpl implements ExternalServiceResource {
    @JsonProperty("Destination")
    private String destination;

    @JsonProperty("EventTypes")
    private EnumSet<EventType> eventTypes;

    @JsonProperty("Context")
    private String context;

    public URI getDestination() {
        return URI.create(destination);
    }

    public EnumSet<EventType> getEventTypes() {
        if (eventTypes == null) {
            return noneOf(EventType.class);
        }
        return eventTypes;
    }

    public String getContext() {
        return context;
    }
}

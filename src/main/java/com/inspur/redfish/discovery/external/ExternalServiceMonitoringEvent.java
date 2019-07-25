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

package com.inspur.redfish.discovery.external;

import static com.inspur.redfish.discovery.external.ExternalServiceMonitoringEvent.MonitoringState.STARTED;
import static com.inspur.redfish.discovery.external.ExternalServiceMonitoringEvent.MonitoringState.STOPPED;

import java.util.UUID;

import org.springframework.context.ApplicationEvent;

public final class ExternalServiceMonitoringEvent extends ApplicationEvent{
    /** @Fields serialVersionUID: TODO 功能描述  */
	private static final long serialVersionUID = -2799224748075311881L;
	private final UUID uuid;
    private final MonitoringState monitoringState;

    public ExternalServiceMonitoringEvent(Object source,UUID externalServiceUuid, MonitoringState monitoringState) {
    	super(source);
    	this.uuid = externalServiceUuid;
        this.monitoringState = monitoringState;
    }
//    private ExternalServiceMonitoringEvent(UUID externalServiceUuid, MonitoringState monitoringState) {
//        this.uuid = externalServiceUuid;
//        this.monitoringState = monitoringState;
//    }

    public static ExternalServiceMonitoringEvent externalServiceMonitoringStartedEvent(Object source, UUID externalServiceUuid) {
        return new ExternalServiceMonitoringEvent(source, externalServiceUuid, STARTED);
    }

    static ExternalServiceMonitoringEvent externalServiceMonitoringStoppedEvent(Object source, UUID externalServiceUuid) {
        return new ExternalServiceMonitoringEvent(source, externalServiceUuid, STOPPED);
    }

    public UUID getExternalServiceUuid() {
        return uuid;
    }

    public MonitoringState getMonitoringState() {
        return monitoringState;
    }

    public enum MonitoringState {
        STARTED,
        STOPPED
    }
}

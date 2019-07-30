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

package com.inspur.redfish.discovery;

import static com.inspur.redfish.discovery.ExternalServiceMonitoringEvent.MonitoringState.STARTED;
import static com.inspur.redfish.discovery.ExternalServiceMonitoringEvent.MonitoringState.STOPPED;

import org.springframework.context.ApplicationEvent;

import com.inspur.redfish.common.types.discovery.ServiceEndpoint;
/**
 * 这个event暂时未被使用,后续需要时可用.参考Spring中的event使用方式.
 * @author 86135
 *
 */
public final class ExternalServiceMonitoringEvent extends ApplicationEvent{
	private static final long serialVersionUID = -2799224748075311881L;
	private final ServiceEndpoint serviceEndPoint;
    private final MonitoringState monitoringState;

    public ExternalServiceMonitoringEvent(Object source,ServiceEndpoint serviceEndPoint, MonitoringState monitoringState) {
    	super(source);
    	this.serviceEndPoint = serviceEndPoint;
        this.monitoringState = monitoringState;
    }
    
    public static ExternalServiceMonitoringEvent externalServiceMonitoringStartedEvent(Object source, ServiceEndpoint serviceEndPoint) {
        return new ExternalServiceMonitoringEvent(source, serviceEndPoint, STARTED);
    }

    static ExternalServiceMonitoringEvent externalServiceMonitoringStoppedEvent(Object source, ServiceEndpoint serviceEndPoint) {
        return new ExternalServiceMonitoringEvent(source, serviceEndPoint, STOPPED);
    }

    public ServiceEndpoint getExternalServiceUuid() {
        return serviceEndPoint;
    }

    public MonitoringState getMonitoringState() {
        return monitoringState;
    }

    public enum MonitoringState {
        STARTED,
        STOPPED
    }
}

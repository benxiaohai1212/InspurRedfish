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

package com.inspur.redfish.discovery;

import com.inspur.redfish.common.types.discovery.ServiceEndpoint;

/**
 * Default discovery interface for services of known type
 */
public interface ServiceDetectionListener {
    void onServiceDetected(ServiceEndpoint serviceEndpoint);
    void onServiceRemoved(ServiceEndpoint serviceEndpoint);
}
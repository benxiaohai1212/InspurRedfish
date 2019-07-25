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

package com.inspur.redfish.south.reader;

import com.inspur.redfish.http.WebClient;
import com.inspur.redfish.http.WebClientRequestException;
import com.inspur.redfish.south.resources.redfish.ServiceRootResource;

public final class ExternalServiceReader implements AutoCloseable {
    private final WebClient webClient;

    public ExternalServiceReader(WebClient webClient) {
        this.webClient = webClient;
    }

    public ServiceRootResource getServiceRoot() throws WebClientRequestException {
        return (ServiceRootResource) webClient.get(webClient.getBaseUri());
    }

    @Override
    public void close() {
        webClient.close();
    }
}

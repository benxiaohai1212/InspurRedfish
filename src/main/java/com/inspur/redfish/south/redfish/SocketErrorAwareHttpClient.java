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

package com.inspur.redfish.south.redfish;

import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCause;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URI;

import com.inspur.redfish.http.HttpRequest;
import com.inspur.redfish.http.HttpResponse;
import com.inspur.redfish.http.SimpleHttpClient;
import com.inspur.redfish.http.WebClientConnectionException;
import com.inspur.redfish.south.redfish.response.RedfishClientException;
import com.inspur.redfish.south.redfish.response.RedfishResponseBodyImpl;

public class SocketErrorAwareHttpClient implements AutoCloseable {
    private final SimpleHttpClient client;

    public SocketErrorAwareHttpClient(SimpleHttpClient client) {
        this.client = client;
    }

    HttpResponse call(HttpRequest httpRequest) throws RedfishClientException {
        URI path = httpRequest.getRequestUri();
        try {
            return client.call(httpRequest.getHttpMethod(), path, httpRequest.getRequestBody().orElse(null), RedfishResponseBodyImpl.class);
        } catch (Exception e) {
            if (isCausedBySocketException(e)) {
                throw new RedfishClientException(new WebClientConnectionException("Connectivity exception occurred", path, e));
            } else {
                throw new RedfishClientException(e);
            }
        }
    }

    private static boolean isCausedBySocketException(Exception e) {
        return getRootCause(e) instanceof SocketException || getRootCause(e) instanceof SocketTimeoutException;
    }

    @Override
    public void close() {
        this.client.close();
    }
}

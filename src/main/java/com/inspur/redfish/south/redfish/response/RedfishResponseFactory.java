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

package com.inspur.redfish.south.redfish.response;

import static java.lang.String.format;

import java.net.URI;

import org.apache.commons.lang3.NotImplementedException;

import com.inspur.redfish.common.types.net.HttpMethod;
import com.inspur.redfish.common.types.net.HttpStatusCode;
import com.inspur.redfish.common.types.redfish.ExternalServiceError;
import com.inspur.redfish.common.types.redfish.RedfishErrorResponse;
import com.inspur.redfish.common.utils.Contracts;
import com.inspur.redfish.http.HttpRequest;
import com.inspur.redfish.http.HttpResponse;

@SuppressWarnings({"checkstyle:ClassFanOutComplexity"})
public final class RedfishResponseFactory {
    private RedfishResponseFactory() {
    }

    public static RedfishResponse redfishResponseFromHttpResponse(HttpResponse httpResponse, HttpRequest httpRequest) throws RedfishClientException {
    	Contracts.requiresNonNull(httpResponse, "httpResponse");

        if (httpResponse.getStatusCode().isSuccessful()) {
            return new RedfishResponseImpl(httpResponse);
        } else if (httpResponse.getStatusCode().isError()) {
            throw new RedfishClientException(format("Service replied with %s", httpResponse), buildExternalServiceError(httpRequest, httpResponse));
        } else if (httpResponse.getStatusCode().isRedirection()) {
            throw new RedfishClientException(new UnexpectedRedirectionException(httpRequest.getRequestUri(), httpResponse.getLocation().orElse(null)));
        }

        throw new NotImplementedException("Status code is not supported: " + httpResponse.getStatusCode());
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:AnonInnerLength"})
    private static ExternalServiceError buildExternalServiceError(HttpRequest httpRequest, HttpResponse httpResponse) {
        return new ExternalServiceError() {
            @Override
            public Request getRequest() {
                return new Request() {
                    @Override
                    public HttpMethod getHttpMethod() {
                        return httpRequest.getHttpMethod();
                    }

                    @Override
                    public URI getRequestUri() {
                        return httpRequest.getRequestUri();
                    }

                    @Override
                    public Object getRequestBody() {
                        return httpRequest.getRequestBody().orElse(null);
                    }
                };
            }

            @Override
            public Response getResponse() {
                return new Response() {
                    @Override
                    public HttpStatusCode getHttpStatusCode() {
                        return httpResponse.getStatusCode();
                    }

                    @Override
                    public Object getResponseBody() {
                        return httpResponse.getEntity().orElse(null);
                    }
                };
            }

            @Override
            public RedfishErrorResponse getRedfishErrorResponse() {
                Object entity = httpResponse.getEntity().orElse(null);
                if (entity instanceof RedfishErrorResponse) {
                    return (RedfishErrorResponse) entity;
                } else {
                    return () -> new RedfishErrorResponse.Error() {
                        @Override
                        public String getCode() {
                            return null;
                        }

                        @Override
                        public String getMessage() {
                            return null;
                        }
                    };
                }
            }
        };
    }
}

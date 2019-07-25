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

import java.net.URI;
import java.util.List;
import java.util.stream.StreamSupport;

import com.inspur.redfish.common.types.Id;
import com.inspur.redfish.common.types.redfish.OdataIdProvider;
import com.inspur.redfish.common.utils.Contracts;
import com.inspur.redfish.http.IdFromUriGenerator;
import com.inspur.redfish.http.WebClient;
import com.inspur.redfish.http.WebClientRequestException;
import com.inspur.redfish.south.reader.ResourceLinks;
import com.inspur.redfish.south.reader.ResourceSupplier;
import com.inspur.redfish.south.resources.redfish.UnknownOemObject;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public interface ExternalServiceResource {
    /**
     * Gets relative URI of resource.
     */
    URI getUri();

    void setUri(URI uri);

    void setWebClient(WebClient webClient);

    ResourceLinks getLinks();

    default List<UnknownOemObject> getUnknownOems() {
        return emptyList();
    }

    default ExternalServiceResource getByFragment(String uriFragment) {
        throw null;
    }

    default Id getGlobalId(Id externalServiceId) {
        return IdFromUriGenerator.instance().getIdFromUri(getUri(), externalServiceId.getValue());
    }

    default Iterable<ResourceSupplier> toSuppliers(WebClient webClient, Iterable<? extends OdataIdProvider.ODataId> collection)
        throws WebClientRequestException {
        if (collection == null) {
            return emptyList();
        }

        return StreamSupport.stream(collection.spliterator(), false)
            .map(odataid -> toSupplier(webClient, odataid))
            .collect(toList());
    }

    default ResourceSupplier toSupplier(WebClient webClient, OdataIdProvider.ODataId oDataId) {
        if (oDataId == null || oDataId.toUri() == null) {
            return new ResourceSupplier() {
                @Override
                public ExternalServiceResource get() throws WebClientRequestException {
                    throw new WebClientRequestException(
                        "null ODataId", ExternalServiceResource.this.getUri(), new IllegalStateException("null ODataId")
                    );
                }

                @Override
                public URI getUri() {
                    throw new IllegalStateException("URI not available");
                }
            };
        }

        return new ResourceSupplierImpl(webClient, oDataId);
    }

    class ResourceSupplierImpl implements ResourceSupplier {
        private final WebClient webClient;
        private final URI uri;

        private ResourceSupplierImpl(WebClient webClient, OdataIdProvider.ODataId oDataId) {
            this.webClient = webClient;
            this.uri = oDataId.toUri();
        }

        @Override
        public ExternalServiceResource get() throws WebClientRequestException {
            ExternalServiceResource resource = webClient.get(uri);
            if (uri.getFragment() != null) {
                resource = resource.getByFragment(uri.getFragment());
                Contracts.requiresNonNull(resource, () -> new WebClientRequestException(
                    "Requested resource doesn't exist", uri, new IllegalStateException("Requested resource doesn't exist"))
                );
                resource.setWebClient(webClient);
            }
            return resource;
        }

        @Override
        public URI getUri() {
            return uri;
        }
    }
}

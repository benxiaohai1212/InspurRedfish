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

package com.inspur.redfish.discovery.external.restgraph;

import static java.util.Collections.unmodifiableCollection;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

import com.inspur.redfish.south.resources.ExternalServiceResource;
import com.inspur.redfish.south.resources.redfish.ServiceRootResource;

public final class RestGraph {
    private final HashSet<ExternalServiceResource> resources = new HashSet<>();
    private final HashSet<ResourceLink> links = new HashSet<>();
    private final ServiceRootResource serviceRootResource;

    public RestGraph(ServiceRootResource serviceRoot) {
        this.serviceRootResource = serviceRoot;
    }

    public Collection<ResourceLink> getLinks() {
        return unmodifiableCollection(links);
    }

    public Collection<ExternalServiceResource> getResources() {
        return resources.stream().filter(r -> !r.equals(serviceRootResource)).collect(collectingAndThen(toList(), Collections::unmodifiableList));
    }

    public void add(ResourceLink link) {
        resources.add(link.getSource());
        resources.add(link.getTarget());
        links.add(new ResourceLink(link.getSource(), link.getTarget(), link.getLinkName()));
    }

    public void addAll(Iterable<ResourceLink> links) {
        for (ResourceLink link : links) {
            add(link);
        }
    }

    public boolean contains(ResourceLink resourceLink) {
        return links.contains(resourceLink);
    }

    public UUID findServiceUuid() {
        return serviceRootResource.getUuid();
    }
}

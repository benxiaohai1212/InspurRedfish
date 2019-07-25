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


import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import com.inspur.redfish.common.utils.Contracts;
import com.inspur.redfish.http.WebClientRequestException;
import com.inspur.redfish.south.resources.ExternalServiceResource;

public class ResourceLinks {
    private final ExternalServiceResource resource;

    public ResourceLinks(ExternalServiceResource resource) {
        this.resource = resource;
    }

    public Collection<String> getLinkNames() {
        return ResourceLinksCache.getCachedLinkNames(resource.getClass());
    }

    @SuppressWarnings({"unchecked"})
    public Iterable<ResourceSupplier> get(String linkName) throws WebClientRequestException {
    	Contracts.requiresNonNull(linkName, "linkName");

        Method method = ResourceLinksCache.getCachedMethod(resource.getClass(), linkName);
        if (method == null) {
            throw new IllegalArgumentException("No method found for given linkName");
        }

        Object result = invoke(method);
        if (result == null) {
            return emptyList();
        }

        return ResourceLinksCache.isSupplierType(method.getGenericReturnType())
            ? singleton((ResourceSupplier) result)
            : (Iterable<ResourceSupplier>) result;
    }

    private Object invoke(Method method) throws WebClientRequestException {
        Object result;
        try {
            result = method.invoke(resource);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof WebClientRequestException) {
                throw (WebClientRequestException) e.getTargetException();
            }

            throw new RuntimeException(e);
        }
        return result;
    }
}

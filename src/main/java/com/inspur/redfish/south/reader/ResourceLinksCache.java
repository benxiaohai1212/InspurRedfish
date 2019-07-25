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

package com.inspur.redfish.south.reader;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.reflect.TypeToken;
import com.inspur.redfish.http.LinkName;
import com.inspur.redfish.south.resources.ExternalServiceResource;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.google.common.cache.CacheBuilder.newBuilder;

final class ResourceLinksCache {
    private static final TypeToken<ResourceSupplier> SUPPLIER_TYPE_TOKEN = new TypeToken<ResourceSupplier>() {
    };
    private static final int CACHE_SIZE = 100;

    //这是谷歌的一个动态的缓存，可以指定CacheLoader来动态取得数据，支持同步操作，详见43行build方法
    private static final LoadingCache<Class<? extends ExternalServiceResource>, Map<String, Method>> EXTERNAL_SERVICE_RESOURCE_METHODS =
        newBuilder()
            .maximumSize(CACHE_SIZE)
            .build(
                new CacheLoader<Class<? extends ExternalServiceResource>, Map<String, Method>>() {
                    @Override
                    public Map<String, Method> load(Class<? extends ExternalServiceResource> resourceClass) throws Exception {
                        return getValidResourceLinksMethods(resourceClass);
                    }
                });

    private ResourceLinksCache() {
    }


    public static boolean isSupplierType(Type type) {
        return SUPPLIER_TYPE_TOKEN.isSupertypeOf(type);
    }

    static Set<String> getCachedLinkNames(Class<? extends ExternalServiceResource> resourceClass) {
        return EXTERNAL_SERVICE_RESOURCE_METHODS.getUnchecked(resourceClass).keySet();
    }

    static Method getCachedMethod(Class<? extends ExternalServiceResource> resourceClass, String methodName) {
        return EXTERNAL_SERVICE_RESOURCE_METHODS.getUnchecked(resourceClass).get(methodName);
    }

    private static Map<String, Method> getValidResourceLinksMethods(Class<? extends ExternalServiceResource> resourceClass) {
        HashMap<String, Method> methods = new HashMap<>();
        for (Method method : resourceClass.getMethods()) {
            LinkName linkName = method.getAnnotation(LinkName.class);
            if (linkName != null && hasAcceptableReturnType(method)) {
                methods.put(linkName.value(), method);
            }
        }

        return methods;
    }

    private static boolean hasAcceptableReturnType(Method method) {
        if (!Iterable.class.isAssignableFrom(method.getReturnType())) {
            return isSupplierType(method.getGenericReturnType());
        }

        ParameterizedType parameterizedType = (ParameterizedType) TypeToken.of(method.getGenericReturnType()).getType();
        Type iterableParamType = parameterizedType.getActualTypeArguments()[0];

        return isSupplierType(iterableParamType);
    }
}

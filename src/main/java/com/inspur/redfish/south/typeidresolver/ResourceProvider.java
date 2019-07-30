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

package com.inspur.redfish.south.typeidresolver;

import static com.inspur.redfish.south.typeidresolver.OdataTypeMatcher.odataTypePatternMatcher;
import static com.inspur.redfish.south.typeidresolver.OdataTypeMatcher.simpleOdataTypeMatcher;
import static com.inspur.redfish.south.typeidresolver.ResourceResolver.register;
import static java.lang.reflect.Modifier.isAbstract;
import static java.util.stream.StreamSupport.stream;

import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inspur.redfish.south.resources.ExternalServiceResource;
import com.inspur.redfish.south.resources.MembersListResource;
import com.inspur.redfish.south.resources.redfish.ManagerResource;
import com.inspur.redfish.south.resources.redfish.OemVendor;
import com.inspur.redfish.south.resources.redfish.ProcessorResource;
import com.inspur.redfish.utils.ClassUtil;

//@Singleton
//@Startup
public class ResourceProvider {
	private static final Logger logger = LoggerFactory.getLogger(ResourceProvider.class);

    public static void resourceProvider() {
        logger.debug("Registering supported OData types...");
        registerKnownOdataTypes(ExternalServiceResource.class);
        registerKnownOdataTypes(OemVendor.class);
        registerKnownIncorrectOdataTypes();
    }

    private static void registerKnownOdataTypes(Class<?> clazz) {
//        stream(getSubclasses(clazz).spliterator(), false)
    	 stream(ClassUtil.getAllClassByParent(clazz).spliterator(), false)
            .filter(byConcreteClass())
            .forEach(ResourceResolver::registerResource);
    }

    private static Predicate<Class<?>> byConcreteClass() {
        return clazz -> !isAbstract(clazz.getModifiers());
    }

    private static void registerKnownIncorrectOdataTypes() {
        register(odataTypePatternMatcher(".*Processor.+Processor", ProcessorResource.class));
        register(simpleOdataTypeMatcher("#Managers.1.0.0.Manager", ManagerResource.class));
        register(simpleOdataTypeMatcher("#Processors.1.0.0.ProcessorsCollection", MembersListResource.class));
    }
}

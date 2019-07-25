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

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inspur.redfish.http.WebClientBuilder;

//@Dependent
@Component
public class ExternalServiceReaderFactory {
//    @Inject
	@Autowired
    private WebClientBuilder webClientBuilder;

    public ExternalServiceReader createExternalServiceReaderWithCacheAndRetries(URI baseUri) {
        return new ExternalServiceReader(webClientBuilder.newInstance(baseUri)
            .cachable()
            .retryable()
            .build()
        );
    }

    public ExternalServiceReader createExternalServiceReaderWithRetries(URI baseUri) {
        return new ExternalServiceReader(webClientBuilder.newInstance(baseUri)
            .retryable()
            .build());
    }
}

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

package com.inspur.redfish.south.resources.redfish;

import static com.fasterxml.jackson.annotation.Nulls.AS_EMPTY;
import static com.inspur.redfish.common.types.Ref.unassigned;
import static com.inspur.redfish.common.types.annotations.AsUnassigned.Strategy.WHEN_EMPTY_COLLECTION;
import static com.inspur.redfish.common.types.annotations.AsUnassigned.Strategy.WHEN_NULL;
import static com.inspur.redfish.common.types.redfish.OdataTypeVersions.VERSION_PATTERN;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.inspur.redfish.common.types.Ref;
import com.inspur.redfish.common.types.Status;
import com.inspur.redfish.common.types.annotations.AsUnassigned;
import com.inspur.redfish.http.LinkName;
import com.inspur.redfish.http.OdataTypes;
import com.inspur.redfish.http.WebClientRequestException;
import com.inspur.redfish.south.reader.ResourceSupplier;
import com.inspur.redfish.south.resources.ExternalServiceResource;
import com.inspur.redfish.south.resources.ExternalServiceResourceImpl;
import com.inspur.redfish.south.resources.ExternalServiceResourceImpl.RedfishLinks;
import com.inspur.redfish.south.resources.ODataId;

@OdataTypes({
    "#Storage" + VERSION_PATTERN + "Storage"
})
public class StorageResource extends ExternalServiceResourceImpl implements ExternalServiceResource {
    @JsonSetter(value = "Status", nulls = AS_EMPTY)
    @AsUnassigned(WHEN_NULL)
    private Ref<Status> status;

    @JsonSetter(value = "StorageControllers", nulls = AS_EMPTY)
    @AsUnassigned({WHEN_NULL, WHEN_EMPTY_COLLECTION})
    private Ref<List<StorageControllerResource>> storageControllers = unassigned();

    @JsonProperty("Drives")
    private List<ODataId> drives;

    @JsonProperty("Volumes")
    private Object volumes;

    @JsonProperty("Links")
    private Links links;

    public Ref<Status> getStatus() {
        return status;
    }

    @LinkName("storageControllers")
    public Iterable<ResourceSupplier> getStorageControllers() throws WebClientRequestException {
        if (storageControllers.isAssigned()) {
            return toSuppliersFromResources(storageControllers.get());
        } else {
            return null;
        }
    }

    @LinkName("drives")
    public Iterable<ResourceSupplier> getDrives() throws WebClientRequestException {
        return toSuppliers(drives);
    }

    public ResourceSupplier getChassis() {
        return toSupplier(links.enclosures.stream().findFirst().orElseThrow(
            () -> new IllegalStateException("There should be exact one enclosure Chassis within Enclosures collection."))
        );
    }

    public class Links extends RedfishLinks {
        @JsonProperty("Enclosures")
        private List<ODataId> enclosures = new ArrayList<>();
    }
}

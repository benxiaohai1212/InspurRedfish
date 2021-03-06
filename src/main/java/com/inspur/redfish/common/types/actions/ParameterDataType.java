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

package com.inspur.redfish.common.types.actions;

import com.inspur.redfish.common.types.EnumeratedType;

public enum ParameterDataType implements EnumeratedType {
    BOOLEAN("Boolean"),
    NUMBER("Number"),
    NUMBER_ARRAY("NumberArray"),
    STRING("String"),
    STRING_ARRAY("StringArray"),
    OBJECT("Object"),
    OBJECT_ARRAY("ObjectArray");

    private final String value;

    ParameterDataType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }
}

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

package com.inspur.redfish.common.types;

public enum BootSourceType implements EnumeratedType {
    NONE("None"),
    PXE("Pxe"),
    FLOPPY("Floppy"),
    CD("Cd"),
    USB("Usb"),
    HDD("Hdd"),
    BIOS_SETUP("BiosSetup"),
    UTILITIES("Utilities"),
    DIAGS("Diags"),
    UEFI_SHELL("UefiShell"),
    UEFI_TARGET("UefiTarget"),
    SD_CARD("SDCard"),
    REMOTE_DRIVE("RemoteDrive");

    private final String value;

    BootSourceType(String value) {
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
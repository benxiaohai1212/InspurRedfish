package com.inspur.redfish.common.types.redfish;

import java.net.URI;

public interface OdataIdProvider {
    ODataId asOdataId();

    interface ODataId {
        URI toUri();
        String toString();
    }
}
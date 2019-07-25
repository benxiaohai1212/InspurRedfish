package com.inspur.redfish.http;

import java.net.URI;
import java.util.Optional;

import com.inspur.redfish.south.resources.ExternalServiceResource;

public interface WebClient extends AutoCloseable {
    URI getBaseUri();

    @Override
    void close();

    ExternalServiceResource get(URI uri) throws WebClientRequestException;
    <T> URI post(URI requestUri, T obj) throws WebClientRequestException;
    <T> void postNotMonitored(String requestUri, T obj) throws WebClientRequestException;
    <T> Optional<ExternalServiceResource> patch(URI requestUri, T obj) throws WebClientRequestException;
    void delete(URI requestUri) throws WebClientRequestException;

    /**
     * Performs PATCH request using obj on requestUri and returns patched resource (performs additional GET on it if necessary)
     */
    /*参照PSME API的27页,以chassis的update为例,patch方法要么返回no content,要么返回updated resource,因此
     * 这里是patch.orElse*/
    @SuppressWarnings("unchecked")
    default <T extends ExternalServiceResource> T patchAndRetrieve(URI requestUri, Object obj) throws WebClientRequestException {
        return (T) patch(requestUri, obj).orElse(get(requestUri));
    }
}
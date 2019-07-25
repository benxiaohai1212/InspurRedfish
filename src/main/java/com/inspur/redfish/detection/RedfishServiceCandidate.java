package com.inspur.redfish.detection;

import static java.lang.String.format;
import static java.util.Objects.hash;

import java.net.URI;
import java.time.LocalDateTime;

import com.inspur.redfish.common.types.ServiceType;


public class RedfishServiceCandidate {
    private final ServiceType serviceType;
    private final URI endpointUri;
    //更新时间
    private LocalDateTime updateDate;
    //重试次数初始为0
    private long retries;

    public RedfishServiceCandidate(ServiceType serviceType, URI endpointUri, LocalDateTime updateDate) {
        if (endpointUri == null) {
            throw new IllegalArgumentException("URI cannot be null in DhcpServiceCandidate");
        }
        this.serviceType = serviceType;
        this.endpointUri = endpointUri;
        this.updateDate = updateDate;
        this.retries = 0;
    }

    public URI getEndpointUri() {
        return endpointUri;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public long getRetries() {
        return retries;
    }

    public void increaseRetries() {
        this.retries++;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RedfishServiceCandidate candidate = (RedfishServiceCandidate) o;

        if (serviceType != candidate.serviceType) {
            return false;
        }
        return endpointUri.equals(candidate.endpointUri);
    }

    @Override
    public int hashCode() {
        return hash(serviceType, endpointUri);
    }

    @Override
    public String toString() {
        return format("{URI=%s, type=%s, updateDate=%s}", getEndpointUri(), getServiceType(), updateDate);
    }
}


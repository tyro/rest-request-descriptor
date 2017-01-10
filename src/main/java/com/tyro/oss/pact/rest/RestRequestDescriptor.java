/*
 * #%L
 * rest-request-descriptor
 * %%
 * Copyright (C) 2016 - 2017 Tyro Payments Pty Ltd
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.tyro.oss.pact.rest;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

public class RestRequestDescriptor<T> {
    private final String url;
    private final HttpMethod method;
    private final Object request;
    private final Class<T> responseType;
    private final ParameterizedTypeReference<T> parameterizedResponseType;

    public RestRequestDescriptor(String url, HttpMethod method, Object request, Class<T> responseType) {
        this.url = url;
        this.method = method;
        this.request = request;
        this.responseType = responseType;
        this.parameterizedResponseType = null;
    }

    public RestRequestDescriptor(String url, HttpMethod method, Object request, ParameterizedTypeReference<T> parameterizedResponseType) {
        this.url = url;
        this.method = method;
        this.request = request;
        this.responseType = null;
        this.parameterizedResponseType = parameterizedResponseType;
    }

    private RestRequestDescriptor(String url, HttpMethod method, Object request, Class<T> responseType, ParameterizedTypeReference<T> parameterizedResponseType) {
        this.url = url;
        this.method = method;
        this.request = request;
        this.responseType = responseType;
        this.parameterizedResponseType = parameterizedResponseType;
    }

    public static Builder<Object> newRequestBuilder(String url) {
        return new Builder<>(url);
    }

    public static Builder<Object> newGetBuilder(String url) {
        return new Builder<>(url).withMethod(HttpMethod.GET);
    }

    public static Builder<Object> newPostBuilder(String url) {
        return new Builder<>(url).withMethod(HttpMethod.POST);
    }

    public static Builder<Object> newDeleteBuilder(String url) {
        return new Builder<>(url).withMethod(HttpMethod.DELETE);
    }

    public String getUrl() {
        return url;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public boolean hasParameterizedResponseType() {
        return parameterizedResponseType != null;
    }

    public Object getRequest() {
        return request;
    }

    public Class<T> getResponseType() {
        return responseType;
    }

    public ParameterizedTypeReference<T> getParameterizedResponseType() {
        return parameterizedResponseType;
    }

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public static class Builder<T> {
        private final String url;

        private HttpMethod method;
        private Object request;

        private Class<T> responseType;
        private ParameterizedTypeReference<T> parameterizedResponseType;

        private Builder(String url) {
            this.url = url;
        }

        public Builder<T> withMethod(HttpMethod method) {
            this.method = method;
            return this;
        }

        public Builder<T> withRequest(Object request) {
            this.request = request;
            return this;
        }

        @SuppressWarnings("unchecked")
        public <R> Builder<R> withResponseType(Class<R> responseType) {
            this.responseType = (Class<T>) responseType;
            return (Builder<R>) this;
        }

        @SuppressWarnings("unchecked")
        public <R> Builder<R> withParameterizedResponseType(ParameterizedTypeReference<R> parameterizedResponseType) {
            this.parameterizedResponseType = (ParameterizedTypeReference<T>) parameterizedResponseType;
            return (Builder<R>) this;
        }

        public RestRequestDescriptor<T> build() {
            if (bothResponseTypeAndParameterizedResponseTypeAreEitherSetOrUnset()) {
                throw new IllegalStateException("You must set one of either responseType or parameterizedResponseType");
            }

            return new RestRequestDescriptor<>(url, method, request, responseType, parameterizedResponseType);
        }

        private boolean bothResponseTypeAndParameterizedResponseTypeAreEitherSetOrUnset() {
            return (responseType != null) == (parameterizedResponseType != null);
        }
    }
}

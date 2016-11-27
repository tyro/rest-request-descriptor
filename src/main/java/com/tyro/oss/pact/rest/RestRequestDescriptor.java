/*
 * #%L
 * rest-request-descriptor
 * %%
 * Copyright (C) 2016 Tyro Payments Pty Ltd
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
}

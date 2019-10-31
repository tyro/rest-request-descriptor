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

import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class RestRequestDescriptorTest {

    @Test
    void shouldNotAllowBothParameterizedResponseTypeAndResponseTypeToBeSet() {
        try {
            RestRequestDescriptor.newRequestBuilder("url")
                    .withParameterizedResponseType(createStringParameterizedTypeReference())
                    .withResponseType(Integer.class)
                    .build();
            fail("Should have thrown an IllegalStateException here");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    @Test
    void shouldNotAllowNotSettingEitherOfParameterizedResponseTypeOrResponseType() {
        try {
            RestRequestDescriptor.newRequestBuilder("url")
                    .build();
            fail("Should have thrown an IllegalStateException here");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    @Test
    void shouldAllowSettingEitherParameterizedResponseTypeOrResponseType() {
        RestRequestDescriptor.newRequestBuilder("url")
                .withParameterizedResponseType(createStringParameterizedTypeReference())
                .build();

        RestRequestDescriptor.newRequestBuilder("url")
                .withResponseType(Integer.class)
                .build();
    }

    @Test
    void shouldCorrectlyConfigureGetBuilder() {
        RestRequestDescriptor<String> requestDescriptor = RestRequestDescriptor.newGetBuilder("url")
                .withResponseType(String.class)
                .build();

        assertEquals(HttpMethod.GET, requestDescriptor.getMethod());
    }

    @Test
    void shouldCorrectlyConfigurePostBuilder() {
        RestRequestDescriptor<String> requestDescriptor = RestRequestDescriptor.newPostBuilder("url")
                .withResponseType(String.class)
                .build();

        assertEquals(HttpMethod.POST, requestDescriptor.getMethod());
    }

    @Test
    void shouldCorrectlyConfigureDeleteBuilder() {
        RestRequestDescriptor<String> requestDescriptor = RestRequestDescriptor.newDeleteBuilder("url")
                .withResponseType(String.class)
                .build();

        assertEquals(HttpMethod.DELETE, requestDescriptor.getMethod());
    }

    private static ParameterizedTypeReference<String> createStringParameterizedTypeReference() {
        return new ParameterizedTypeReference<String>() {
        };
    }

}

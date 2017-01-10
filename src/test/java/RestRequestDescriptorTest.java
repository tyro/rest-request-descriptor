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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import com.tyro.oss.pact.rest.RestRequestDescriptor;

public class RestRequestDescriptorTest {

    @Test
    public void shouldNotAllowBothParameterizedResponseTypeAndResponseTypeToBeSet() throws Exception {
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
    public void shouldNotAllowNotSettingEitherOfParameterizedResponseTypeOrResponseType() throws Exception {
        try {
            RestRequestDescriptor.newRequestBuilder("url")
                    .build();
            fail("Should have thrown an IllegalStateException here");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    @Test
    public void shouldAllowSettingEitherParameterizedResponseTypeOrResponseType() throws Exception {
        RestRequestDescriptor.newRequestBuilder("url")
                .withParameterizedResponseType(createStringParameterizedTypeReference())
                .build();

        RestRequestDescriptor.newRequestBuilder("url")
                .withResponseType(Integer.class)
                .build();
    }

    @Test
    public void shouldCorrectlyConfigureGetBuilder() throws Exception {
        RestRequestDescriptor<String> requestDescriptor = RestRequestDescriptor.newGetBuilder("url")
                .withResponseType(String.class)
                .build();

        assertThat(requestDescriptor.getMethod(), is(HttpMethod.GET));
    }

    @Test
    public void shouldCorrectlyConfigurePostBuilder() throws Exception {
        RestRequestDescriptor<String> requestDescriptor = RestRequestDescriptor.newPostBuilder("url")
                .withResponseType(String.class)
                .build();

        assertThat(requestDescriptor.getMethod(), is(HttpMethod.POST));
    }

    @Test
    public void shouldCorrectlyConfigureDeleteBuilder() throws Exception {
        RestRequestDescriptor<String> requestDescriptor = RestRequestDescriptor.newDeleteBuilder("url")
                .withResponseType(String.class)
                .build();

        assertThat(requestDescriptor.getMethod(), is(HttpMethod.DELETE));
    }

    private static ParameterizedTypeReference<String> createStringParameterizedTypeReference() {
        return new ParameterizedTypeReference<String>() {
        };
    }

}

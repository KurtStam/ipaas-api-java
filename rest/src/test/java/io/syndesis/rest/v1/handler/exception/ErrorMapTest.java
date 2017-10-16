/**
 * Copyright (C) 2016 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.syndesis.rest.v1.handler.exception;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class ErrorMapTest {

    @Test
    public void testUnmarshalXML() throws IOException {
        String rawMsg = read("/HttpClientErrorException.xml");
        ErrorMap errorMap = ErrorMap.from(rawMsg);
        assertThat(errorMap.getError()).isEqualTo("Desktop applications only support the oauth_callback value 'oob'");
    }

    @Test
    public void testUnmarshalJSON() throws IOException {
        String rawMsg = read("/HttpClientErrorException.json");
        ErrorMap errorMap = ErrorMap.from(rawMsg);
        assertThat(errorMap.getError()).isEqualTo("Could not authenticate you.");
    }

    @Test
    public void testUnmarshalImpossible() throws IOException {
        String rawMsg = "This is just some other error format";
        ErrorMap errorMap = ErrorMap.from(rawMsg);
        assertThat(errorMap.getError()).isEqualTo(rawMsg);
    }

    private static String read(final String path) {
        try {
            return String.join("",
                Files.readAllLines(Paths.get(ErrorMapTest.class.getResource(path).toURI())));
        } catch (IOException | URISyntaxException e) {
            throw new IllegalArgumentException("Unable to read from path: " + path, e);
        }
    }

}

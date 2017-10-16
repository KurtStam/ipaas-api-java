package io.syndesis.rest.v1.handler.exception;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class ErrorMapTest {

    @Test
    public void testUnmarshal() throws IOException {
        String xmlMsg = read("/HttpClientErrorException.xml");
        ErrorMap errorMap = ErrorMap.from(xmlMsg);
        assertThat(errorMap.getError()).isEqualTo("Desktop applications only support the oauth_callback value 'oob'");
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

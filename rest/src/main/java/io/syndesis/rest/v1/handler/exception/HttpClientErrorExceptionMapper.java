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

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
@Provider
public class HttpClientErrorExceptionMapper implements ExceptionMapper<HttpClientErrorException> {

    @Override
    public Response toResponse(HttpClientErrorException exception) {
        String message = exception.getMessage() + 
                " " + ErrorMap.from(new String(exception.getResponseBodyAsByteArray())).getError();
        return Response.status(exception.getStatusCode().value()).entity(message).build();
    }
}

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

import java.io.IOException;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@XmlRootElement (name="hash")
public class ErrorMap {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorMap.class);
            
    private String error;
    private String request;


    public ErrorMap() {
        super();
    }
    
    public ErrorMap(String error) {
        super();
        this.error = error;
    }
    /**
     * Performs best effort to parse the rawMsg. If all parsers fail it returns the raw message.
     * 
     * @param rawMsg
     * @return ErrorMap containing the underlying error message.
     */
    public static ErrorMap from(String rawMsg) {
        if ('<' == rawMsg.charAt(0)) {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(ErrorMap.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                ErrorMap map = (ErrorMap) jaxbUnmarshaller.unmarshal(new StringReader(rawMsg));
                if (map.getError()!=null) {
                    return map;
                }
            } catch (JAXBException e) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Swallowing " + e.getMessage());
                }
            }
        }
        if ('{' == rawMsg.charAt(0)) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(rawMsg);
                JsonNode errors = jsonNode.get("errors");
                if (errors.isArray() && errors.iterator().hasNext()) {
                    return new ErrorMap(errors.iterator().next().get("message").asText());
                }
            } catch (IOException e) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Swallowing " + e.getMessage());
                }
            }
        }
        return new ErrorMap(rawMsg);
    }

    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
    public String getRequest() {
        return request;
    }
    public void setRequest(String request) {
        this.request = request;
    }
}


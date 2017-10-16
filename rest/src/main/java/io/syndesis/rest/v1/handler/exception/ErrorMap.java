package io.syndesis.rest.v1.handler.exception;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement (name="hash")
public class ErrorMap {

    private String error;
    private String request;

    public static ErrorMap from(String xmlMsg) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ErrorMap.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (ErrorMap) jaxbUnmarshaller.unmarshal(new StringReader(xmlMsg));
        } catch (JAXBException e) {}
        return new ErrorMap();
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


package org.connectWithGson;

public class Response {

    String response;
    String reason;


    public Response(String response) {
        this.response = response;
    }

    public Response(String response, String reason) {
        this.response = response;
        this.reason = reason;
    }
}

package com.epam.esm.exception;

public class ResourceNoLinks extends RuntimeException{

    public ResourceNoLinks() {
        super();
    }

    public ResourceNoLinks(String message) {
        super(message);
    }

    public ResourceNoLinks(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNoLinks(Throwable cause) {
        super(cause);
    }

}

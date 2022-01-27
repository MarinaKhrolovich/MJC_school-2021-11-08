package com.epam.esm.exception;

public class ResourceNoLinksException extends RuntimeException {

    public ResourceNoLinksException() {
        super();
    }

    public ResourceNoLinksException(String message) {
        super(message);
    }

    public ResourceNoLinksException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNoLinksException(Throwable cause) {
        super(cause);
    }

}

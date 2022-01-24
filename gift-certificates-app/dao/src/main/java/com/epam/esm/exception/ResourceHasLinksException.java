package com.epam.esm.exception;

public class ResourceHasLinksException extends RuntimeException {

    private int resourceId;

    public ResourceHasLinksException() {
        super();
    }

    public ResourceHasLinksException(int id) {
        resourceId = id;
    }

    public ResourceHasLinksException(String message) {
        super(message);
    }

    public ResourceHasLinksException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceHasLinksException(Throwable cause) {
        super(cause);
    }

    public int getResourceId() {
        return resourceId;
    }

}

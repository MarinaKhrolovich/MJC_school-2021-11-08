package com.epam.esm.exception;

public class ResourceNotFoundException extends RuntimeException {

    private int resourceId;

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(int id) {
        resourceId = id;
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    public int getResourceId() {
        return resourceId;
    }
}

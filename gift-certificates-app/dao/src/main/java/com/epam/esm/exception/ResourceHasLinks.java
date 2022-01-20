package com.epam.esm.exception;

public class ResourceHasLinks extends RuntimeException{

    private int resourceId;

    public ResourceHasLinks() {
        super();
    }

    public ResourceHasLinks(int id) {
        resourceId = id;
    }

    public ResourceHasLinks(String message) {
        super(message);
    }

    public ResourceHasLinks(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceHasLinks(Throwable cause) {
        super(cause);
    }

    public int getResourceId() {
        return resourceId;
    }

}

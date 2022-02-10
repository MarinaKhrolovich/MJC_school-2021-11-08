package com.epam.esm.exception;

public interface MessageLocal {

    String MESSAGE_SOMETHING_WRONG = "message.somethingWrong";
    String MESSAGE_ID_MIN = "message.path.id.min";

    String MESSAGE_RESOURCE_NOT_FOUND = "message.resource.notFound";
    String MESSAGE_RESOURCE_ALREADY_EXISTS = "message.resource.alreadyExists";
    String MESSAGE_RESOURCE_HAS_LINKS = "message.resource.hasLinks";
    String MESSAGE_RESOURCE_NO_LINKS = "message.resource.noLinks";

    String MESSAGE_TOKEN_INVALID = "message.token.invalid";
    String MESSAGE_TOKEN_EXPIRED = "message.token.expired";

}

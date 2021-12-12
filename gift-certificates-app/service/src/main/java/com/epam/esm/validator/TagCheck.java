package com.epam.esm.validator;

import com.epam.esm.bean.Tag;
import com.epam.esm.exception.ValidatorException;

public final class TagCheck {

    public static final String MESSAGE_TAG_NAME_FILL = "message.tag.name.fill";
    public static final String MESSAGE_TAG_NAME_LENGTH = "message.tag.name.length";

    public static void checkTag(Tag tag) {
        String name = tag.getName();

        if (name == null) {
            throw new ValidatorException(MESSAGE_TAG_NAME_FILL);
        }

        if (name.length() < 3 || name.length() > 45) {
            throw new ValidatorException(MESSAGE_TAG_NAME_LENGTH);
        }

    }
}

package com.epam.esm.validator;

import com.epam.esm.bean.Tag;
import com.epam.esm.exception.ValidatorException;
import org.springframework.stereotype.Component;

@Component
public class TagCheck {

    public static final String MESSAGE_TAG_NAME_FILL = "message.tag.name.fill";
    public static final String MESSAGE_TAG_NAME_LENGTH = "message.tag.name.length";
    public static final int MIN_NAME_LENGTH = 3;
    public static final int MAX_NAME_LENGTH = 45;

    public void check(Tag tag) {
        String name = tag.getName();

        if (name == null) {
            throw new ValidatorException(MESSAGE_TAG_NAME_FILL);
        }

        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            throw new ValidatorException(MESSAGE_TAG_NAME_LENGTH);
        }
    }
}

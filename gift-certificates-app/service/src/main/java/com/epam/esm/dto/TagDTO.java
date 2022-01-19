package com.epam.esm.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class TagDTO extends RepresentationModel<TagDTO> {

    private int id;

    @NotBlank(message = "{message.tag.name.fill}")
    @Size(min = 3, max = 45, message = "{message.tag.name.length}")
    private String name;
}

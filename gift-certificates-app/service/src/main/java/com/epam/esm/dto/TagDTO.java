package com.epam.esm.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TagDTO {

    private int id;

    @NotBlank(message = "{message.tag.name.fill}")
    @Size(min = 3, max = 45, message = "{message.tag.name.length}")
    private String name;
}

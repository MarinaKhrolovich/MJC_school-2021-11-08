package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO {

    @Min(value = 1, message = "message.limit.min")
    private Integer limit = 10;

    @Min(value = 0, message = "message.offset.min")
    private Integer offset = 0;

}

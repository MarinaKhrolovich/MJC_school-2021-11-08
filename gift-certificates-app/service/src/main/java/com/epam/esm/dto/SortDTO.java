package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SortDTO {

    public static final String ID = "id";
    public static final String DESC = "DESC";

    private String sortBy = ID;

    private String orderBy = DESC;

}

package com.epam.esm.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Search {

    private List<String> tagName = new ArrayList<>();

    private String name;

    private String description;

}

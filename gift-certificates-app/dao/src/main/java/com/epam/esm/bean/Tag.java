package com.epam.esm.bean;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Tag implements Serializable {

    private static final long serialVersionUID = 4183390015095448535L;

    private int id;

    private String name;

}

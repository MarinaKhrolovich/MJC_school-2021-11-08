package com.epam.esm.bean;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "tag")
public class Tag implements Serializable {

    private static final long serialVersionUID = 4183390015095448535L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name",nullable = false,unique = true)
    private String name;

    @ManyToMany(mappedBy = "tagList")
    private List<Certificate> certificates = new ArrayList<>();

}

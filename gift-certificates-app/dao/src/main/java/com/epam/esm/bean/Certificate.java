package com.epam.esm.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Data
@Table(name = "certificate")
public class Certificate implements Serializable {

    private static final long serialVersionUID = 9078411259250099890L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private Instant createDate;
    private Instant lastUpdateDate;
    private List<Tag> tagList;

}

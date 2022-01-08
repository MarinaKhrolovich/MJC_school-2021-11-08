package com.epam.esm.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
public class Certificate implements Serializable {

    private static final long serialVersionUID = 9078411259250099890L;

    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private Instant createDate;
    private Instant lastUpdateDate;
    private List<Tag> tagList;

}

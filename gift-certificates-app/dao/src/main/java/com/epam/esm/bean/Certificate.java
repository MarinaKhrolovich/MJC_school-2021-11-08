package com.epam.esm.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Certificate implements Serializable {

    private static final long serialVersionUID = 9078411259250099890L;

    private int id;
    private String name;
    private String description;
    private double price;
    private int duration;
    private Instant createDate;
    private Instant lastUpdateDate;
    private List<Tag> tagList;

}

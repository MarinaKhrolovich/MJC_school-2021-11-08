package com.epam.esm.dto;

import lombok.*;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CertificateDTO {

    private int id;

    private String name;

    private String description;

    private double price;

    private int duration;

    private Instant createDate;

    private Instant lastUpdateDate;

    private List<TagDTO> tagList;

}

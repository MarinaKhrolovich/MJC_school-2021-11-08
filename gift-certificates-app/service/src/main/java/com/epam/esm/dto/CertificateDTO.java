package com.epam.esm.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @NotBlank(message = "{message.certificate.name.fill}")
    @Size(min = 3, max = 100, message = "{message.certificate.name.length}")
    private String name;

    @NotBlank(message = "{message.certificate.description.fill}")
    @Size(min = 3, max = 1000, message = "{message.certificate.description.length}")
    private String description;

    @DecimalMin(value="0.01", message="{message.certificate.price}")
    private double price;

    @Min(value=1, message="{message.certificate.duration}")
    private int duration;

    private Instant createDate;

    private Instant lastUpdateDate;

    @Valid
    private List<TagDTO> tagList;

}

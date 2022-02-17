package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
public class CertificateUpdateDTO extends RepresentationModel<CertificateUpdateDTO> {

    private static final String NOT_BLANK_FIELD = "^(?!\\s*$).+";

    private int id;

    @Pattern(regexp = NOT_BLANK_FIELD, message = "{message.certificate.name.fill}")
    @Size(min = 3, max = 100, message = "{message.certificate.name.length}")
    private String name;

    @Pattern(regexp = NOT_BLANK_FIELD, message = "{message.certificate.description.fill}")
    @Size(min = 3, max = 1000, message = "{message.certificate.description.length}")
    private String description;

    @DecimalMin(value = "0.01", message = "{message.certificate.price}")
    private BigDecimal price;

    @Min(value = 1, message = "{message.certificate.duration}")
    private Integer duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
    private Instant createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
    private Instant lastUpdateDate;

    @Valid
    private Set<TagDTO> tagList;

}

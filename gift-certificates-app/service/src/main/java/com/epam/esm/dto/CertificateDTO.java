package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CertificateDTO extends RepresentationModel<CertificateDTO> {

    private int id;

    @NotBlank(message = "{message.certificate.name.fill}")
    @Size(min = 3, max = 100, message = "{message.certificate.name.length}")
    private String name;

    @NotBlank(message = "{message.certificate.description.fill}")
    @Size(min = 3, max = 1000, message = "{message.certificate.description.length}")
    private String description;

    @NotNull(message = "{message.certificate.price.fill")
    @DecimalMin(value = "0.01", message = "{message.certificate.price.value}")
    private BigDecimal price;

    @NotNull(message = "{message.certificate.duration.fill")
    @Min(value = 1, message = "{message.certificate.duration.value}")
    private Integer duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
    private Instant createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
    private Instant lastUpdateDate;

    @Valid
    private List<TagDTO> tagList;

}

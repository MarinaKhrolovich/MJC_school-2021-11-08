package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
public class OrderDTO extends RepresentationModel<OrderDTO> {

    private int id;

    @NotNull(message="{message.order.user.id.fill}")
    private UserDTO user;

    @NotNull(message="{message.order.certificate.id.fill}")
    private List<CertificateDTO> certificates;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
    private Instant createDate;

    private BigDecimal cost;

}

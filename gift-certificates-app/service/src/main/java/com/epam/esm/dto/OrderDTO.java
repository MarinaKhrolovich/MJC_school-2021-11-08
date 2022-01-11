package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@Data
public class OrderDTO {

    private int id;

    @NotNull(message="{message.order.user.id.fill}")
    @Min(value=1, message="{message.order.user.id.value}")
    private Integer userId;

    @NotNull(message="{message.order.certificate.id.fill}")
    @Min(value=1, message="{message.order.certificate.id.value}")
    private Integer certificateId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
    private Instant createDate;

    private BigDecimal cost;

}

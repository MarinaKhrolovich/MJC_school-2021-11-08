package com.epam.esm.dto;

import com.epam.esm.bean.Certificate;
import com.epam.esm.bean.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
public class OrderDTO {

    private int id;

    private User user;

    private List<Certificate> certificateList;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
    private Instant createDate;

    private BigDecimal cost;

}

package com.epam.esm.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {

    private static final long serialVersionUID = 1610961455968411283L;

    private int id;

    private User user;

    private List<Certificate> certificateList;

    private Instant createDate;

    private BigDecimal cost;

}

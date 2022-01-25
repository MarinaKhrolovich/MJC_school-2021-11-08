package com.epam.esm.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 1610961455968411283L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "order_certificate",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "certificate_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Certificate> certificates;

    @Column(name = "create_date")
    private Instant createDate;

    @Column(name = "price")
    private BigDecimal price;

    @PrePersist
    protected void onCreate() {
        this.createDate = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        this.setPrice(BigDecimal.valueOf(0));
    }

}

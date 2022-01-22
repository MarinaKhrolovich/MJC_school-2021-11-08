package com.epam.esm.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
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
    @ToString.Exclude
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "order_certificate",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "certificate_id")
    )
    @ToString.Exclude
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (!user.equals(order.user)) return false;
        if (!certificates.equals(order.certificates)) return false;
        if (!createDate.equals(order.createDate)) return false;
        return price.equals(order.price);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + user.hashCode();
        result = 31 * result + certificates.hashCode();
        result = 31 * result + createDate.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }

}

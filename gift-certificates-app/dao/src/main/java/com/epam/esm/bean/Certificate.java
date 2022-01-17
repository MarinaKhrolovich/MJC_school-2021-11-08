package com.epam.esm.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "certificate")
public class Certificate implements Serializable {

    private static final long serialVersionUID = 9078411259250099890L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="price")
    private BigDecimal price;

    @Column(name="duration")
    private Integer duration;

    @Column(name="create_date", updatable=false)
    private Instant createDate;

    @Column(name="last_update_date",insertable = false)
    private Instant lastUpdateDate;

    @ManyToMany(fetch=FetchType.LAZY,
            cascade= {CascadeType.ALL})
    @JoinTable(
            name="certificate_tag",
            joinColumns=@JoinColumn(name="certificate_id"),
            inverseJoinColumns=@JoinColumn(name="tag_id")
    )
    private List<Tag> tagList = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createDate = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        this.lastUpdateDate = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdateDate = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    }
}

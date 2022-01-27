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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "certificate")
public class Certificate implements Serializable {

    private static final long serialVersionUID = 9078411259250099890L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "create_date", updatable = false)
    private Instant createDate;

    @Column(name = "last_update_date", insertable = false)
    private Instant lastUpdateDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "certificate_tag",
            joinColumns = @JoinColumn(name = "certificate_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "tag_id", nullable = false)
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Tag> tagList = new HashSet<>();

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

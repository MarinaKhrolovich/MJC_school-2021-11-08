package com.epam.esm.bean;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
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
    @ToString.Exclude
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Certificate that = (Certificate) o;

        if (id != that.id) return false;
        if (!name.equals(that.name)) return false;
        if (!description.equals(that.description)) return false;
        if (!price.equals(that.price)) return false;
        if (!duration.equals(that.duration)) return false;
        if (!createDate.equals(that.createDate)) return false;
        if (!lastUpdateDate.equals(that.lastUpdateDate)) return false;
        return tagList.equals(that.tagList);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + duration.hashCode();
        result = 31 * result + createDate.hashCode();
        result = 31 * result + lastUpdateDate.hashCode();
        result = 31 * result + tagList.hashCode();
        return result;
    }

}

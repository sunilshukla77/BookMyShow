package com.book.my.show.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Accessors(chain = true)
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "city")
public class City extends BaseEntity {
    private static final long serialVersionUID = 2191893425539823344L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "city_name")
    private String name;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "city_theatre", joinColumns = @JoinColumn(name = "city_id"),
            inverseJoinColumns = @JoinColumn(name = "theatre_id"))
    private Set<Theatre> theatres = new HashSet<>();

    //Add Theatre
    public void addTheatre(Theatre theatre) {
        theatres.add(theatre);
        theatre.getCities().add(this);
    }

    //Remove Theatre
    public void removeTheatre(Theatre theatre) {
        theatres.remove(theatre);
        theatre.getCities().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City )) return false;
        return id != null && id.equals(((City) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

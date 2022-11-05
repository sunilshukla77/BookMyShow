package com.book.my.show.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Accessors(chain = true)
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "movie")
public class Movie extends BaseEntity {
    private static final long serialVersionUID = 8830677172407410347L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "movie_name")
    private String name;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "movie")
    private List<Auditorium> auditoriums = new ArrayList<>();

    //Add Auditorium
    public void addAuditorium(Auditorium auditorium) {
        auditoriums.add(auditorium);
        auditorium.setMovie(this);
    }

    //Remove Auditorium
    public void removeAuditorium(Auditorium auditorium) {
        auditoriums.remove(auditorium);
        auditorium.setMovie(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie )) return false;
        return id != null && id.equals(((Movie) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package com.book.my.show.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "auditorium")
public class Auditorium extends BaseEntity {
    private static final long serialVersionUID = -8832110236525833374L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "auditorium_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theatre_id", referencedColumnName = "id")
    private Theatre theatre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;


    @OneToMany(orphanRemoval = true, mappedBy = "auditorium", cascade = CascadeType.ALL)
    private List<Seat> seats = new ArrayList<>();

    //Add Seat
    public void addSeat(Seat seat) {
        seats.add(seat);
        seat.setAuditorium(this);
    }

    //Remove Seat
    public void removeSeat(Seat seat) {
        seats.remove(seat);
        seat.setAuditorium(this);
    }

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "auditorium_show", joinColumns = @JoinColumn(name = "auditorium_id"),
            inverseJoinColumns = @JoinColumn(name = "show_id"))
    private Set<Show> shows = new HashSet<>();

    //Add Show
    public void addShow(Show show) {
        shows.add(show);
        show.getAuditoriums().remove(this);
    }

    //Remove Show
    public void removeShow(Show show) {
        shows.remove(show);
        show.getAuditoriums().remove(this);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Auditorium )) return false;
        return id != null && id.equals(((Auditorium) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

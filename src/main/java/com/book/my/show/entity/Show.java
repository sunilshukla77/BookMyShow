package com.book.my.show.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.*;

@Accessors(chain = true)
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "show")
public class Show extends BaseEntity {
    private static final long serialVersionUID = 6160600467481367584L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "show_name")
    private String name;
    @Column(name = "show_time")
    private String showTime;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auditorium_id", referencedColumnName = "id")
    private Auditorium auditorium;*/
    @ManyToMany(mappedBy = "shows")
    private Set<Auditorium> auditoriums = new HashSet<>();


    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "show")
    private List<BookShowSeat> bookShowSeats = new ArrayList<>();

    //Add BookShowSeat
    public void addBookShowSeat(BookShowSeat bookShowSeat) {
        bookShowSeats.add(bookShowSeat);
        bookShowSeat.setShow(this);
    }

    //Remove BookShowSeat
    public void removeBookShowSeat(BookShowSeat bookShowSeat) {
        bookShowSeats.remove(bookShowSeat);
        bookShowSeat.setShow(null);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Show)) return false;
        return id != null && id.equals(((Show) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

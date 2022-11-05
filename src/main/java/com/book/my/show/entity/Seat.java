package com.book.my.show.entity;

import com.book.my.show.type.SeatType;
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
@Table(name = "seat")
public class Seat extends BaseEntity {
    private static final long serialVersionUID = 225924801850279213L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "seat_name")
    private String name;
    @Column(name = "seat_type")
    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auditorium_id", referencedColumnName = "id")
    private Auditorium auditorium;


    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "seat")
    private List<BookShowSeat> bookShowSeats = new ArrayList<>();

    //Add BookShowSeat
    public void addBookShowSeat(BookShowSeat bookShowSeat) {
        bookShowSeats.add(bookShowSeat);
        bookShowSeat.setSeat(this);
    }

    //Remove BookShowSeat
    public void removeBookShowSeat(BookShowSeat bookShowSeat) {
        bookShowSeats.remove(bookShowSeat);
        bookShowSeat.setSeat(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seat )) return false;
        return id != null && id.equals(((Seat) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

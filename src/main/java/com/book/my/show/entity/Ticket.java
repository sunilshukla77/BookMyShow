package com.book.my.show.entity;

import com.book.my.show.type.BookStatus;
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
@Table(name = "ticket")
public class Ticket extends BaseEntity {
    private static final long serialVersionUID = 5897180508752561503L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ticket_status")
    @Enumerated(EnumType.STRING)
    private BookStatus status;
    @Column(name = "ticket_id")
    private String ticketId;
    @Column(name = "ticket_amount")
    private double ticketAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    @OneToMany(orphanRemoval = true, mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<BookShowSeat> bookShowSeats = new ArrayList<>();

    //Add BookShowSeat
    public void addSeat(BookShowSeat bookShowSeat) {
        bookShowSeats.add(bookShowSeat);
        bookShowSeat.setTicket(this);
    }

    //Remove BookShowSeat
    public void removeSeat(BookShowSeat bookShowSeat) {
        bookShowSeats.remove(bookShowSeat);
        bookShowSeat.setTicket(null);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket )) return false;
        return id != null && id.equals(((Ticket) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package com.book.my.show.entity;

import com.book.my.show.type.SeatStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Accessors(chain = true)
@Setter
@Getter
@NoArgsConstructor
@Table(name = "book_show_seat")
@Entity
public class BookShowSeat extends BaseEntity {
    private static final long serialVersionUID = 6306307587603459855L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "seat_status")
    @Enumerated(EnumType.STRING)
    private SeatStatus status;
    @Column(name = "seat_book_time")
    private LocalDateTime bookTime;
    @Column(name = "show_day")
    private String showDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", referencedColumnName = "id")
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id", referencedColumnName = "id")
    private Show show;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", referencedColumnName = "id")
    private Ticket ticket;

    @Version
    private int version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookShowSeat )) return false;
        return id != null && id.equals(((BookShowSeat) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

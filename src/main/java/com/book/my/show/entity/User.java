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
@Table(name = "user")
public class User extends BaseEntity {
    private static final long serialVersionUID = 2296703749268988057L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name")
    private String name;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "user_email")
    private String email;
    @Column(name = "mobile_number")
    private String mobileNumber;

    @OneToMany(orphanRemoval = true, mappedBy = "user", cascade = CascadeType.ALL)
    private final List<Ticket> tickets = new ArrayList<>();

    //Add ticket
    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
        ticket.setUser(this);
    }

    //Remove ticket
    public void removeTicket(Ticket ticket) {
        tickets.remove(ticket);
        ticket.setUser(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User )) return false;
        return id != null && id.equals(((User) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package com.book.my.show.repository;

import com.book.my.show.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends PagingAndSortingRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {

    @Query("SELECT t FROM Ticket t WHERE t.user.userId = ?1 ORDER BY id")
    Page<Ticket> findAllByUserId(@Param("userId") String userId, Pageable pageable);

    @Query("SELECT t FROM Ticket t WHERE t.user.userId = ?1 AND t.ticketId = ?2")
    Optional<Ticket> findByUserIdAndTicketId(@Param("userId") String userId, @Param("ticketId") String ticketId);
}
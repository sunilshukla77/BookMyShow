package com.book.my.show.repository;

import com.book.my.show.entity.Seat;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends PagingAndSortingRepository<Seat, Long>, JpaSpecificationExecutor<Seat> {
}

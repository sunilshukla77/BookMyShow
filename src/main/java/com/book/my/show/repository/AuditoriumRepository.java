package com.book.my.show.repository;

import com.book.my.show.entity.Auditorium;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriumRepository extends PagingAndSortingRepository<Auditorium, Long>, JpaSpecificationExecutor<Auditorium> {
}

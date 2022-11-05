package com.book.my.show.repository.specification;

import com.book.my.show.entity.Auditorium;
import com.book.my.show.entity.BookShowSeat;
import com.book.my.show.entity.Seat;
import com.book.my.show.type.SeatStatus;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Builder
public class SeatSpecification extends SpecificationHelper implements Specification<Seat> {
    private static final long serialVersionUID = 2324943510422081269L;

    private final String cityName;
    private final String theatreName;
    private final String movieName;
    private final String showTime;
    private final String showDay;

    @Override
    public Predicate toPredicate(Root<Seat> seatRoot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        final Join<Seat, Auditorium> auditoriumRoot = seatRoot.join("auditorium");
        final Join<Seat, BookShowSeat> bookShowSeatRoot = seatRoot.join("bookShowSeats");

        if(StringUtils.hasLength(movieName)) {
            predicates.add(criteriaBuilder.equal(criteriaBuilder.upper(
                    auditoriumRoot.join("movie").get("name")), movieName));
        }

        if(StringUtils.hasLength(showTime)) {
            predicates.add(criteriaBuilder.equal(criteriaBuilder.upper(bookShowSeatRoot
                    .join("show").get("name")), showTime));
        }

        if(StringUtils.hasLength(theatreName)) {
            predicates.add(criteriaBuilder.equal(criteriaBuilder.upper(auditoriumRoot
                    .join("theatre").get("name")), theatreName));
        }
        if(StringUtils.hasLength(showDay)) {
            predicates.add(criteriaBuilder.equal(bookShowSeatRoot.get("showDay"), showDay));
        }

        if(StringUtils.hasLength(cityName)) {
            predicates.add(criteriaBuilder.equal(criteriaBuilder.upper(auditoriumRoot
                    .join("theatre").join("cities").get("name")), cityName));
        }

        //Filter out any booked/blocked seat
        predicates.add(criteriaBuilder.equal(bookShowSeatRoot.get("status"), SeatStatus.OPEN));

       return getPredicateWithAndOperation(criteriaBuilder, predicates);
    }
}

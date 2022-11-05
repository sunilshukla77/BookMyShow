package com.book.my.show.repository.specification;

import com.book.my.show.entity.*;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Builder
public class BookShowSeatSpecification extends SpecificationHelper implements Specification<BookShowSeat> {
    private static final long serialVersionUID = -8237719561246567834L;

    private final String cityName;
    private final String theatreName;
    private final String movieName;
    private final String showDay;
    private final String showTime;
    private final List<String> seatInfoList;

    @Override
    public Predicate toPredicate(Root<BookShowSeat> bookShowSeatroot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> andOperationPredicates = new ArrayList<>(), orOperationPredicates = new ArrayList<>();

        final Join<BookShowSeat, Show> showRoot = bookShowSeatroot.join("show");
        final Join<BookShowSeat, Seat> seatRoot = bookShowSeatroot.join("seat");
        final Join<Seat, Auditorium> auditoriumRoot = seatRoot.join("auditorium");
        final Join<Auditorium, Theatre> theatreRoot = auditoriumRoot.join("theatre");
        final Join<Auditorium, Movie> movieRoot = auditoriumRoot.join("movie");

        if(StringUtils.hasLength(cityName)) {
            andOperationPredicates.add(criteriaBuilder.equal(criteriaBuilder.upper(theatreRoot.join("cities").get("name")), cityName.toUpperCase()));
        }

        if(StringUtils.hasLength(theatreName)) {
            andOperationPredicates.add(criteriaBuilder.equal(criteriaBuilder.upper(theatreRoot.get("name")), theatreName.toUpperCase()));
        }

        if(StringUtils.hasLength(movieName)) {
            andOperationPredicates.add(criteriaBuilder.equal(criteriaBuilder.upper(movieRoot.get("name")), movieName.toUpperCase()));
        }

        if(StringUtils.hasLength(showDay)) {
            andOperationPredicates.add(criteriaBuilder.equal(bookShowSeatroot.get("showDay"), showDay));
        }

        if(StringUtils.hasLength(showTime)) {
            andOperationPredicates.add(criteriaBuilder.equal(criteriaBuilder.upper(showRoot.get("name")), showTime.toUpperCase()));
        }

        seatInfoList.forEach((seatName) -> {
            orOperationPredicates.add(criteriaBuilder.equal(criteriaBuilder.upper(seatRoot.get("name")), seatName.toUpperCase()));
        });

        if(CollectionUtils.isEmpty(andOperationPredicates)) {
            getPredicateWithOrOperation(criteriaBuilder, orOperationPredicates);
        }
        if(CollectionUtils.isEmpty(orOperationPredicates)) {
            getPredicateWithAndOperation(criteriaBuilder, andOperationPredicates);
        }
        if(!CollectionUtils.isEmpty(andOperationPredicates) && !CollectionUtils.isEmpty(orOperationPredicates)) {
            return criteriaBuilder.and(getPredicateWithAndOperation(criteriaBuilder, andOperationPredicates),
                    getPredicateWithOrOperation(criteriaBuilder, orOperationPredicates));
        }
        return null;
    }
}

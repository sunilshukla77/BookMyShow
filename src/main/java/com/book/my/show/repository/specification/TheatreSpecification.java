package com.book.my.show.repository.specification;

import com.book.my.show.entity.Theatre;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Builder
public class TheatreSpecification extends SpecificationHelper implements Specification<Theatre> {
    private static final long serialVersionUID = -6841749395292180007L;

    private final String cityName;
    private final String theatreName;
    private final String movieName;


    @Override
    public Predicate toPredicate(Root<Theatre> theatreRoot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();

        if(StringUtils.hasLength(cityName)) {
            predicateList.add(criteriaBuilder.equal(criteriaBuilder.upper(theatreRoot.join("cities").get("name")), cityName));
        }
        if(StringUtils.hasLength(theatreName)) {
            predicateList.add(criteriaBuilder.equal(criteriaBuilder.upper(theatreRoot.get("name")), theatreName));
        }

        if(StringUtils.hasLength(movieName)) {
            predicateList.add(criteriaBuilder.equal(criteriaBuilder.upper(theatreRoot.join("auditoriums").join("movie").get("name")), movieName));
        }

        return getPredicateWithAndOperation(criteriaBuilder, predicateList);
    }
}

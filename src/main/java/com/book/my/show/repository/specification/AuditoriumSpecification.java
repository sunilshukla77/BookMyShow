package com.book.my.show.repository.specification;

import com.book.my.show.entity.Auditorium;
import com.book.my.show.entity.Theatre;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Builder
public class AuditoriumSpecification extends SpecificationHelper implements Specification<Auditorium> {
    private static final long serialVersionUID = -1589324014475816874L;

    private final String cityName;
    private final String theatreName;

    @Override
    public Predicate toPredicate(Root<Auditorium> auditoriumRoot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        final Join<Auditorium, Theatre> theatreRoot = auditoriumRoot.join("theatre");
        if(StringUtils.hasLength(theatreName)) {
            predicates.add(criteriaBuilder.equal(criteriaBuilder.upper(theatreRoot.get("name")), theatreName));
        }

        if(StringUtils.hasLength(cityName)) {
            predicates.add(criteriaBuilder.equal(criteriaBuilder.upper(theatreRoot.join("cities").get("name")), cityName));
        }

        return getPredicateWithAndOperation(criteriaBuilder, predicates);
    }
}

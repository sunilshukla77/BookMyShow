package com.book.my.show.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.List;

public abstract class SpecificationHelper {
    public Predicate getPredicateWithAndOperation(CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {
        if(predicateList.size() == 0) {
            return null;
        }
        Predicate resultantPredicate = predicateList.get(0);

        for(int counter = 1; counter < predicateList.size(); counter++) {
            resultantPredicate = criteriaBuilder.and(resultantPredicate, predicateList.get(counter));
        }

        return resultantPredicate;
    }

    public Predicate getPredicateWithOrOperation(CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {
        if(predicateList.size() == 0) {
            return null;
        }
        Predicate resultantPredicate = predicateList.get(0);

        for(int counter = 1; counter < predicateList.size(); counter++) {
            resultantPredicate = criteriaBuilder.or(resultantPredicate, predicateList.get(counter));
        }

        return resultantPredicate;
    }
}

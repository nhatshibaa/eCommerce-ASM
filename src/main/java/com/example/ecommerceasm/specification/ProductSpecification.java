package com.example.ecommerceasm.specification;

import com.example.ecommerceasm.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class ProductSpecification implements Specification<Order> {

    private SearchCriteria searchCriteria;

    public ProductSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    // criteriaBuilder giúp xử lý các toán tử.
    // root lấy ra trường và giá trị các trường.
    @Override
    public Predicate toPredicate(
            Root<Order> root,
            CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder) {
        switch (searchCriteria.getOperator()) {
            case EQUALS:
                return criteriaBuilder.equal(
                        root.get(searchCriteria.getKey()),
                        searchCriteria.getValue());
            case GREATER_THAN:
                return criteriaBuilder.greaterThan(
                        root.get(searchCriteria.getKey()),
                        String.valueOf(searchCriteria.getValue()));
            case GREATER_THAN_OR_EQUALS:
                return criteriaBuilder.greaterThanOrEqualTo(
                        root.get(searchCriteria.getKey()),
                        String.valueOf(searchCriteria.getValue()));
            case LESS_THAN:
                return criteriaBuilder.lessThan(
                        root.get(searchCriteria.getKey()),
                        String.valueOf(searchCriteria.getValue()));
            case LESS_THAN_OR_EQUALS:
                return criteriaBuilder.lessThanOrEqualTo(
                        root.get(searchCriteria.getKey()),
                        String.valueOf(searchCriteria.getValue()));
        }
        return null;
    }

    public static void main(String[] args) {
        Specification<Order> specification = Specification.where(null);

        SearchCriteria searchCriteria1 = new SearchCriteria(
                "userId",
                SearchCriteriaOperator.EQUALS,
                "0");
        ProductSpecification spec1 = new ProductSpecification(searchCriteria1);
        specification = specification.and(spec1);

        SearchCriteria searchCriteria2 = new SearchCriteria(
                "status",
                SearchCriteriaOperator.EQUALS,
                2);
        ProductSpecification spec2 = new ProductSpecification(searchCriteria2);
        specification = specification.and(spec2);


    }
}

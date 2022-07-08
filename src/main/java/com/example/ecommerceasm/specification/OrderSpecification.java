package com.example.ecommerceasm.specification;

import com.example.ecommerceasm.entity.Order;
import com.example.ecommerceasm.entity.OrderDetail;
import com.example.ecommerceasm.entity.Product;
import com.example.ecommerceasm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;

public class OrderSpecification implements Specification<Order> {
    SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        switch (criteria.getOperator()) {
            case EQUALS:
                return criteriaBuilder.equal(
                        root.get(criteria.getKey()),
                        criteria.getValue());
            case LIKE:
                return criteriaBuilder.like(
                        root.get(criteria.getKey()),
                        "%" + criteria.getValue() + "%");
            case GREATER_THAN:
                return criteriaBuilder.greaterThan(
                        root.get(criteria.getKey()),
                        String.valueOf(criteria.getValue()));

            case GREATER_THAN_OR_EQUALS:
                if (root.get(criteria.getKey()).getJavaType() == LocalDateTime.class) {
                    return criteriaBuilder.greaterThanOrEqualTo(
                            root.get(criteria.getKey()), (LocalDateTime) criteria.getValue());
                } else {
                    return criteriaBuilder.greaterThanOrEqualTo(
                            root.get(criteria.getKey()),
                            String.valueOf(criteria.getValue()));
                }
            case LESS_THAN:
                return criteriaBuilder.lessThan(
                        root.get(criteria.getKey()),
                        String.valueOf(criteria.getValue()));
            case LESS_THAN_OR_EQUALS:
                if (root.get(criteria.getKey()).getJavaType() == LocalDateTime.class) {
                    return criteriaBuilder.lessThanOrEqualTo(
                            root.get(criteria.getKey()), (LocalDateTime) criteria.getValue());
                } else {
                    return criteriaBuilder.lessThanOrEqualTo(
                            root.get(criteria.getKey()),
                            String.valueOf(criteria.getValue()));
                }
            case JOIN_DETAIL_PRODUCT:
                Join<OrderDetail, Product> orderDetailProductJoin = root.join("orderDetails").join("product");
                Predicate predicate = criteriaBuilder.like(
                        orderDetailProductJoin.get(criteria.getKey()), "%" + criteria.getValue() + "%"
                );
                return predicate;

            case JOIN_USER:
                From<Order, User> orderOrderCustomerJoin = root.join("customer");
                Predicate predicateOrderCustomer = criteriaBuilder.like(
                        // hoặc tìm trong bảng customer bản ghi có name giống với giá trị
                        orderOrderCustomerJoin.get(criteria.getKey()), "%" + criteria.getValue() + "%"
                );
                return criteriaBuilder.like(
                        // hoặc tìm trong bảng customer bản ghi có name giống với giá trị
                        orderOrderCustomerJoin.get(criteria.getKey()), "%" + criteria.getValue() + "%"
                );
        }
        return null;
    }
}

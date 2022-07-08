package com.example.ecommerceasm.service;

import com.example.ecommerceasm.entity.Order;
import com.example.ecommerceasm.entity.Product;
import com.example.ecommerceasm.repository.OrderRepository;
import com.example.ecommerceasm.specification.OrderSearchBody;
import com.example.ecommerceasm.specification.ProductSpecification;
import com.example.ecommerceasm.specification.SearchCriteria;
import com.example.ecommerceasm.specification.SearchCriteriaOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public Optional<?> findById(Integer id) {
        return orderRepository.findById(id);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public void delete(Integer id) {
        orderRepository.deleteById(id);
    }

    public Map<String, Object> findAll(Pageable pageable) {
        Map<String, Object> responses = new HashMap<>();
        Page<Order> pageTotal = orderRepository.findAll(pageable);
        List<Order> list = pageTotal.getContent();
        responses.put("content", list);
        responses.put("currentPage", pageTotal.getNumber() + 1);
        responses.put("totalItems", pageTotal.getTotalElements());
        responses.put("totalPage", pageTotal.getTotalPages());
        return responses;
    }

    public Map<String, Object> findAllBy(OrderSearchBody orderSearchBody) {
        Specification specification = Specification.where(null);

        if (orderSearchBody.getProductId() != null && orderSearchBody.getProductId().length() > 0) {
            specification = specification.and(new ProductSpecification(new SearchCriteria("id", SearchCriteriaOperator.EQUALS, orderSearchBody.getProductId())));
        }
        if (orderSearchBody.getCateId() != null && orderSearchBody.getCateId().length() > 0) {
            specification = specification.and(new ProductSpecification(new SearchCriteria("cate_id", SearchCriteriaOperator.EQUALS, orderSearchBody.getCateId())));
        }
        if (orderSearchBody.getNameProduct() != null && orderSearchBody.getNameProduct().length() > 0) {
            specification = specification.and(new ProductSpecification(new SearchCriteria("name", SearchCriteriaOperator.EQUALS, orderSearchBody.getProductId())));
        }
//        if (searchBody.getStart() != null && searchBody.getStart().length() > 0){
//            LocalDateTime date = DateTimeHelper.convertStringToLocalDateTime(searchBody.getStart());
//            log.info("Check Start" + date);
////            log.info("check start convert date: " + date );
//            specification = specification.and(new OrderSpecification(new SearchCriteria("createdAt", GREATER_THAN_OR_EQUALS,date)));
//        }
//        if (searchBody.getEnd() != null && searchBody.getEnd().length() > 0){
//            LocalDateTime date = ConvertDateHelper.convertStringToLocalDateTime(searchBody.getEnd());
//            specification = specification.and(new OrderSpecification(new SearchCriteria("createdAt", LESS_THAN_OR_EQUALS,date)));
//        }
        return null;
    }

    public Order saveCart(Order order) {
        return orderRepository.save(order);
    }
}

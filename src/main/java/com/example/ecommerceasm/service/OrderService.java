package com.example.ecommerceasm.service;

import com.example.ecommerceasm.entity.Order;
import com.example.ecommerceasm.entity.Product;
import com.example.ecommerceasm.repository.OrderRepository;
import com.example.ecommerceasm.specification.*;
import com.example.ecommerceasm.util.DateTimeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.ecommerceasm.specification.SearchCriteriaOperator.*;
@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public Optional<?> findById(String id) {
        return orderRepository.findById(id);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public void delete(String id) {
        orderRepository.deleteById(id);
    }
    public Order saveCart(Order order) {
        return orderRepository.save(order);
    }

    public Map<String, Object> findAll(Pageable pageable) {
        Map<String, Object> responses = new HashMap<>();
        Page<Order> pageTotal = orderRepository.findAllBy(pageable);
        List<Order> list = pageTotal.getContent();
        responses.put("content", list);
        responses.put("currentPage", pageTotal.getNumber() + 1);
        responses.put("totalItems", pageTotal.getTotalElements());
        responses.put("totalPage", pageTotal.getTotalPages());
        return responses;
    }

    public Map<String, Object> findOrdersBy(OrderSearchBody orderSearchBody) {
        Specification specification = Specification.where(null);

        if (orderSearchBody.getOrderId() != null && orderSearchBody.getOrderId().length() > 0) {
            specification = specification.and(new OrderSpecification(new SearchCriteria("id", EQUALS, orderSearchBody.getOrderId())));
        }
        if (orderSearchBody.getProductId() != null && orderSearchBody.getProductId().length() > 0) {
            specification = specification.and(new OrderSpecification(new SearchCriteria("id", EQUALS, orderSearchBody.getProductId())));
        }
        if (orderSearchBody.getCateId() != null && orderSearchBody.getCateId().length() > 0) {
            specification = specification.and(new OrderSpecification(new SearchCriteria("id", JOIN_DETAIL_PRODUCT, orderSearchBody.getCateId())));
        }
        if (orderSearchBody.getNameProduct() != null && orderSearchBody.getNameProduct().length() > 0) {
            specification = specification.and(new OrderSpecification(new SearchCriteria("name", JOIN_DETAIL_PRODUCT, orderSearchBody.getNameProduct())));
        }
        if (orderSearchBody.getNameUser() != null && orderSearchBody.getNameUser().length() > 0) {
            specification = specification.and(new OrderSpecification(new SearchCriteria("fullName", JOIN_USER, orderSearchBody.getNameUser())));
        }
        if (orderSearchBody.getPhone() != null && orderSearchBody.getPhone().length() > 0) {
            specification = specification.and(new OrderSpecification(new SearchCriteria("phone", JOIN_USER, orderSearchBody.getPhone())));
        }
        if (orderSearchBody.getEmail() != null && orderSearchBody.getEmail().length() > 0) {
            specification = specification.and(new OrderSpecification(new SearchCriteria("email", JOIN_USER, orderSearchBody.getEmail())));
        }
        if (orderSearchBody.getStatus() != null && orderSearchBody.getStatus().length() > 0) {
            specification = specification.and(new OrderSpecification(new SearchCriteria("status", EQUALS, orderSearchBody.getStatus())));
        }
        if (orderSearchBody.getStart() != null && orderSearchBody.getStart().length() > 0){
            LocalDateTime date = DateTimeHelper.convertStringToLocalDateTime(orderSearchBody.getStart());
            specification = specification.and(new OrderSpecification(new SearchCriteria("createdAt", GREATER_THAN_OR_EQUALS,date)));
        }
        if (orderSearchBody.getEnd() != null && orderSearchBody.getEnd().length() > 0){
            LocalDateTime date = DateTimeHelper.convertStringToLocalDateTime(orderSearchBody.getEnd());
            specification = specification.and(new OrderSpecification(new SearchCriteria("createdAt", LESS_THAN_OR_EQUALS,date)));
        }

        Sort sort= Sort.by(Sort.Order.asc("id"));
        if (orderSearchBody.getSort() !=null && orderSearchBody.getSort().length() >0){
            if (orderSearchBody.getSort().contains("desc")){
                sort = Sort.by(Sort.Order.desc("id"));
            }
        }
        Pageable pageable = PageRequest.of(orderSearchBody.getPage() -1, orderSearchBody.getLimit(),sort );
        Page<Order> pageOrder = orderRepository.findAll(specification,pageable);
        List<Order> orderList = pageOrder.getContent();
        Map<String, Object> responses = new HashMap<>();
        responses.put("content",orderList);
        responses.put("currentPage",pageOrder.getNumber() + 1);
        responses.put("totalItems",pageOrder.getTotalElements());
        responses.put("totalPage",pageOrder.getTotalPages());
        return responses;
    }

//    public Page<Order> findAllBy(OrderSearchBody param) {
//        Specification<Order> specification = Specification.where(null);
//        if (param.getNameProduct() != null && param.getNameProduct().length() > 0) {
//            SearchCriteria searchCriteria
//                    = new SearchCriteria("keyword", JOIN, param.getNameProduct());
//            OrderSpecification filter = new OrderSpecification(searchCriteria);
//            specification = specification.and(filter);
//        }
//        if (param.getStatus() != null) {
//            SearchCriteria searchCriteria
//                    = new SearchCriteria("status", EQUALS, param.getStatus());
//            OrderSpecification filter = new OrderSpecification(searchCriteria);
//            specification = specification.and(filter);
//        }
//        if (param.getUserId() != null) {
//            SearchCriteria searchCriteria
//                    = new SearchCriteria("userId", EQUALS, param.getUserId());
//            OrderSpecification filter = new OrderSpecification(searchCriteria);
//            specification = specification.and(filter);
//        }
//        return orderRepository.findAll(
//                specification, PageRequest.of(param.getPage() - 1, param.getLimit()));
//    }
}

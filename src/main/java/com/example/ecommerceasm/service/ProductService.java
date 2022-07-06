package com.example.ecommerceasm.service;

import com.example.ecommerceasm.entity.Product;
import com.example.ecommerceasm.repository.ProductRepository;
import com.example.ecommerceasm.specification.ProductSpecification;
import com.example.ecommerceasm.specification.SearchBody;
import com.example.ecommerceasm.specification.SearchCriteria;
import com.example.ecommerceasm.specification.SearchCriteriaOperator;
import com.example.ecommerceasm.util.DateTimeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void delete(int id) {
        productRepository.deleteById(id);
    }

    public Map<String, Object> findAll(Pageable pageable) {
        Map<String, Object> responses = new HashMap<>();
        Page<Product> pageTotal = productRepository.findAll(pageable);
        List<Product> list = pageTotal.getContent();
        responses.put("content", list);
        responses.put("currentPage", pageTotal.getNumber() + 1);
        responses.put("totalItems", pageTotal.getTotalElements());
        responses.put("totalPage", pageTotal.getTotalPages());
        return responses;
    }

    public Map<String, Object> findAllBy(SearchBody searchBody) {
        Specification specification = Specification.where(null);

        if (searchBody.getProductId() != null && searchBody.getProductId().length() > 0) {
            specification = specification.and(new ProductSpecification(new SearchCriteria("id", SearchCriteriaOperator.EQUALS, searchBody.getProductId())));
        }
        if (searchBody.getCateId() != null && searchBody.getCateId().length() > 0) {
            specification = specification.and(new ProductSpecification(new SearchCriteria("cate_id", SearchCriteriaOperator.EQUALS, searchBody.getCateId())));
        }
        if (searchBody.getNameProduct() != null && searchBody.getNameProduct().length() > 0) {
            specification = specification.and(new ProductSpecification(new SearchCriteria("name", SearchCriteriaOperator.EQUALS, searchBody.getProductId())));
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

    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }
}

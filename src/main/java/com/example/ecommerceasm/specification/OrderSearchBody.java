package com.example.ecommerceasm.specification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchBody {
    private int page;
    private int limit;
    private String orderId;
    private String userId;
    private String productId;
    private String nameProduct;
    private String nameUser;
    private String email;
    private String phone;
    private String city;
    private String district;
    private String ward;
    private String cateId;
    private String sort;
    private String start;
    private String end;
    private String status;


    public static final class OrderSearchBodyBuilder {
        private int page;
        private int limit;
        private String orderId;
        private String userId;
        private String productId;
        private String nameProduct;
        private String nameUser;
        private String email;
        private String phone;
        private String city;
        private String district;
        private String ward;
        private String cateId;
        private String sort;
        private String start;
        private String end;
        private String status;

        private OrderSearchBodyBuilder() {
        }

        public static OrderSearchBodyBuilder anOrderSearchBody() {
            return new OrderSearchBodyBuilder();
        }

        public OrderSearchBodyBuilder withPage(int page) {
            this.page = page;
            return this;
        }

        public OrderSearchBodyBuilder withLimit(int limit) {
            this.limit = limit;
            return this;
        }

        public OrderSearchBodyBuilder withOrderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public OrderSearchBodyBuilder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public OrderSearchBodyBuilder withProductId(String productId) {
            this.productId = productId;
            return this;
        }

        public OrderSearchBodyBuilder withNameProduct(String nameProduct) {
            this.nameProduct = nameProduct;
            return this;
        }

        public OrderSearchBodyBuilder withNameUser(String nameUser) {
            this.nameUser = nameUser;
            return this;
        }

        public OrderSearchBodyBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public OrderSearchBodyBuilder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public OrderSearchBodyBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public OrderSearchBodyBuilder withDistrict(String district) {
            this.district = district;
            return this;
        }

        public OrderSearchBodyBuilder withWard(String ward) {
            this.ward = ward;
            return this;
        }

        public OrderSearchBodyBuilder withCateId(String cateId) {
            this.cateId = cateId;
            return this;
        }

        public OrderSearchBodyBuilder withSort(String sort) {
            this.sort = sort;
            return this;
        }

        public OrderSearchBodyBuilder withStart(String start) {
            this.start = start;
            return this;
        }

        public OrderSearchBodyBuilder withEnd(String end) {
            this.end = end;
            return this;
        }

        public OrderSearchBodyBuilder withStatus(String status) {
            this.status = status;
            return this;
        }

        public OrderSearchBody build() {
            OrderSearchBody orderSearchBody = new OrderSearchBody();
            orderSearchBody.setPage(page);
            orderSearchBody.setLimit(limit);
            orderSearchBody.setOrderId(orderId);
            orderSearchBody.setUserId(userId);
            orderSearchBody.setProductId(productId);
            orderSearchBody.setNameProduct(nameProduct);
            orderSearchBody.setNameUser(nameUser);
            orderSearchBody.setEmail(email);
            orderSearchBody.setPhone(phone);
            orderSearchBody.setCity(city);
            orderSearchBody.setDistrict(district);
            orderSearchBody.setWard(ward);
            orderSearchBody.setCateId(cateId);
            orderSearchBody.setSort(sort);
            orderSearchBody.setStart(start);
            orderSearchBody.setEnd(end);
            orderSearchBody.setStatus(status);
            return orderSearchBody;
        }
    }
}
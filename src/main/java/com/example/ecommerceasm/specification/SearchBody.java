package com.example.ecommerceasm.specification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchBody {
    private int page;
    private int limit;
    private String userId;
    private String productId;
    private String nameProduct;
    private String nameUser;
    private String phone;
    private String city;
    private String district;
    private String ward;
    private String cateId;
    private String sort;
    private String start;
    private String end;
    private String status;

    public static final class SearchBodyBuilder {
        private int page;
        private int limit;
        private String userId;
        private String productId;
        private String nameProduct;
        private String nameUser;
        private String phone;
        private String city;
        private String district;
        private String ward;
        private String cateId;
        private String sort;
        private String start;
        private String end;
        private String status;

        private SearchBodyBuilder() {
        }

        public static SearchBodyBuilder aSearchBody() {
            return new SearchBodyBuilder();
        }

        public SearchBodyBuilder withPage(int page) {
            this.page = page;
            return this;
        }

        public SearchBodyBuilder withLimit(int limit) {
            this.limit = limit;
            return this;
        }

        public SearchBodyBuilder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public SearchBodyBuilder withProductId(String productId) {
            this.productId = productId;
            return this;
        }

        public SearchBodyBuilder withNameProduct(String nameProduct) {
            this.nameProduct = nameProduct;
            return this;
        }

        public SearchBodyBuilder withNameUser(String nameUser) {
            this.nameUser = nameUser;
            return this;
        }

        public SearchBodyBuilder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public SearchBodyBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public SearchBodyBuilder withDistrict(String district) {
            this.district = district;
            return this;
        }

        public SearchBodyBuilder withWard(String ward) {
            this.ward = ward;
            return this;
        }

        public SearchBodyBuilder withCateId(String cateId) {
            this.cateId = cateId;
            return this;
        }

        public SearchBodyBuilder withSort(String sort) {
            this.sort = sort;
            return this;
        }

        public SearchBodyBuilder withStart(String start) {
            this.start = start;
            return this;
        }

        public SearchBodyBuilder withEnd(String end) {
            this.end = end;
            return this;
        }

        public SearchBodyBuilder withStatus(String status) {
            this.status = status;
            return this;
        }

        public SearchBody build() {
            SearchBody searchBody = new SearchBody();
            searchBody.setPage(page);
            searchBody.setLimit(limit);
            searchBody.setUserId(userId);
            searchBody.setProductId(productId);
            searchBody.setNameProduct(nameProduct);
            searchBody.setNameUser(nameUser);
            searchBody.setPhone(phone);
            searchBody.setCity(city);
            searchBody.setDistrict(district);
            searchBody.setWard(ward);
            searchBody.setCateId(cateId);
            searchBody.setSort(sort);
            searchBody.setStart(start);
            searchBody.setEnd(end);
            searchBody.setStatus(status);
            return searchBody;
        }
    }
}
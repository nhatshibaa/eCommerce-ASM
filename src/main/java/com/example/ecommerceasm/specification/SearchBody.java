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
    private String name;
    private String phone;
    private String cateId;
    private String sort;
    private String start;
    private String end;

    public static final class SearchBodyBuilder {
        private int page;
        private int limit;
        private String name;
        private String phone;
        private String cateId;
        private String sort;
        private String start;
        private String end;

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

        public SearchBodyBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public SearchBodyBuilder withPhone(String phone) {
            this.phone = phone;
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

        public SearchBody build() {
            SearchBody searchBody = new SearchBody();
            searchBody.setPage(page);
            searchBody.setLimit(limit);
            searchBody.setName(name);
            searchBody.setPhone(phone);
            searchBody.setCateId(cateId);
            searchBody.setSort(sort);
            searchBody.setStart(start);
            searchBody.setEnd(end);
            return searchBody;
        }
    }
}
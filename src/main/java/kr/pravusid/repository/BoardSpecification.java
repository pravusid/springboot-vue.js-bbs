package kr.pravusid.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import kr.pravusid.util.Pagination;
import org.springframework.data.jpa.domain.Specification;

import kr.pravusid.domain.Board;

public class BoardSpecification {

    public static Specification<Board> findByFilter(final Pagination pagination) {
        return (Root<Board> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            String keyword = pagination.getKeyword().toLowerCase();
            query.distinct(true);

            if (pagination.filterMatcher(Pagination.FilterType.USER)) {
                return cb.like(cb.lower(root.get(pagination.getFilter()).get("name")), "%" + keyword + "%");

            } else if (pagination.filterMatcher(Pagination.FilterType.COMMENT)) {
                return cb.like(cb.lower(root.join("comments").get("content")), "%" + keyword + "%");

            } else {
                return cb.like(cb.lower(root.get(pagination.getFilter())), "%" + keyword + "%");
            }
        };
    }

    public static Specification<Board> findByAll(final String keyword) {
        return (Root<Board> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            String keywordLCase = keyword.toLowerCase();
            query.distinct(true);
            return cb.or(
                    cb.like(cb.lower(root.get("title")), "%" + keywordLCase + "%"),
                    cb.like(cb.lower(root.get("user").get("name")), "%" + keywordLCase + "%"),
                    cb.like(cb.lower(root.get("content")), "%" + keywordLCase + "%")
            );
        };
    }

}

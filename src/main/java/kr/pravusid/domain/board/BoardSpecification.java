package kr.pravusid.domain.board;

import kr.pravusid.dto.Pagination;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class BoardSpecification {

    public static Specification<Board> findByFilter(final Pagination pagination) {
        return (Root<Board> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            String keyword = pagination.getKeyword().toLowerCase();
            query.distinct(true);

            if (pagination.filterMatcher(Pagination.FilterType.USER)) {
                return cb.like(cb.lower(root.get(pagination.getFilter()).get("name")), "%" + keyword + "%");

            } else if (pagination.filterMatcher(Pagination.FilterType.TITLE)) {
                return cb.like(cb.lower(root.get(pagination.getFilter())), "%" + keyword + "%");

            } else if (pagination.filterMatcher(Pagination.FilterType.CONTENT)) {
                return cb.like(cb.lower(root.get(pagination.getFilter())), "%" + keyword + "%");

            } else if (pagination.filterMatcher(Pagination.FilterType.COMMENTS)) {
                return cb.like(cb.lower(root.join(pagination.getFilter()).get("content")), "%" + keyword + "%");
            }
            return null;
        };
    }

    public static Specification<Board> findByAll(final String keyword) {
        return (Root<Board> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            String keywordLCase = keyword.toLowerCase();
            query.distinct(true);
            return cb.or(
                    cb.like(cb.lower(root.get(Pagination.FilterType.USER.toString().toLowerCase()).get("name")),
                            "%" + keywordLCase + "%"),
                    cb.like(cb.lower(root.get(Pagination.FilterType.TITLE.toString().toLowerCase())),
                            "%" + keywordLCase + "%"),
                    cb.like(cb.lower(root.get(Pagination.FilterType.CONTENT.toString().toLowerCase())),
                            "%" + keywordLCase + "%")
            );
        };
    }

}

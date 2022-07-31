package com.board.board.service.specification;

import com.board.board.model.Post;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;

public class PostSpecification {


    public static Specification<Post> defaultWhere() {
        return (root, query, cb) -> cb.isTrue(cb.literal(true));
    }

    public static Specification<Post> likePostName(String postName) {
        return (root, query, cb) -> cb.like(root.get("postName"), "%" + postName + "%");
    }

    public static Specification<Post> likePostContent(String postContent) {
        return (root, query, cb) -> cb.like(root.get("postContent"), "%" + postContent + "%");
    }

    public static Specification<Post> betweenUpdateTime(Timestamp startDatetime, Timestamp endDatetime) {
        return (root, query, cb) -> {
            if (startDatetime == null) {
                return cb.lessThanOrEqualTo(root.get("updateTime"), endDatetime);
            } else if (endDatetime == null) {
                return cb.greaterThanOrEqualTo(root.get("updateTime"), startDatetime);
            } else {
                return cb.between(root.get("updateTime"), startDatetime, endDatetime);
            }
        };
    }
}


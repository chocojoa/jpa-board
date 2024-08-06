package com.lollipop.jpaboard.user.specification;

import com.lollipop.jpaboard.user.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> usernameContains(String username) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("username"), "%" + username + "%");
    }

    public static Specification<User> emailContains(String email) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("email"), "%" + email + "%");
    }

}

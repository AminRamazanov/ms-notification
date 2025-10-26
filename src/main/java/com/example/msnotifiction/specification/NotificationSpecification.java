package com.example.msnotifiction.specification;

import com.example.msnotifiction.dao.entity.NotificationEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class NotificationSpecification {

    public static Specification<NotificationEntity> filter(Long userId, Long orderId, String email) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (userId != null) {
                predicate = cb.and(predicate, cb.equal(root.get("userId"), userId));
            }
            if (orderId != null) {
                predicate = cb.and(predicate, cb.equal(root.get("orderId"), orderId));
            }
            if (email != null && !email.isEmpty()) {
                predicate = cb.and(predicate, cb.equal(root.get("email"), email));
            }

            return predicate;
        };
    }
}

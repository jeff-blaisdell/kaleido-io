package io.kaleido.user.dao

import io.kaleido.user.entity.RoleEntity
import org.hibernate.Criteria
import org.hibernate.Session
import org.hibernate.criterion.DetachedCriteria

class RoleCriteriaBuilder {
    DetachedCriteria criteria

    RoleCriteriaBuilder() {
        criteria = DetachedCriteria.forClass(RoleEntity)
    }

    Criteria build(Session session) {
        return criteria.getExecutableCriteria(session)
    }
}

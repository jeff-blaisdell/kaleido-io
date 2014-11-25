package io.kaleido.user.dao

import io.kaleido.user.entity.UserEntity
import org.hibernate.Criteria
import org.hibernate.Session
import org.hibernate.criterion.DetachedCriteria
import org.hibernate.criterion.Restrictions

class UserCriteriaBuilder {

    DetachedCriteria criteria

    UserCriteriaBuilder() {
        criteria = DetachedCriteria.forClass(UserEntity)
    }

    UserCriteriaBuilder filterUserGuid(String userGuid) {
        criteria.add(Restrictions.eq('userGuid', userGuid))
        return this
    }

    UserCriteriaBuilder filterEmail(String email) {
        criteria.add(Restrictions.eq('email', email))
        return this
    }

    Criteria build(Session session) {
        return criteria.getExecutableCriteria(session)
    }
}

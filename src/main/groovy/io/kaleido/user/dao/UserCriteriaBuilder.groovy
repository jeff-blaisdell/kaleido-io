package io.kaleido.user.dao

import io.kaleido.user.entity.User
import org.hibernate.Criteria
import org.hibernate.Session
import org.hibernate.criterion.DetachedCriteria
import org.hibernate.criterion.Restrictions

class UserCriteriaBuilder {

    DetachedCriteria criteria

    UserCriteriaBuilder() {
        criteria = DetachedCriteria.forClass(User)
    }

    UserCriteriaBuilder filterUserGuid(String userGuid) {
        criteria.add(Restrictions.eq('userGuid', userGuid))
        return this
    }

    Criteria build(Session session) {
        return criteria.getExecutableCriteria(session)
    }
}

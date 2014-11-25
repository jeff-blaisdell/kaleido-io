package io.kaleido.auth.dao

import com.google.inject.Inject
import groovy.util.logging.Slf4j
import io.kaleido.user.dao.UserCriteriaBuilder
import io.kaleido.user.entity.UserEntity
import org.hibernate.Criteria
import org.hibernate.Session
import org.hibernate.SessionFactory

@Slf4j
class AuthDao {

    protected final SessionFactory sessionFactory

    @Inject
    AuthDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory
    }

    UserEntity findUserByEmail(String email) {
        UserEntity user = null
        Session session = sessionFactory.openSession()
        if (!session.transaction.active) {
            session.beginTransaction()
        }

        try {
            UserCriteriaBuilder criteriaBuilder = new UserCriteriaBuilder()
            criteriaBuilder.filterEmail(email)
            Criteria criteria = criteriaBuilder.build(session)
            user = (UserEntity) criteria.uniqueResult()
            session.transaction.commit()
        } catch (Throwable t) {
            session.transaction.rollback()
            throw t
        }

        return user
    }

}

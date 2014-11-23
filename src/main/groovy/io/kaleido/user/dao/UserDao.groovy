package io.kaleido.user.dao

import com.google.inject.Inject
import groovy.util.logging.Slf4j
import io.kaleido.common.dao.CommonDao
import io.kaleido.user.entity.User
import org.hibernate.Criteria
import org.hibernate.Session
import org.hibernate.SessionFactory
import ratpack.exec.ExecControl
import rx.Observable

@Slf4j
class UserDao extends CommonDao {

    @Inject
    UserDao(ExecControl execControl, SessionFactory sessionFactory) {
        super(execControl, sessionFactory)
    }

    Observable<User> findUser(String userGuid) {

        Session session = openSession()

        block(session) {
            UserCriteriaBuilder criteriaBuilder = new UserCriteriaBuilder()
            criteriaBuilder.filterUserGuid(userGuid)
            Criteria criteria = criteriaBuilder.build(session)
            return (User) criteria.uniqueResult()
        }
    }

    Observable<List<User>> listUsers() {

        Session session = openSession()

        block(session) {
            UserCriteriaBuilder criteriaBuilder = new UserCriteriaBuilder()
            Criteria criteria = criteriaBuilder.build(session)
            return (List<User>) criteria.list()
        }
    }

}

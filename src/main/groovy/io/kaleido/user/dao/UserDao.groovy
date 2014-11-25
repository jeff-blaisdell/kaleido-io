package io.kaleido.user.dao

import com.google.inject.Inject
import groovy.util.logging.Slf4j
import io.kaleido.common.dao.CommonDao
import io.kaleido.user.entity.UserEntity
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

    Observable<List<UserEntity>> listUsers() {
        Session session = openSession()
        UserCriteriaBuilder criteriaBuilder = new UserCriteriaBuilder()
        Criteria criteria = criteriaBuilder.build(session)

        block(session) {
            return (List<UserEntity>) criteria.list()
        }
    }

    Observable<UserEntity> findUser(String userGuid) {
        Session session = openSession()
        UserCriteriaBuilder criteriaBuilder = new UserCriteriaBuilder()
        criteriaBuilder.filterUserGuid(userGuid)
        Criteria criteria = criteriaBuilder.build(session)

        block(session) {
            return (UserEntity) criteria.uniqueResult()
        }
    }

    Observable<UserEntity> findUserByEmail(String email) {
        Session session = openSession()
        UserCriteriaBuilder criteriaBuilder = new UserCriteriaBuilder()
        criteriaBuilder.filterEmail(email)
        Criteria criteria = criteriaBuilder.build(session)

        block(session) {
            return (UserEntity) criteria.uniqueResult()
        }
    }

    Observable<UserEntity> createUser(UserEntity user) {
        Session session = openSession()

        block(session) {
            session.saveOrUpdate(user)
            return user
        }
    }

    Observable<UserEntity> updateUser(UserEntity user) {
        Session session = openSession()

        block(session) {
            session.saveOrUpdate(user)
            return user
        }
    }

    Observable<UserEntity> deleteUser(UserEntity user) {
        Session session = openSession()

        block(session) {
            session.delete(user)
            return user
        }
    }

}

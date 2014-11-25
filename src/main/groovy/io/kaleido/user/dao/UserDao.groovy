package io.kaleido.user.dao

import com.google.inject.Inject
import groovy.util.logging.Slf4j
import io.kaleido.common.api.AppConfig
import io.kaleido.common.dao.CommonDao
import io.kaleido.user.entity.User
import org.hibernate.Criteria
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.joda.time.DateTimeZone
import org.joda.time.LocalDateTime
import org.mindrot.jbcrypt.BCrypt
import ratpack.exec.ExecControl
import rx.Observable

@Slf4j
class UserDao extends CommonDao {

    private final AppConfig appConfig

    @Inject
    UserDao(ExecControl execControl, SessionFactory sessionFactory, AppConfig appConfig) {
        super(execControl, sessionFactory)
        this.appConfig = appConfig
    }

    Observable<List<User>> listUsers() {

        Session session = openSession()

        block(session) {
            UserCriteriaBuilder criteriaBuilder = new UserCriteriaBuilder()
            Criteria criteria = criteriaBuilder.build(session)
            return (List<User>) criteria.list()
        }
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

    Observable<User> createUser(User user) {
        Session session = openSession()

        block(session) {
            Integer rounds = appConfig.bcryptRounds
            String hash = BCrypt.hashpw(user.password, BCrypt.gensalt(rounds));
            user.with {
                password = hash
                LocalDateTime now = LocalDateTime.now(DateTimeZone.UTC)
                createdDate = now
                lastUpdateDate = now
            }

            session.saveOrUpdate(user)
            return user
        }
    }

    Observable<User> updateUser(User user) {
        Session session = openSession()

        block(session) {
            User dbUser = session.get('User', user.userId)

            dbUser.with {
                LocalDateTime now = LocalDateTime.now(DateTimeZone.UTC)
                firstName = user.firstName
                lastName = user.lastName
                birthDate = user.birthDate
                email = user.email
                lastUpdateDate = now
            }

            session.saveOrUpdate(dbUser)
            return dbUser
        }
    }

    Observable<User> deleteUser(User user) {
        Session session = openSession()

        block(session) {
            session.delete(user)
            return user
        }
    }

}

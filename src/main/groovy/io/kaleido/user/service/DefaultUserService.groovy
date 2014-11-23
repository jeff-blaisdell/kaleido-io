package io.kaleido.user.service

import com.google.inject.Inject
import groovy.util.logging.Slf4j
import io.kaleido.user.dao.UserDao
import io.kaleido.user.entity.User
import rx.Observable

@Slf4j
class DefaultUserService implements UserService {

    private final UserDao userDao

    @Inject
    DefaultUserService(UserDao userDao) {
        this.userDao = userDao
    }

    Observable<User> findUser(String userGuid) {
        return userDao.findUser(userGuid)
    }

    Observable<List<User>> listUsers() {
        return userDao.listUsers()
    }
}

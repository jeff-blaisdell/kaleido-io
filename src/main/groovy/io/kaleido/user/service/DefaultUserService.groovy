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

    @Override
    Observable<List<User>> listUsers() {
        return userDao.listUsers()
    }

    @Override
    Observable<User> findUser(String userGuid) {
        return userDao.findUser(userGuid)
    }

    @Override
    Observable<User> createUser(User user) {
        return userDao.createUser(user)
    }

    @Override
    Observable<User> updateUser(User user) {
        return userDao.updateUser(user)
    }

    @Override
    Observable<User> deleteUser(User user) {
        return userDao.deleteUser(user)
    }
}

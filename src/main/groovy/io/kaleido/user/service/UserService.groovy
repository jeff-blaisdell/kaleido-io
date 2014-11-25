package io.kaleido.user.service

import io.kaleido.user.entity.User
import rx.Observable

interface UserService {

    Observable<List<User>> listUsers()

    Observable<User> findUser(String userGuid)

    Observable<User> createUser(User user)

    Observable<User> updateUser(User user)

    Observable<User> deleteUser(User user)

}

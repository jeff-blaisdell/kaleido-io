package io.kaleido.user.service

import io.kaleido.user.api.User
import io.kaleido.user.entity.UserEntity
import rx.Observable

interface UserService {

    Observable<List<UserEntity>> listUsers()

    Observable<UserEntity> findUser(String userGuid)

    Observable<UserEntity> createUser(User user)

    Observable<UserEntity> updateUser(User user)

    Observable<UserEntity> updateUserPassword(User user)

    Observable<UserEntity> deleteUser(User user)

}

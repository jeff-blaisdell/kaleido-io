package io.kaleido.user.service

import io.kaleido.user.entity.User
import rx.Observable

interface UserService {

    Observable<User> findUser(String userGuid)

    Observable<List<User>> listUsers()

}

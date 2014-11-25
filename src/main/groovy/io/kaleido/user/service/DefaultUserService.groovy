package io.kaleido.user.service

import com.google.inject.Inject
import groovy.util.logging.Slf4j
import io.kaleido.user.api.RoleType
import io.kaleido.common.config.AppConfig
import io.kaleido.user.api.User
import io.kaleido.user.dao.RoleDao
import io.kaleido.user.entity.RoleEntity
import io.kaleido.user.entity.UserEntity
import io.kaleido.user.dao.UserDao
import io.kaleido.user.entity.UserRoleEntity
import org.joda.time.DateTimeZone
import org.joda.time.LocalDateTime
import org.mindrot.jbcrypt.BCrypt
import rx.Observable
import rx.functions.Func1

@Slf4j
class DefaultUserService implements UserService {

    private final UserDao userDao
    private final RoleDao roleDao
    private final AppConfig appConfig

    @Inject
    DefaultUserService(UserDao userDao, RoleDao roleDao, AppConfig appConfig) {
        this.userDao = userDao
        this.roleDao = roleDao
        this.appConfig = appConfig
    }

    @Override
    Observable<List<UserEntity>> listUsers() {
        return userDao.listUsers()
    }

    @Override
    Observable<UserEntity> findUser(String userGuid) {
        return userDao.findUser(userGuid)
    }

    @Override
    Observable<UserEntity> createUser(User user) {
        Observable<List<RoleEntity>> rolesObservable = listRoles()
        LocalDateTime now = LocalDateTime.now(DateTimeZone.UTC)

        Func1 createUserFunc = new Func1<List<RoleEntity>, Observable<UserEntity>>() {
            @Override
            Observable<UserEntity> call(List<RoleEntity> roles) {
                user.roles = [RoleType.ROLE_REGISTERED_USER]
                UserEntity userEntity = new UserEntity()

                userEntity.with {
                    userGuid = UUID.randomUUID().toString()
                    email = user.email
                    firstName = user.firstName
                    lastName = user.lastName
                    birthDate = user.birthDate
                    password = hashpw(user.password)
                    createdDate = now
                    lastUpdateDate = now
                    userRoles = user.roles?.collect { RoleType roleType ->
                        RoleEntity role = roles.find { it.roleName == roleType.name() }
                        return new UserRoleEntity(
                            user: userEntity,
                            role: role
                        )
                    }
                }
                return userDao.createUser(userEntity)
            }
        }

        return rolesObservable.flatMap(createUserFunc)
    }

    @Override
    Observable<UserEntity> updateUser(User user) {
        Observable<UserEntity> userObservable = findUser(user.userGuid)
        LocalDateTime now = LocalDateTime.now(DateTimeZone.UTC)

        Func1 updateUserFunc = new Func1<UserEntity, Observable<UserEntity>>() {
            @Override
            Observable<UserEntity> call(UserEntity userEntity) {
                userEntity.with {
                    firstName = user.firstName
                    lastName = user.lastName
                    birthDate = user.birthDate
                    lastUpdateDate = now
                }

                return userDao.createUser(userEntity)
            }
        }

        return userObservable.flatMap(updateUserFunc)
    }

    @Override
    Observable<UserEntity> updateUserPassword(User user) {
        Observable<UserEntity> userObservable = findUser(user.userGuid)

        Func1 updateUserFunc = new Func1<UserEntity, Observable<UserEntity>>() {
            @Override
            Observable<UserEntity> call(UserEntity userEntity) {
                userEntity.with {
                    password = hashpw(user.password)
                    lastUpdateDate = LocalDateTime.now(DateTimeZone.UTC)
                }

                return userDao.updateUser(userEntity)
            }
        }

        return userObservable.flatMap(updateUserFunc)
    }

    @Override
    Observable<UserEntity> deleteUser(User user) {
        UserEntity userEntity = new UserEntity(
            userId: user.userId
        )
        return userDao.deleteUser(userEntity)
    }


    private Observable<List<RoleEntity>> listRoles() {
        return roleDao.listRoles()
    }

    private String hashpw(String password) {
        Integer rounds = appConfig.auth.bcryptRounds
        return BCrypt.hashpw(password, BCrypt.gensalt(rounds))
    }

}

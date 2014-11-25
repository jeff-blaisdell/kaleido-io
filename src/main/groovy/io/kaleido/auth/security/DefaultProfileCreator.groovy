package io.kaleido.auth.security

import com.google.inject.Inject
import groovy.util.logging.Slf4j
import io.kaleido.auth.dao.AuthDao
import io.kaleido.user.api.RoleType
import io.kaleido.user.entity.UserEntity
import org.pac4j.core.profile.CommonProfile
import org.pac4j.http.profile.HttpProfile
import org.pac4j.http.profile.ProfileCreator

@Slf4j
class DefaultProfileCreator implements ProfileCreator {

    private final AuthDao authDao

    @Inject
    DefaultProfileCreator(AuthDao authDao) {
        this.authDao = authDao
    }

    @Override
    HttpProfile create(String email) {
        UserEntity user = authDao.findUserByEmail(email)

        DefaultProfile profile = new DefaultProfile()
        profile.setId(user.email)
        profile.addAttribute(CommonProfile.USERNAME, user.email)
        profile.addAttribute(DefaultProfile.USER_GUID, user.userGuid)
        user.roles.each { RoleType roleType ->
            profile.addRole(roleType.name())
        }

        return profile
    }
}

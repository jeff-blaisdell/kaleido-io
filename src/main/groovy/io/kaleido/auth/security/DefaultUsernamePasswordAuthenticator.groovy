package io.kaleido.auth.security

import com.google.inject.Inject
import groovy.util.logging.Slf4j
import io.kaleido.auth.dao.AuthDao
import io.kaleido.user.entity.UserEntity
import org.mindrot.jbcrypt.BCrypt
import org.pac4j.core.exception.CredentialsException
import org.pac4j.http.credentials.UsernamePasswordAuthenticator
import org.pac4j.http.credentials.UsernamePasswordCredentials

@Slf4j
class DefaultUsernamePasswordAuthenticator implements UsernamePasswordAuthenticator {

    private final AuthDao authDao

    @Inject
    DefaultUsernamePasswordAuthenticator(AuthDao authDao) {
        this.authDao = authDao
    }

    @Override
    void validate(UsernamePasswordCredentials credentials) {
        String email = credentials?.username
        String password = credentials?.password

        log.debug("Authenticating [username: ${email}]")

        if (!email) {
            throw new CredentialsException('Email is required.')
        }

        if (!password) {
            throw new CredentialsException('Password is required.')
        }

        UserEntity user = authDao.findUserByEmail(email)

        if (!user) {
            throw new CredentialsException('Account not found.')
        }

        log.debug("Found user account [username: ${user.email}]")

        if (!password || !user.password) {
            log.debug("User account is missing a password field.  Cannot authenticate. [username: ${user.email}]")
            throw new CredentialsException('Invalid credentials.')
        }

        if (!BCrypt.checkpw(password, user.password)) {
            log.debug("User account credentials are invalid. [username: ${user.email}]")
            throw new CredentialsException('Invalid credentials.')
        }
    }
}

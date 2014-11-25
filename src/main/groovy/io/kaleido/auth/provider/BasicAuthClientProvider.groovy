package io.kaleido.auth.provider

import com.google.inject.Inject
import com.google.inject.Provider
import io.kaleido.auth.security.CustomBasicAuthClient
import io.kaleido.common.config.AppConfig
import io.kaleido.common.config.AuthConfig
import org.pac4j.http.client.BasicAuthClient
import org.pac4j.http.credentials.UsernamePasswordAuthenticator
import org.pac4j.http.profile.ProfileCreator

class BasicAuthClientProvider implements Provider<BasicAuthClient> {

    private final UsernamePasswordAuthenticator usernamePasswordAuthenticator
    private final ProfileCreator profileCreator
    private final AuthConfig authConfig

    @Inject
    BasicAuthClientProvider(AppConfig appConfig,
                       UsernamePasswordAuthenticator usernamePasswordAuthenticator,
                       ProfileCreator profileCreator) {
        this.authConfig = appConfig.auth
        this.usernamePasswordAuthenticator = usernamePasswordAuthenticator
        this.profileCreator = profileCreator
    }

    BasicAuthClient get() {
        return new CustomBasicAuthClient(authConfig.loginUrl, this.usernamePasswordAuthenticator, this.profileCreator)
    }
}

package io.kaleido.auth.provider

import com.google.inject.Inject
import com.google.inject.Provider
import groovy.util.logging.Slf4j
import ratpack.pac4j.Authorizer
import ratpack.pac4j.internal.Pac4jAuthenticationHandler

@Slf4j
class Pac4jAuthenticationHandlerProvider implements Provider<Pac4jAuthenticationHandler> {

    private static final String REDIRECT_CLIENT_NAME = 'BasicAuthClient'
    private Authorizer authorizer

    @Inject
    Pac4jAuthenticationHandlerProvider(Authorizer authorizer) {
        this.authorizer = authorizer
    }

    @Override
    Pac4jAuthenticationHandler get() {
        return new Pac4jAuthenticationHandler(REDIRECT_CLIENT_NAME, authorizer)
    }
}

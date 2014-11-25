package io.kaleido.auth.handler

import com.google.inject.Inject
import groovy.util.logging.Slf4j
import io.kaleido.common.config.AppConfig
import io.kaleido.common.config.AuthConfig
import ratpack.func.Action
import ratpack.handling.Chain
import ratpack.handling.Handler
import ratpack.handling.Handlers
import ratpack.launch.HandlerFactory
import ratpack.launch.LaunchConfig
import ratpack.pac4j.internal.Pac4jAuthenticationHandler
import ratpack.pac4j.internal.Pac4jCallbackHandler
import ratpack.pac4j.internal.Pac4jClientsHandler

@Slf4j
class AuthHandlerFactory implements HandlerFactory {

    private final AuthConfig authConfig
    private final Pac4jClientsHandler pac4jClientsHandler
    private final Pac4jCallbackHandler pac4jCallbackHandler
    private final Pac4jAuthenticationHandler pac4jAuthenticationHandler

    @Inject
    AuthHandlerFactory(AppConfig appConfig,
                       Pac4jClientsHandler pac4jClientsHandler,
                       Pac4jCallbackHandler pac4jCallbackHandler,
                       Pac4jAuthenticationHandler pac4jAuthenticationHandler) {
        this.authConfig = appConfig.auth
        this.pac4jClientsHandler = pac4jClientsHandler
        this.pac4jCallbackHandler = pac4jCallbackHandler
        this.pac4jAuthenticationHandler = pac4jAuthenticationHandler
    }

    @Override
    Handler create(LaunchConfig launchConfig) throws Exception {
        return Handlers.chain(launchConfig, new Action<Chain>() {
            public void execute(Chain chain) {
                chain.handler(pac4jClientsHandler)
                chain.handler(authConfig.authCallbackUrl, pac4jCallbackHandler)
                chain.handler(pac4jAuthenticationHandler)
            }
        })
    }
}

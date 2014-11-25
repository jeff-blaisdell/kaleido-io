package io.kaleido.auth.provider

import com.google.inject.Inject
import com.google.inject.Provider
import groovy.util.logging.Slf4j
import io.kaleido.common.config.AppConfig
import io.kaleido.common.config.AuthConfig
import org.pac4j.core.client.Client
import org.pac4j.http.client.BasicAuthClient
import ratpack.pac4j.internal.Pac4jClientsHandler

@Slf4j
class Pac4JClientsHandlerProvider implements Provider<Pac4jClientsHandler> {

    private final AuthConfig authConfig
    private final BasicAuthClient basicAuthClient

    @Inject
    Pac4JClientsHandlerProvider(AppConfig appConfig, BasicAuthClient basicAuthClient) {
        this.authConfig = appConfig.auth
        this.basicAuthClient = basicAuthClient
    }

    Pac4jClientsHandler get() {
        List<Client> clients = [basicAuthClient]
        log.debug("Registering authentication clients ${clients*.name}")
        return new Pac4jClientsHandler(authConfig.authCallbackUrl, clients)
    }
}

package io.kaleido.auth.provider

import com.google.inject.Inject
import com.google.inject.Provider
import groovy.util.logging.Slf4j
import io.kaleido.auth.security.DefaultProfile
import org.pac4j.core.client.Client
import org.pac4j.core.client.Clients
import org.pac4j.core.context.WebContext
import org.pac4j.core.credentials.Credentials
import org.pac4j.core.exception.TechnicalException
import org.pac4j.core.profile.UserProfile
import ratpack.handling.Context
import ratpack.http.Request
import ratpack.pac4j.internal.Pac4jCallbackHandler
import ratpack.session.store.SessionStorage
import ratpack.util.Types

import java.util.function.BiConsumer
import java.util.function.BiFunction

import static ratpack.jackson.Jackson.json
import static ratpack.pac4j.internal.SessionConstants.SAVED_URI
import static ratpack.pac4j.internal.SessionConstants.USER_PROFILE

@Slf4j
class Pac4jCallbackHandlerProvider implements Provider<Pac4jCallbackHandler> {

    private static final String DEFAULT_REDIRECT_URI = "/"

    BiFunction<? super Context, ? super WebContext, ? extends Client<Credentials, UserProfile>> lookupClient =
    { Context context, WebContext webContext ->
        Request request = context.request
        Clients clients = request.get(Clients)
        log.debug("Searching registered authentication clients [clients: ${clients.findAllClients()*.name}]")
        return Types.cast(clients.findClient(webContext))
    }

    BiConsumer<? super Context, ? super UserProfile> onSuccess =
    { Context context, UserProfile profile ->
        Request request = context.request
        SessionStorage sessionStorage = request.get(SessionStorage)
        if (profile != null) {
            sessionStorage.put(USER_PROFILE, profile)
        }
        String originalUri = (String) sessionStorage.remove(SAVED_URI)
        if (originalUri == null) {
            originalUri = DEFAULT_REDIRECT_URI
        }

        // Sniff AJAX requests and prevent redirect.
        if (request.headers.get('X-Requested-With') == 'XMLHttpRequest') {
            context.render(json([
                'userGuid': profile.getAttribute(DefaultProfile.USER_GUID)
            ]))
        } else {
            context.redirect(originalUri)
        }
    }

    BiConsumer<? super Context, ? super Throwable> onError =
    { Context ctx, Throwable ex ->
        throw new TechnicalException("Failed to get user profile", ex)
    }

    @Inject
    Pac4jCallbackHandlerProvider() {

    }

    Pac4jCallbackHandler get() {
        return new Pac4jCallbackHandler(lookupClient, onSuccess, onError)
    }
}

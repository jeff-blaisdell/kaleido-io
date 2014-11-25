package io.kaleido.auth.security

import org.pac4j.core.client.RedirectAction
import org.pac4j.core.context.WebContext
import org.pac4j.http.client.BasicAuthClient
import org.pac4j.http.credentials.UsernamePasswordAuthenticator
import org.pac4j.http.profile.ProfileCreator

/**
 * An extension of the pac4j BasicAuthClient which allows for redirection to a
 * custom login page rather than the default browser basic auth alert.
 */
class CustomBasicAuthClient extends BasicAuthClient {

    private static final String CLIENT_NAME = 'BasicAuthClient'
    private String loginUrl

    CustomBasicAuthClient(String loginUrl,
                    UsernamePasswordAuthenticator usernamePasswordAuthenticator,
                    ProfileCreator profileCreator) {
        super(usernamePasswordAuthenticator, profileCreator)
        this.loginUrl = loginUrl
    }

    @Override
    protected RedirectAction retrieveRedirectAction(final WebContext context) {
        return RedirectAction.redirect(this.loginUrl);
    }

    @Override
    public String getName() {
        return CLIENT_NAME;
    }
}

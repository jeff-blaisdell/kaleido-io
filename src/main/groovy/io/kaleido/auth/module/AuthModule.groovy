package io.kaleido.auth.module

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import com.google.inject.TypeLiteral
import io.kaleido.auth.dao.AuthDao
import io.kaleido.auth.handler.AuthHandlerFactory
import io.kaleido.auth.provider.BasicAuthClientProvider
import io.kaleido.auth.provider.Pac4JClientsHandlerProvider
import io.kaleido.auth.provider.Pac4jAuthenticationHandlerProvider
import io.kaleido.auth.provider.Pac4jCallbackHandlerProvider
import io.kaleido.auth.security.DefaultAuthorizer
import io.kaleido.auth.security.DefaultProfile
import io.kaleido.auth.security.DefaultProfileCreator
import io.kaleido.auth.security.DefaultUsernamePasswordAuthenticator
import io.kaleido.common.module.AppConfigModule
import io.kaleido.common.module.HibernateModule
import org.pac4j.core.client.Client
import org.pac4j.http.client.BasicAuthClient
import org.pac4j.http.credentials.UsernamePasswordAuthenticator
import org.pac4j.http.credentials.UsernamePasswordCredentials
import org.pac4j.http.profile.HttpProfile
import org.pac4j.http.profile.ProfileCreator
import ratpack.pac4j.Authorizer
import ratpack.pac4j.internal.Pac4jAuthenticationHandler
import ratpack.pac4j.internal.Pac4jCallbackHandler
import ratpack.pac4j.internal.Pac4jClientsHandler

class AuthModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new AppConfigModule())
        install(new HibernateModule())
        bind(AuthDao)
        bind(ProfileCreator).to(DefaultProfileCreator).in(Scopes.SINGLETON)
        bind(UsernamePasswordAuthenticator).to(DefaultUsernamePasswordAuthenticator).in(Scopes.SINGLETON)
        bind(BasicAuthClient).toProvider(BasicAuthClientProvider).in(Scopes.SINGLETON)
        bind(Pac4jCallbackHandler).toProvider(Pac4jCallbackHandlerProvider).in(Scopes.SINGLETON)
        bind(Pac4jClientsHandler).toProvider(Pac4JClientsHandlerProvider).in(Scopes.SINGLETON)
        bind(Pac4jAuthenticationHandler).toProvider(Pac4jAuthenticationHandlerProvider).in(Scopes.SINGLETON)
        bind(HttpProfile).to(DefaultProfile)
        bind(new TypeLiteral<Client<UsernamePasswordCredentials, HttpProfile>>() {}).to(BasicAuthClient)
        bind(Authorizer).to(DefaultAuthorizer)
        bind(AuthHandlerFactory)
    }

}

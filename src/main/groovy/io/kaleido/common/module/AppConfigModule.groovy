package io.kaleido.common.module

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import groovy.transform.CompileStatic
import io.kaleido.common.api.AppConfig
import io.kaleido.common.provider.AppConfigProvider

@CompileStatic
class AppConfigModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AppConfig).toProvider(AppConfigProvider).in(Scopes.SINGLETON)
    }

}


package io.kaleido.common.module

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.AbstractModule
import com.google.inject.Scopes
import groovy.transform.CompileStatic
import io.kaleido.common.provider.ObjectMapperProvider

@CompileStatic
class GlobalJacksonConfigModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ObjectMapper.class).toProvider(ObjectMapperProvider.class).in(Scopes.SINGLETON)
    }
}


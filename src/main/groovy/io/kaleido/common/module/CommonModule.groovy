package io.kaleido.common.module

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import io.kaleido.common.handler.IndexHandler

class CommonModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IndexHandler).in(Scopes.SINGLETON)
    }

}


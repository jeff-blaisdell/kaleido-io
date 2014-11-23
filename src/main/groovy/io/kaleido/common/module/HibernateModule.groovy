package io.kaleido.common.module

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import io.kaleido.common.api.HibernateEntities
import io.kaleido.common.provider.HibernateConfigurationProvider
import io.kaleido.common.provider.HibernateEntitiesProvider
import io.kaleido.common.provider.HibernateSessionFactoryProvider
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration

class HibernateModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Configuration.class).toProvider(HibernateConfigurationProvider).in(Scopes.SINGLETON)
        bind(SessionFactory.class).toProvider(HibernateSessionFactoryProvider).in(Scopes.SINGLETON)
        bind(HibernateEntities.class).toProvider(HibernateEntitiesProvider).in(Scopes.SINGLETON)
    }

}

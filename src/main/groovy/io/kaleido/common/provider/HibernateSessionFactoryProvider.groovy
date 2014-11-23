package io.kaleido.common.provider

import com.google.inject.Inject
import com.google.inject.Provider
import org.hibernate.SessionFactory
import org.hibernate.boot.registry.StandardServiceRegistry
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.cfg.Configuration

class HibernateSessionFactoryProvider implements Provider<SessionFactory> {

    private final Configuration configuration

    @Inject
    HibernateSessionFactoryProvider(Configuration configuration) {
        this.configuration = configuration
    }

    @Override
    SessionFactory get() {
        StandardServiceRegistry registryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.properties).build()
        return configuration.buildSessionFactory(registryBuilder)
    }

}

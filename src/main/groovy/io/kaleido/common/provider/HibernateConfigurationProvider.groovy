package io.kaleido.common.provider

import com.google.inject.Inject
import com.google.inject.Provider
import groovy.util.logging.Slf4j
import io.kaleido.common.config.AppConfig
import io.kaleido.common.config.DatabaseConfig
import io.kaleido.common.config.HibernateEntitiesConfig
import org.hibernate.cfg.AvailableSettings
import org.hibernate.cfg.Configuration

@Slf4j
class HibernateConfigurationProvider implements Provider<Configuration> {

    private final DatabaseConfig database
    private final HibernateEntitiesConfig hibernateEntities

    @Inject
    HibernateConfigurationProvider(AppConfig appConfig, HibernateEntitiesConfig hibernateEntities) {
        this.database = appConfig.database
        this.hibernateEntities = hibernateEntities

        log.debug("Classes to annotate [${this.hibernateEntities.entities}]")
    }

    @Override
    Configuration get() {
        Configuration configuration = new Configuration()
            .setProperty(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, 'managed')
            .setProperty(AvailableSettings.USE_SQL_COMMENTS, 'false')
            .setProperty(AvailableSettings.USE_GET_GENERATED_KEYS, 'true')
            .setProperty(AvailableSettings.GENERATE_STATISTICS, 'true')
            .setProperty(AvailableSettings.USE_REFLECTION_OPTIMIZER, 'true')
            .setProperty(AvailableSettings.ORDER_UPDATES, 'true')
            .setProperty(AvailableSettings.ORDER_INSERTS, 'true')
            .setProperty(AvailableSettings.USE_NEW_ID_GENERATOR_MAPPINGS, 'true')
            .setProperty(AvailableSettings.SHOW_SQL, 'true')
            .setProperty(AvailableSettings.DIALECT, database.properties.get('hibernate.dialect'))
            .setProperty(AvailableSettings.URL, database.url)
            .setProperty(AvailableSettings.USER, database.user)
            .setProperty(AvailableSettings.PASS, database.password)
            .setProperty(AvailableSettings.DRIVER, database.driverClass)
            .setProperty("jadira.usertype.autoRegisterUserTypes", "true")

        this.hibernateEntities.entities?.each { Class clazz ->
            log.debug("Adding class to annotations [${clazz}]")
            configuration.addAnnotatedClass(clazz)
        }

        return configuration
    }

}

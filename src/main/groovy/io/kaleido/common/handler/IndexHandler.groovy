package io.kaleido.common.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Inject
import groovy.util.logging.Slf4j
import io.kaleido.common.config.SecurePathConfig
import io.kaleido.common.config.SecurePathDetailsConfig
import io.kaleido.user.api.RoleType
import io.kaleido.common.config.AppConfig
import ratpack.handling.Context
import ratpack.handling.Handler
import static ratpack.groovy.Groovy.groovyTemplate

@Slf4j
class IndexHandler implements Handler {

    private Map<String, Set<SecurePathDetailsConfig>> securedRoutes
    private final ObjectMapper objectMapper

    @Inject
    IndexHandler(AppConfig appConfig, ObjectMapper objectMapper) {
        this.securedRoutes = appConfig?.auth?.getRouteSecurityMappings()
        this.objectMapper = objectMapper
    }

    @Override
    public void handle(final Context context) throws Exception {
        log.debug('Entering Index handler')
        Map<String, Object> model = [
            contextPath: context.launchConfig.getPublicAddress().toString(),
            securedRoutes: objectMapper.writeValueAsString(securedRoutes)
        ]
        context.render(groovyTemplate(model, 'index.html'))
    }

}

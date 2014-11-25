package io.kaleido.auth.security

import com.google.inject.Inject
import groovy.util.logging.Slf4j
import io.kaleido.common.config.AppConfig
import io.kaleido.common.config.SecurePathDetailsConfig
import io.kaleido.user.api.RoleType
import org.pac4j.core.exception.RequiresHttpAction
import org.pac4j.core.profile.UserProfile
import ratpack.handling.Context
import ratpack.http.HttpMethod
import ratpack.pac4j.AbstractAuthorizer
import ratpack.pac4j.internal.RatpackWebContext

@Slf4j
class DefaultAuthorizer extends AbstractAuthorizer {

    private AntPathMatcher antPathMatcher = new AntPathMatcher()
    private Map<String, Set<SecurePathDetailsConfig>> routeSecurityMappings
    private Set<String> pathExclusions

    @Inject
    DefaultAuthorizer(AppConfig appConfig) {
        this.routeSecurityMappings = appConfig.auth.getRouteSecurityMappings()
        this.pathExclusions = appConfig.auth?.security?.pathExclusions ?: []
        log.debug("These routes have been secured: ${routeSecurityMappings}")
        log.debug("These routes have been excluded: ${pathExclusions}")
    }

    @Override
    boolean isAuthenticationRequired(Context context) {
        String path =  getPath(context)
        HttpMethod method = context.request.method

        boolean pathExcluded = isPathExcluded(path)
        log.debug("Is path excluded? [path: ${path}, excluded: ${pathExcluded}]")

        // Path does not require authentication.
        if(isPathExcluded(path)) {
            return false
        }

        // Path is protected for some HTTP methods check for presence of PERMIT_ALL role.
        boolean authenticationRequired = routeSecurityMappings.get(path)?.any {
            method.name(it.method) && !it.roles.any { it == RoleType.PERMIT_ALL }
        }

        log.debug("Is authentication necessary for [path: ${path}, method: ${method}, authenticationRequired: ${authenticationRequired}]")
        return authenticationRequired
    }

    @Override
    void handleAuthorization(Context context, UserProfile userProfile) throws Exception {
        String path =  getPath(context)
        HttpMethod method = context.request.method

        // Under certain scenarios Ratpack's Pac4j authorization handler will invoke
        // this handler even when path does not require authentication.
        boolean authenticationRequired = isAuthenticationRequired(context)
        log.debug("Is auth required? [path: ${path}, excluded: ${authenticationRequired}]")

        // Path does not require authentication.
        if(!authenticationRequired) {
            context.next()
            return
        }

        SecurePathDetailsConfig routeSecurityConfig = routeSecurityMappings.get(path)?.find {
            method.name(it.method)
        }

        List<RoleType> allowedRoles = routeSecurityConfig?.roles ?: []
        List<RoleType> hasRoles = userProfile?.roles?.collect { it as RoleType } ?: []

        if(hasRoles.any { allowedRoles.contains(it) }) {
            context.next()
        } else {
            log.debug("User not authorized. Path [${path} : ${method.name}] requires [${allowedRoles}] user has [${hasRoles}]")
            throw RequiresHttpAction.forbidden('User not authorized to access path.', new RatpackWebContext(context))
        }
    }

    private String getPath(Context context) {
        return '/' + context.request.path
    }

    private boolean isPathExcluded(String requestedPath) {
        return this.pathExclusions.any { String excludedPath ->
            return antPathMatcher.match(excludedPath, requestedPath)
        }
    }

}


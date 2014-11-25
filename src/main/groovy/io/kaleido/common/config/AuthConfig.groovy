package io.kaleido.common.config

import groovy.transform.ToString

import javax.validation.constraints.NotNull

@ToString
class AuthConfig {

    /**
     * The number of rounds bcrypt will hash a password.
     */
    @NotNull
    Integer bcryptRounds

    /**
     * The url which a user will be directed to for authorization requests.
     * Format: my/apps/signin/page
     */
    @NotNull
    String loginUrl

    /**
     * The url which should handle an authentication request.
     * Format: my/apps/authenticate
     */
    @NotNull
    String authCallbackUrl

    /**
     * A mapping of URL paths to the roles allowed access.
     * Any path not present will not require any authorization.
     */
    @NotNull
    SecurityConfig security

    /**
     * A convenience to retrieve a map representing route security.
     * The key of the map is the path being secured.
     * @return
     */
    Map<String, Set<SecurePathDetailsConfig>> getRouteSecurityMappings() {

        Map<String, Set<SecurePathDetailsConfig>> routeSecurityMappings = [:]
        if (security && security.pathRestrictions) {
            security.pathRestrictions.each { SecurePathConfig securePathConfig ->
                routeSecurityMappings.put(securePathConfig.path, securePathConfig.restrict)
            }
        }
        return routeSecurityMappings
    }

}

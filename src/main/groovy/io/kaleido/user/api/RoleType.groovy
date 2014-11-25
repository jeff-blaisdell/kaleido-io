package io.kaleido.user.api

import com.fasterxml.jackson.annotation.JsonCreator
import groovy.util.logging.Slf4j

/**
 * Enumeration of available user roles.
 * PERMIT_ALL is rather a pseudo roles to mark a resource as completely open.
 */
@Slf4j
enum RoleType {
    ROLE_REGISTERED_USER,
    ROLE_ADMIN_USER,
    PERMIT_ALL

    @JsonCreator
    static RoleType create(String val) {
        try {
            return valueOf(val)
        } catch (Exception e) {
            log.warn("Unable to create enum from [val: ${val}]", e)
        }
    }
}

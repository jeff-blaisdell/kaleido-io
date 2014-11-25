package io.kaleido.common.config

import groovy.transform.ToString

@ToString
class SecurityConfig {
    Set<String> pathExclusions
    Set<SecurePathConfig> pathRestrictions
}

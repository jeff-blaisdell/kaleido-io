package io.kaleido.common.config

import groovy.transform.ToString

@ToString
class SecurePathConfig {
    String path
    Set<SecurePathDetailsConfig> restrict
}

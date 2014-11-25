package io.kaleido.common.config

import javax.validation.constraints.NotNull

class AppConfig {

    /**
     * General Auth configuration
     */
    @NotNull
    AuthConfig auth

    @NotNull
    DatabaseConfig database

}

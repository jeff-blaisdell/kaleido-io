package io.kaleido.common.api

import javax.validation.constraints.NotNull

class AppConfig {

    @NotNull
    DatabaseConfig database
}

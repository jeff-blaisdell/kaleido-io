package io.kaleido.common.config

import com.google.common.collect.Maps
import javax.validation.constraints.NotNull;

class DatabaseConfig {

    @NotNull
    String driverClass = null;

    @NotNull
    String user = null;

    String password = "";

    @NotNull
    String url = null;

    @NotNull
    Map<String, String> properties = Maps.newHashMap();

}

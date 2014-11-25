package io.kaleido.common.provider

import com.google.inject.Provider
import groovy.util.logging.Slf4j
import io.kaleido.common.config.AppConfig
import org.yaml.snakeyaml.Yaml

@Slf4j
class AppConfigProvider implements Provider<AppConfig> {

    @Override
    AppConfig get() {
        String env = System.getenv('ENV_SPEC') ?: 'local'
        InputStream input = new FileInputStream("${env}_config.yml")

        Yaml yaml = new Yaml();
        return yaml.loadAs(input, AppConfig)
    }
}

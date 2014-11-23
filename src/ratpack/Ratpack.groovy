import com.google.inject.util.Modules
import io.kaleido.common.module.AppConfigModule
import io.kaleido.common.module.GlobalJacksonConfigModule
import io.kaleido.common.module.HibernateModule
import io.kaleido.user.handler.UserHandlerFactory
import io.kaleido.user.module.UserModule
import ratpack.groovy.templating.TemplatingModule
import ratpack.jackson.JacksonModule
import static ratpack.groovy.Groovy.ratpack

ratpack {
    bindings {
        add Modules.override(new JacksonModule()).with(new GlobalJacksonConfigModule())
        add TemplatingModule
        add new AppConfigModule()
        add new HibernateModule()
        add new UserModule()
    }

    handlers {

        // User API
        prefix('api/u') {
            handler(registry.get(UserHandlerFactory).create(launchConfig))
        }

        assets "public"
    }
}

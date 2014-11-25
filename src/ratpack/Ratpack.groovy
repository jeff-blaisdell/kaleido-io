import com.google.inject.util.Modules
import io.kaleido.auth.handler.AuthHandlerFactory
import io.kaleido.auth.module.AuthModule
import io.kaleido.common.handler.IndexHandler
import io.kaleido.common.module.CommonModule
import io.kaleido.common.module.GlobalJacksonConfigModule
import io.kaleido.user.handler.UserHandlerFactory
import io.kaleido.user.module.UserModule
import ratpack.groovy.templating.TemplatingModule
import ratpack.jackson.JacksonModule
import ratpack.rx.RxRatpack
import ratpack.session.SessionModule
import ratpack.session.store.MapSessionsModule

import static ratpack.groovy.Groovy.ratpack

ratpack {
    RxRatpack.initialize()

    bindings {
        add Modules.override(new JacksonModule()).with(new GlobalJacksonConfigModule())
        add TemplatingModule
        add new SessionModule()
        add new MapSessionsModule(10, 5)
        add new CommonModule()
        add new UserModule()
        add new AuthModule()
    }

    handlers {
        handler(registry.get(AuthHandlerFactory).create(launchConfig))

        // User API
        prefix('api/user') {
            handler(registry.get(UserHandlerFactory).create(launchConfig))
        }

        // Application Routes
        handler('', registry.get(IndexHandler))
        handler('app/auth/signin', registry.get(IndexHandler))
        handler('app/auth/register', registry.get(IndexHandler))

        assets 'public'
    }
}

package io.kaleido.user.handler

import com.google.inject.Inject
import groovy.util.logging.Slf4j
import io.kaleido.user.entity.User
import io.kaleido.user.service.UserService
import ratpack.func.Action
import ratpack.handling.Chain
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.handling.Handlers
import ratpack.launch.HandlerFactory
import ratpack.launch.LaunchConfig
import static ratpack.jackson.Jackson.json

@Slf4j
class UserHandlerFactory implements HandlerFactory {

    private final UserService userService

    @Inject
    UserHandlerFactory(UserService userService) {
        this.userService = userService
    }

    @Override
    Handler create(LaunchConfig launchConfig) throws Exception {
        return Handlers.chain(launchConfig, new Action<Chain>() {
            public void execute(Chain chain) {
                chain

                .get(new Handler() {
                    @Override
                    void handle(Context context) throws Exception {
                        userService.listUsers().subscribe { List<User> users ->
                            context.render(json(users))
                        }
                    }
                })

                .get(':userGuid', new Handler() {
                    @Override
                    void handle(Context context) throws Exception {
                        String userGuid = context.pathTokens['userGuid']
                        userService.findUser(userGuid).subscribe { User user ->
                            if (!user) {
                                context.next()
                                return
                            }
                            context.render(json(user))
                        }
                    }
                })
            }
        });
    }


}

package io.kaleido.user.handler

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Inject
import groovy.util.logging.Slf4j
import io.kaleido.user.api.User
import io.kaleido.user.entity.UserEntity
import io.kaleido.user.service.UserService
import ratpack.func.Action
import ratpack.handling.Chain
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.handling.Handlers
import ratpack.launch.HandlerFactory
import ratpack.launch.LaunchConfig
import rx.functions.Action1

import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.fromJson

@Slf4j
class UserHandlerFactory implements HandlerFactory {

    private final UserService userService
    private final ObjectMapper objectMapper

    @Inject
    UserHandlerFactory(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService
        this.objectMapper = objectMapper
    }

    @Override
    Handler create(LaunchConfig launchConfig) throws Exception {
        return Handlers.chain(launchConfig, new Action<Chain>() {
            public void execute(Chain chain) {
                chain

                .post(new Handler() {
                    @Override
                    void handle(Context context) throws Exception {
                        log.debug('Entering save user handler.')
                        User newUser = context.parse(fromJson(User, objectMapper))
                        userService.createUser(newUser).subscribe(new Action1<UserEntity>() {
                            @Override
                            void call(UserEntity userEntity) {
                                User user = objectMapper.convertValue(userEntity, User)
                                context.render(json(user))
                            }
                        })
                    }
                })

                .get(new Handler() {
                    @Override
                    void handle(Context context) throws Exception {
                        userService.listUsers().subscribe(new Action1<List<UserEntity>>() {
                            @Override
                            void call(List<UserEntity> userEntities) {
                                List<User> users = objectMapper.convertValue(
                                    userEntities,
                                    new TypeReference<List<User>>() {}
                                )
                                context.render(json(users))
                            }
                        })
                    }
                })

                .get(':userGuid', new Handler() {
                    @Override
                    void handle(Context context) throws Exception {
                        String userGuid = context.pathTokens['userGuid']
                        userService.findUser(userGuid).subscribe(new Action1<UserEntity>() {
                            @Override
                            void call(UserEntity userEntity) {
                                User user = objectMapper.convertValue(userEntity, User)
                                context.render(json(user))
                            }
                        })
                    }
                })

                .put(':userGuid', new Handler() {
                    @Override
                    void handle(Context context) throws Exception {
                        String userGuid = context.pathTokens['userGuid']
                        User existingUser = context.parse(fromJson(User, objectMapper))

                        if (userGuid != existingUser.userGuid) {
                            throw new IllegalArgumentException('Unable to update requested user.')
                        }

                        userService.updateUser(existingUser).subscribe(new Action1<UserEntity>() {
                            @Override
                            void call(UserEntity userEntity) {
                                User user = objectMapper.convertValue(userEntity, User)
                                context.render(json(user))
                            }
                        })
                    }
                })

                .put(':userGuid/pwd', new Handler() {
                    @Override
                    void handle(Context context) throws Exception {
                        String userGuid = context.pathTokens['userGuid']
                        User existingUser = context.parse(fromJson(User, objectMapper))

                        if (userGuid != existingUser.userGuid) {
                            throw new IllegalArgumentException('Unable to update requested user password.')
                        }
                        userService.updateUserPassword(existingUser).subscribe(new Action1<UserEntity>() {
                            @Override
                            void call(UserEntity userEntity) {
                                User user = objectMapper.convertValue(userEntity, User)
                                context.render(json(user))
                            }
                        })
                    }
                })

                .delete(':userGuid', new Handler() {
                    @Override
                    void handle(Context context) throws Exception {
                        String userGuid = context.pathTokens['userGuid']
                        User existingUser = context.parse(fromJson(User, objectMapper))

                        if (userGuid != existingUser.userGuid) {
                            throw new IllegalArgumentException('Unable to delete requested user.')
                        }

                        userService.deleteUser(existingUser).subscribe(new Action1<UserEntity>() {
                            @Override
                            void call(UserEntity userEntity) {
                                User user = objectMapper.convertValue(userEntity, User)
                                context.render(json(user))
                            }
                        })
                    }
                })

            }
        })
    }


}

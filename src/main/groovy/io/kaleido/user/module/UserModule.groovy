package io.kaleido.user.module

import com.google.inject.AbstractModule
import groovy.transform.CompileStatic
import io.kaleido.common.module.HibernateModule
import io.kaleido.user.dao.UserDao
import io.kaleido.user.handler.UserHandlerFactory
import io.kaleido.user.service.DefaultUserService
import io.kaleido.user.service.UserService

@CompileStatic
class UserModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new HibernateModule())
        bind(UserService).to(DefaultUserService)
        bind(UserDao)
        bind(UserHandlerFactory)
    }
}

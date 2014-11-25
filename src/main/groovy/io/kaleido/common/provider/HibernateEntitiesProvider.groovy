package io.kaleido.common.provider

import com.google.inject.Provider
import io.kaleido.common.config.HibernateEntitiesConfig
import io.kaleido.user.entity.AddressEntity
import io.kaleido.user.entity.RoleEntity
import io.kaleido.user.entity.UserEntity
import io.kaleido.user.entity.UserRoleEntity

class HibernateEntitiesProvider implements Provider<HibernateEntitiesConfig> {

    @Override
    HibernateEntitiesConfig get() {
        HibernateEntitiesConfig hibernateEntities = new HibernateEntitiesConfig()
        hibernateEntities.entities = [
            UserEntity,
            RoleEntity,
            UserRoleEntity,
            AddressEntity
        ]
        return hibernateEntities
    }
}

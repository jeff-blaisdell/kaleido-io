package io.kaleido.common.provider

import com.google.inject.Provider
import io.kaleido.common.api.HibernateEntities
import io.kaleido.user.entity.User

class HibernateEntitiesProvider implements Provider<HibernateEntities> {

    @Override
    HibernateEntities get() {
        HibernateEntities hibernateEntities = new HibernateEntities()
        hibernateEntities.entities = [
            User
        ]
        return hibernateEntities
    }
}

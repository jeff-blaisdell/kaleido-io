package io.kaleido.user.dao

import com.google.inject.Inject
import io.kaleido.common.dao.CommonDao
import io.kaleido.user.entity.RoleEntity
import org.hibernate.Criteria
import org.hibernate.Session
import org.hibernate.SessionFactory
import ratpack.exec.ExecControl
import rx.Observable

class RoleDao extends CommonDao {

    @Inject
    RoleDao(ExecControl execControl, SessionFactory sessionFactory) {
        super(execControl, sessionFactory)
    }

    Observable<List<RoleEntity>> listRoles() {
        Session session = openSession()
        RoleCriteriaBuilder criteriaBuilder = new RoleCriteriaBuilder()
        Criteria criteria = criteriaBuilder.build(session)

        block(session) {
            return (List<RoleEntity>) criteria.list()
        }
    }
}

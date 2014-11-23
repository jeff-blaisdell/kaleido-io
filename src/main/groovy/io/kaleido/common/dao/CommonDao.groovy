package io.kaleido.common.dao

import org.hibernate.Session
import org.hibernate.SessionFactory
import ratpack.exec.ExecControl
import rx.Observable
import static ratpack.rx.RxRatpack.observe

class CommonDao {

    protected final ExecControl execControl
    protected final SessionFactory sessionFactory

    CommonDao(ExecControl execControl, SessionFactory sessionFactory) {
        this.execControl = execControl
        this.sessionFactory = sessionFactory
    }

    protected Session openSession() {
        Session session = sessionFactory.openSession()
        if (!session.transaction.active) {
            session.beginTransaction()
        }
        return session
    }

    protected Observable block(Session session, Closure closure) {
        observe(
            execControl.blocking(closure)
        ).doOnCompleted({
            session.transaction.commit()
        }).doOnError({
            session.transaction.rollback()
        })
    }
}

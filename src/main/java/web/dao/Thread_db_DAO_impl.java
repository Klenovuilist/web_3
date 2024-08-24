package web.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import web.Entity.Thread_db;

import java.util.List;
@Component
public class Thread_db_DAO_impl implements Thread_db_DAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Thread_db_DAO_impl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
@Transactional
    @Override
    public List<Thread_db> getThread_db() {
        return sessionFactory.getCurrentSession().createQuery("from Thread_db", Thread_db.class).getResultList();
    }
}



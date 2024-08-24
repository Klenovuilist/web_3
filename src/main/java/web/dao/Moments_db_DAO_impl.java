package web.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.Entity.Materals_db;
import web.Entity.Moments_db;

import javax.transaction.Transactional;
import java.util.List;


@Component
public class Moments_db_DAO_impl implements Moments_db_DAO{

    @Autowired
    private SessionFactory sessionFactory;

    public Moments_db_DAO_impl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public List<Moments_db> getMoments_db() {
        Session session = sessionFactory.getCurrentSession();

       return session.createQuery("from Moments_db mom join fetch mom.thread", Moments_db.class).getResultList();



//        return session.createQuery("from Moments_db m join fetch m.thread join fetch m.materals_db", Moments_db.class).getResultList();
    }

    @Override
    @Transactional
    public void saveAll(Moments_db moments_dbs) {
        Session session = sessionFactory.getCurrentSession();
        session.save(moments_dbs);
      }
    @Transactional
    @Override
    public void updateMoment(Moments_db moments_db) {
    Session session = sessionFactory.getCurrentSession();
    session.update(moments_db);
    }

    @Transactional
    @Override
    public List<Moments_db> momentsByIdMaterial(int idMaterial) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Moments_db mom where mom.materals_db =:id", Moments_db.class);
        query.setParameter("id", idMaterial);
        List<Moments_db> moments_dbs = (List<Moments_db>)query.getResultList();
        return moments_dbs;
    }
@Transactional
    @Override
    public void momentRemove(Moments_db moments_db) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(moments_db);
    }


}

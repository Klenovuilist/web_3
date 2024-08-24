package web.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.Entity.Materals_db;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class Material_db_DAO_impl implements Materals_db_DAO {

    @Autowired
    private SessionFactory sessionFactory;



    public Material_db_DAO_impl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;

    }

    @Transactional
    public int materals_dbSize() {

        Session session = sessionFactory.getCurrentSession();
        List<Materals_db> materals_db = session.createQuery("from Materals_db ", Materals_db.class)
                .getResultList();
        return materals_db.size();
    }

    @Transactional
    @Override
    public List<Materals_db> getMaterals_db() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("from Materals_db", Materals_db.class).getResultList();

    }

    @Transactional
    @Override
    public void save(Materals_db materals_db) {
        Session session = sessionFactory.getCurrentSession();
        session.save(materals_db);
    }

    @Transactional
    @Override
    public Materals_db materialsById(int id) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from Materals_db m join fetch m.moments_db r join fetch r.thread" +
                " where m.id =: id", Materals_db.class);
        query.setParameter("id", id);
        return (Materals_db) query.getSingleResult();


//        return session.get(Materals_db.class, id);
    }

    @Transactional
    @Override
    public void updateMaterial(Materals_db materals_db) {
        Session session = sessionFactory.getCurrentSession();
        session.update(materals_db);
    }
    @Transactional
    @Override
    public void materialRemove(Materals_db materals_db) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(materals_db);

    }
}

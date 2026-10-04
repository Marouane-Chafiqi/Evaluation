package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/** Implementation generique de IDao, reutilisee par les 3 services. */
public abstract class AbstractService<T> implements IDao<T> {

    private final Class<T> type;

    protected AbstractService(Class<T> type) {
        this.type = type;
    }

    private interface Action {
        void run(Session s);
    }

    private boolean inTransaction(Action action) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            action.run(s);
            tx.commit();
            return true;
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public T create(T o) {
        return inTransaction(s -> s.save(o)) ? o : null;
    }

    @Override
    public boolean update(T o) {
        return inTransaction(s -> s.update(o));
    }

    @Override
    public boolean delete(T o) {
        return inTransaction(s -> s.delete(o));
    }

    @Override
    public T findById(int id) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(type, id);
        }
    }

    @Override
    public List<T> findAll() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from " + type.getSimpleName(), type).list();
        }
    }
}

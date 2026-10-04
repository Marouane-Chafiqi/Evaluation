package ma.projet.service;

import java.util.List;
import java.util.function.Consumer;

import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Code commun l kol les services (bach ma n3awdouch nfs l-code 4 mrat).
 * Kol service kay-extend had classe w kay3ti ghir l-classe dyal l-entité.
 */
public abstract class AbstractService<T> implements IDao<T> {

    private final Class<T> classe;

    protected AbstractService(Class<T> classe) {
        this.classe = classe;
    }

    // Kat-exécuter chi action dakhel transaction (commit / rollback)
    private boolean executer(Consumer<Session> action) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            action.accept(session);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean create(T o) {
        return executer(s -> s.save(o));
    }

    @Override
    public boolean update(T o) {
        return executer(s -> s.update(o));
    }

    @Override
    public boolean delete(T o) {
        return executer(s -> s.delete(o));
    }

    @Override
    public T findById(int id) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(classe, id);
        }
    }

    @Override
    public List<T> findAll() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from " + classe.getSimpleName(), classe).list();
        }
    }
}

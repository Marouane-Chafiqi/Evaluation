package ma.projet.dao;

import java.util.List;

public interface IDao<T> {
    T create(T o);
    boolean update(T o);
    boolean delete(T o);
    T findById(int id);
    List<T> findAll();
}

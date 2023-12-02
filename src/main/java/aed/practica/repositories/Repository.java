package aed.practica.repositories;

import java.util.List;

public interface Repository<T> {
    
    public List<T> findAll();
    public T findOneById(int id);
    public T save(T t);
    public void updateById(int id, T t);
    public void deleteById(int id);

}

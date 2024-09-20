package repository.interfaces;

import java.util.List;
import java.util.Optional;

public interface CrudInterface<T> {
    T save(T entity);

    Optional<T> findById(int entity);

    List<T> findAll();

    T update(T entity);

    boolean delete(T entity);

}

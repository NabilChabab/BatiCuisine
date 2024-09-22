package repository.interfaces;

import domain.entities.WorkForce;

import java.util.List;
import java.util.Optional;

public interface WorkForceInterface<T extends WorkForce> extends CrudInterface<WorkForce> {
    List<WorkForce> findAllByProjectId(int id);
}

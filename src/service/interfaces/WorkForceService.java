package service.interfaces;

import domain.entities.WorkForce;
import repository.interfaces.WorkForceInterface;

import java.util.List;
import java.util.Optional;

public interface WorkForceService extends WorkForceInterface<WorkForce> {
    @Override
    WorkForce save(WorkForce workForce);

    @Override
    Optional<WorkForce> findById(WorkForce workForce);

    @Override
    List<WorkForce> findAll();

    @Override
    WorkForce update(WorkForce workForce);

    @Override
    boolean delete(WorkForce workForce);
}

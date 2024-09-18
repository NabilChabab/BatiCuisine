package service;

import domain.entities.WorkForce;
import repository.WorkForceRepository;
import service.interfaces.WorkForceService;

import java.util.List;
import java.util.Optional;

public class WorkForceServiceImpl implements WorkForceService {

    private final WorkForceRepository workForceRepository;

    public WorkForceServiceImpl(WorkForceRepository workForceRepository) {
        this.workForceRepository = workForceRepository;
    }

    @Override
    public WorkForce save(WorkForce workForce) {
        return workForceRepository.save(workForce);
    }

    @Override
    public Optional<WorkForce> findById(WorkForce workForce) {
        return this.workForceRepository.findById(workForce);
    }

    @Override
    public List<WorkForce> findAll() {
        return this.workForceRepository.findAll();
    }

    @Override
    public WorkForce update(WorkForce workForce) {
        return this.workForceRepository.update(workForce);
    }

    @Override
    public boolean delete(WorkForce workForce) {
        return this.workForceRepository.delete(workForce);
    }
}

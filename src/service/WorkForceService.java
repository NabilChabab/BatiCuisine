package service;

import domain.entities.WorkForce;
import repository.ComponentRepository;
import repository.WorkForceRepository;


import java.util.List;
import java.util.Optional;

public class WorkForceService {


    private final WorkForceRepository workForceRepository;
    private final ComponentRepository componentRepository;

    public WorkForceService(WorkForceRepository workForceRepository, ComponentRepository componentRepository) {
        this.workForceRepository = workForceRepository;
        this.componentRepository = componentRepository;
    }

    public WorkForce save(WorkForce workForce) {
        return workForceRepository.save(workForce);
    }


    public Optional<WorkForce> findById(int id) {
        return this.workForceRepository.findById(id);
    }


    public List<WorkForce> findAll() {
        return this.workForceRepository.findAll();
    }


    public WorkForce update(WorkForce workForce) {
        return this.workForceRepository.update(workForce);
    }


    public boolean delete(int id) {
        return this.workForceRepository.delete(id);
    }

    public List<WorkForce> findAllByProjectId(int projectId){
        return this.workForceRepository.findAllByProjectId(projectId);
    }

    public double calculateWorkforce(WorkForce workForce) {
        return workForce.getWorkingHours() * workForce.getHourlyCost() * workForce.getWorkerProductivity();
    }

    public double getWorkforceVatRate(WorkForce workForce) {
        return componentRepository.findTvaForComponent(workForce.getId());
    }

    public double calculateWorkforceBeforeVat(WorkForce workForce) {
        return calculateWorkforce(workForce);
    }

    private double applyVat(double cost, double vatRate) {
        return cost + (cost * vatRate / 100);
    }

    public double calculateWorkforceAfterVat(WorkForce workForce) {
        double costBeforeVat = calculateWorkforceBeforeVat(workForce);
        double costAfterVat = getWorkforceVatRate(workForce);
        return applyVat(costBeforeVat, costAfterVat);
    }
}

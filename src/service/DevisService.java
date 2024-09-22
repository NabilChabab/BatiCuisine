package service;

import domain.entities.Devis;
import repository.DevisRepository;

import java.util.List;
import java.util.Optional;

public class DevisService{
    private final DevisRepository devisRepository;

    public DevisService(DevisRepository devisRepository) {
        this.devisRepository = devisRepository;
    }

    public Devis save(Devis devis) {
        return this.devisRepository.save(devis);
    }

    public Optional<Devis> findById(int id) {
        return this.devisRepository.findById(id);
    }

    public Devis update(Devis devis) {
        return this.devisRepository.update(devis);
    }

    public boolean delete(int id) {
        return this.devisRepository.delete(id);
    }

    public List<Devis> findAll() {
        return this.devisRepository.findAll();
    }

    public void updateAmountDevis(int devisId, double amount) {
        this.devisRepository.updateAmount(devisId, amount);
    }

    public Optional<Devis> findDevisByproject(int projectId){
        return   this.devisRepository.findDevisByProjectId(projectId);
    }

    public boolean updateDevisStatus(int devisId) {
        return this.devisRepository.updateDevisStatus(devisId);
    }

}

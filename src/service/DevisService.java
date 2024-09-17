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

    public Optional<Devis> findById(Devis devis) {
        return this.devisRepository.findById(devis);
    }

    public Devis update(Devis devis) {
        return this.devisRepository.update(devis);
    }

    public boolean delete(Devis devis) {
        return this.devisRepository.delete(devis);
    }

    public List<Devis> findAll() {
        return this.devisRepository.findAll();
    }

}

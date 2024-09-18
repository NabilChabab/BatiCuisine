package service;

import domain.entities.Devis;
import repository.DevisRepository;
import service.interfaces.DevisService;

import java.util.List;
import java.util.Optional;

public class DevisServiceImpl implements DevisService {
    private final DevisRepository devisRepository;

    public DevisServiceImpl(DevisRepository devisRepository) {
        this.devisRepository = devisRepository;
    }

    @Override
    public Devis save(Devis devis) {
        return this.devisRepository.save(devis);
    }

    @Override
    public Optional<Devis> findById(Devis devis) {
        return this.devisRepository.findById(devis);
    }

    @Override
    public Devis update(Devis devis) {
        return this.devisRepository.update(devis);
    }

    @Override
    public boolean delete(Devis devis) {
        return this.devisRepository.delete(devis);
    }

    @Override
    public List<Devis> findAll() {
        return this.devisRepository.findAll();
    }

}

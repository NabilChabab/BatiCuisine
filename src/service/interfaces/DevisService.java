package service.interfaces;

import domain.entities.Devis;

import java.util.List;
import java.util.Optional;

public interface DevisService {
    Devis save(Devis devis);

    Optional<Devis> findById(int devis);

    Devis update(Devis devis);

    boolean delete(int id);

    List<Devis> findAll();

    Optional<Devis> findDevisByProject(int id);
}

package service.interfaces;

import domain.entities.Devis;

import java.util.List;
import java.util.Optional;

public interface DevisService {
    Devis save(Devis devis);

    Optional<Devis> findById(Devis devis);

    Devis update(Devis devis);

    boolean delete(Devis devis);

    List<Devis> findAll();
}

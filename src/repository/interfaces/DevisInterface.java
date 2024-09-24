package repository.interfaces;

import domain.entities.Devis;

import java.util.Optional;

public interface DevisInterface extends CrudInterface<Devis> {

    void updateAmount(int id , double amount);
    Optional<Devis> findDevisByProjectId(int id);
    boolean updateDevisStatus(int id);
}

package repository.interfaces;

import domain.entities.Devis;

import java.util.List;
import java.util.Optional;

public interface DevisInterface extends CrudInterface<Devis> {

    @Override
    public Devis save(Devis entity);

    @Override
    public Optional<Devis> findById(int devis);

    @Override
    public List<Devis> findAll();

    @Override
    public Devis update(Devis entity);

    @Override
    public boolean delete(Devis entity);


    public boolean deleteDevisById(int id);

    Optional<Devis> findDevisByProject(int id);
}

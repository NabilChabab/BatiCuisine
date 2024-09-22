package repository.interfaces;


import domain.entities.Client;

import java.util.List;
import java.util.Optional;

public interface ClientInterface extends CrudInterface<Client> {
    Optional<Client> findByName(String name);
}

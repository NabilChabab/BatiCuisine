package service.interfaces;

import domain.entities.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Client save(Client Client);

    Optional<Client> findById(int Client);

    Client update(Client Client);

    boolean delete(Client Client);

    List<Client> findAll();

    Optional<Client> findByName(String name);
}

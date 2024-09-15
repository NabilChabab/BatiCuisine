package service;

import domain.entities.Client;
import repository.ClientRepository;

import java.util.List;
import java.util.Optional;

public class ClientService {
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void save(Client client) {
        clientRepository.save(client);
    }

    public Optional<Client> findById(Client client) {
        return clientRepository.findById(client);  // Return based on ID
    }

    public List<Client> findAll() {
        return clientRepository.findAll();  // Return a list of clients
    }

    public void update(Client client) {
        clientRepository.update(client);
    }

    public void delete(Client client) {
        clientRepository.delete(client);
    }
}


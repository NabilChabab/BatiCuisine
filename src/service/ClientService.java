package service;



import domain.entities.Client;
import repository.ClientRepository;

import java.util.List;
import java.util.Optional;

public class ClientService{

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client save(Client Client) {
        return this.clientRepository.save(Client);
    }

    public Optional<Client> findById(int id) {
        return this.clientRepository.findById(id);
    }

    public Client update(Client Client) {
        return this.clientRepository.update(Client);
    }

    public boolean delete(int id) {
        return this.clientRepository.delete(id);
    }

    public List<Client> findAll() {
        return this.clientRepository.findAll();
    }

    public Optional<Client> findByName(String name) {
        return clientRepository.findByName(name);
    }

}

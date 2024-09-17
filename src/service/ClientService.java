package service;



import domain.entities.Client;
import repository.ClientRepository;
import utils.Validations;

import java.util.List;
import java.util.Optional;

public class ClientService  {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client save(Client Client) {
        return this.clientRepository.save(Client);
    }

    public Optional<Client> findById(Client Client) {
        return this.clientRepository.findById(Client);
    }

    public Client update(Client Client) {
        return this.clientRepository.update(Client);
    }

    public boolean delete(Client Client) {
        return this.clientRepository.delete(Client);
    }

    public List<Client> findAll() {
        return this.clientRepository.findAll();
    }


    public Optional<Client> findByName(String name) {
        Validations.ClientByNameValidation(name);
        return this.clientRepository.findByName(name);
    }


}

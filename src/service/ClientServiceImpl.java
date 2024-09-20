package service;



import domain.entities.Client;
import repository.ClientRepository;
import service.interfaces.ClientService;
import utils.Validations;

import java.util.List;
import java.util.Optional;

public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client save(Client Client) {
        return this.clientRepository.save(Client);
    }

    @Override
    public Optional<Client> findById(int Client) {
        return this.clientRepository.findById(Client);
    }

    @Override
    public Client update(Client Client) {
        return this.clientRepository.update(Client);
    }

    @Override
    public boolean delete(Client Client) {
        return this.clientRepository.delete(Client);
    }

    @Override
    public List<Client> findAll() {
        return this.clientRepository.findAll();
    }


    @Override
    public Optional<Client> findByName(String name) {
        Validations.ClientByNameValidation(name);
        return this.clientRepository.findByName(name);
    }


}

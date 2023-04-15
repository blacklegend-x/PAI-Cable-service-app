package com.example.gawex.service;

import com.example.gawex.entity.Client;
import com.example.gawex.repo.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepo clientRepo;

    @Override
    public List<Client> getAll(){
        List<Client> clientsList = clientRepo.findAll();
        if (clientsList==null){
            clientsList=new ArrayList<>();
        }
        return clientsList;
    }

    @Override
    public Client addClient(Client client) {
        clientRepo.save(client);
        return client;
    }

    @Override
    public void deleteClient(Client client) {
        clientRepo.deleteById(client.getId());
    }


    @Override
    public List<Client> getBySurname(String surname) {
        List<Client> clientList = getAll();
        List<Client> surnameClient = clientList
                .stream()
                .filter(client -> client.getSurname().equals(surname))
                .toList();
        return surnameClient;
    }

    @Override
    public List<Client> getByStreetName(String streetName) {
        List<Client> clientList = getAll();
        List<Client> streetNameClient = clientList
                .stream()
                .filter(client -> client.getStreetName().equals(streetName))
                .toList();
        return streetNameClient;
    }

    @Override
    public List<String> getStreetNames() {
        List<Client> allClients = getAll();
        return allClients.stream().map(Client::getStreetName).distinct().toList();
    }
}

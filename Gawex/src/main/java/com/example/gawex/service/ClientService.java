package com.example.gawex.service;

import com.example.gawex.entity.Client;

import java.util.List;

public interface ClientService {

    List<Client> getAll();

    Client addClient(Client client);
    void deleteClient(Client client);

    List<Client> getBySurname(String surname);
    List<Client> getByStreetName(String streetName);

    List<String> getStreetNames();

}

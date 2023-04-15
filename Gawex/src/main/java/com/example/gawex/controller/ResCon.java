package com.example.gawex.controller;


import com.example.gawex.entity.Client;
import com.example.gawex.entity.Failure;
import com.example.gawex.entity.Installation;
import com.example.gawex.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
public class ResCon {



//    @Autowired
//    StatusService statusService;



//    @Autowired
//    TypeFailureService typeFailureService;

    @Autowired
    ClientService clientService;

    public String isBuilding="nie";


//    @PostMapping("/Installation")


    public void saveClient(String contractNumber, String name, String surname, String streetName, String buildingNumber, String flatNumber, String numberPhone, String email){
        Client client = new Client();
        client.setContractNumber(contractNumber);
        client.setName(name);
        client.setSurname(surname);
        client.setStreetName(streetName);
        client.setBuildingNumber(buildingNumber);
        client.setFlatNumber(flatNumber);
        client.setNumberPhone(numberPhone);
        client.setEmail(email);
        clientService.addClient(client);
    }

    public void updateClient(String chosenClientId,String contractNumber,String name, String surname, String streetName, String buildingNumber, String flatNumber, String email, String numberPhone) {
        List<Client> clientList = clientService.getAll();
        Optional<Client> clientOptional = clientList
                .stream()
                .filter(client -> client.getId().toString().equals(chosenClientId))
                .findFirst();

        Client client = clientOptional.orElse(null);
        client.setContractNumber(contractNumber);
        client.setName(name);
        client.setSurname(surname);
        client.setStreetName(streetName);
        client.setBuildingNumber(buildingNumber);
        client.setFlatNumber(flatNumber);
        client.setEmail(email);
        client.setNumberPhone(numberPhone);

        clientService.addClient(client);
    }



//        public void saveClient(Client savedClient){
//        Client client = new Client();
//        client.setContractNumber(savedClient.getContractNumber());
//        client.setName(savedClient.getName());
//        client.setSurname(savedClient.getSurname());
//        client.setStreetName(savedClient.getStreetName());
//        client.setBuildingNumber(savedClient.getBuildingNumber());
//        client.setFlatNumber(savedClient.getFlatNumber());
//        client.setEmail(savedClient.getEmail());
//        client.setNumberPhone(savedClient.getNumberPhone());
//        clientService.addClient(savedClient);
//
//    }
}

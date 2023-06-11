package com.example.gawex.controller;


import com.example.gawex.entity.Client;
import com.example.gawex.entity.Failure;
import com.example.gawex.entity.Installation;
import com.example.gawex.service.ClientService;
import com.example.gawex.service.FailureService;
import com.example.gawex.service.InstallationService;
import com.example.gawex.service.TypeFailureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

    @Autowired
    InstallationService installationService;

    @Autowired
    FailureService failureService;

    public String isBuilding="nie";
    public Date date;
    public Time time;
    public String typeInstallation;
    public String typeFailure;

    @PutMapping("/failure/{contractNumber}")
    public void saveFailure(String typeFailure, Date date, Time time, String isBuilding, @PathVariable String contractNumber, String name, String surname, String buildingNumber, String flatNumber, String streetName, String numberPhone){
        Failure failure=new Failure();
        failure.setName(name);
        failure.setSurname(surname);
        failure.setTime(time);
        failure.setDate(date);
        failure.setTypeFailure(typeFailure);
        failure.setIsBuilding(isBuilding);
        failure.setContractNumber(contractNumber);
        failure.setStreetName(streetName);
        failure.setBuildingNumber(buildingNumber);
        failure.setFlatNumber(flatNumber);
        failure.setNumberPhone(numberPhone);
        failure.setStatus("Przyjęto");
        failureService.addFailure(failure);
    }
    @PutMapping("failure/update/{contractNumber}")
    public void updateFailure(String chosenFailureId,@PathVariable String contractNumber, String name, String surname, String streetName, String buildingNumber, String flatNumber, String numberPhone, String typeFailure, LocalTime time, LocalDate date, String isBuilding, String status) {
        List<Failure> failureList = failureService.getAll();
        Optional<Failure> failureOptional = failureList
                .stream()
                .filter(failure -> failure.getId().toString().equals(chosenFailureId))
                .findFirst();

        Failure failure = failureOptional.orElse(null);
        failure.setStatus(status);
        failure.setContractNumber(contractNumber);
        failure.setName(name);
        failure.setSurname(surname);
        failure.setStreetName(streetName);
        failure.setBuildingNumber(buildingNumber);
        failure.setFlatNumber(flatNumber);
        failure.setTypeFailure(typeFailure);
        failure.setDate(Date.valueOf(date));
        failure.setTime(Time.valueOf(time));
        failure.setIsBuilding(isBuilding);
        failure.setNumberPhone(numberPhone);

        failureService.addFailure(failure);
    }

    @PutMapping("/installation/{contractNumber}")
    public void saveInstallation(String typeInstallation, Date date, Time time, String isBuilding, @PathVariable String contractNumber, String name, String surname, String buildingNumber, String flatNumber, String streetName, String numberPhone){
        Installation installation = new Installation();
        installation.setName(name);
        installation.setSurname(surname);
        installation.setTime(time);
        installation.setDate(date);
        installation.setTypeInstallation(typeInstallation);
        installation.setIsBuilding(isBuilding);
        installation.setContractNumber(contractNumber);
        installation.setStreetName(streetName);
        installation.setBuildingNumber(buildingNumber);
        installation.setFlatNumber(flatNumber);
        installation.setNumberPhone(numberPhone);
        installation.setStatus("Przyjęto");
        installationService.addInstallation(installation);
    }

    @PutMapping("installation/update/{contractNumber}")
    public void updateInstallation(String chosenInstallationId, @PathVariable String contractNumber, String name, String surname, String streetName, String buildingNumber, String flatNumber, String numberPhone, String typeInstallation, LocalTime time, LocalDate date, String isBuilding, String status) {
        List<Installation> installationList = installationService.getAll();
        Optional<Installation> installationOptional = installationList
                .stream()
                .filter(installation -> installation.getId().toString().equals(chosenInstallationId))
                .findFirst();

        Installation installation = installationOptional.orElse(null);
        installation.setStatus(status);
        installation.setContractNumber(contractNumber);
        installation.setName(name);
        installation.setSurname(surname);
        installation.setStreetName(streetName);
        installation.setBuildingNumber(buildingNumber);
        installation.setFlatNumber(flatNumber);
        installation.setTypeInstallation(typeInstallation);
        installation.setDate(Date.valueOf(date));
        installation.setTime(Time.valueOf(time));
        installation.setIsBuilding(isBuilding);
        installation.setNumberPhone(numberPhone);

        installationService.addInstallation(installation);
    }

    @PutMapping("/client/{contractNumber}")
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
    @PutMapping("/client/update/{contractNumber}")
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

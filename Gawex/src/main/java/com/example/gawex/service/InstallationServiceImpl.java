package com.example.gawex.service;

import com.example.gawex.entity.Installation;
import com.example.gawex.repo.InstallationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InstallationServiceImpl implements InstallationService {

    @Autowired
    InstallationRepo installationRepo;

    @Override
    public List<Installation> getAll() {
        List<Installation> installationList = installationRepo.findAll();
        if (installationList == null) {
            installationList = new ArrayList<>();
        }
        return installationList;
    }

    @Override
    public void addInstallation(Installation installation) {
        installationRepo.save(installation);
    }

    @Override
    public void deleteInstallation(Installation installation) {
        installationRepo.deleteById(installation.getId());
    }

    @Override
    public List<Installation> getByTypeInstallation(String typeInstallation) {
        List<Installation> installationList = getAll();
        List<Installation> installationTypeList = installationList
                .stream()
                .filter(failure -> failure.getTypeInstallation().equals(typeInstallation))
                .toList();

        return installationTypeList;

    }

    @Override
    public Installation getByDate(Date date) {
        List<Installation> installationList = getAll();
        Optional<Installation> dateOptional = installationList
                .stream()
                .filter(installation -> installation.getDate().equals(date))
                .findFirst();
        if (dateOptional.isPresent()) {
            return dateOptional.get();
        } else {
            return null;
        }
    }

    @Override
    public Installation getByDateTime(Date date, Time time) {
        List<Installation> installationList = getAll();
        Optional<Installation> optionalInstallation = installationList
                .stream()
                .filter(installation -> installation.getDate().equals(date) && installation.getTime().equals(time))
                .findFirst();
        if (optionalInstallation.isPresent()) {
            return optionalInstallation.get();
        } else {
            return null;
        }
    }

    @Override
    public List<Installation> getByStreetName(String streetName) {
        List<Installation> installationList = getAll();
        List<Installation> streetNameInstallation = installationList
                .stream()
                .filter(installation -> installation.getStreetName().equals(streetName))
                .toList();
        return streetNameInstallation;
    }

    @Override
    public List<String> getStreetNames() {
        List<Installation> allInstallations = getAll();
        return allInstallations.stream().map(Installation::getStreetName).distinct().toList();
    }

    @Override
    public List<Installation> getByType(String type) {
        List<Installation> installationList = getAll();
        List<Installation> typeInstallation = installationList
                .stream()
                .filter(failure -> failure.getStreetName().equals(type))
                .toList();
        return typeInstallation;
    }

    @Override
    public List<String> getTypes() {
        List<Installation> allInstallations = getAll();
        return allInstallations.stream().map(Installation::getTypeInstallation).distinct().toList();
    }
}

package com.example.gawex.service;

import com.example.gawex.entity.Installation;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface InstallationService {

    List<Installation> getAll();

    void addInstallation(Installation installation);
    void deleteInstallation(Installation installation);

    List<Installation> getByTypeInstallation(String typeInstallation);

    Installation getByDate(Date date);
    Installation getByDateTime(Date date, Time time);

    List<Installation> getByStreetName(String streetName);

    List<String> getStreetNames();

    List<Installation> getByType(String type);

    List<String> getTypes();
}

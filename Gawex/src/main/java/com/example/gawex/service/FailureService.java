package com.example.gawex.service;

import com.example.gawex.entity.Failure;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface FailureService {

    List<Failure> getAll();

    void addFailure(Failure failure);
    void deleteFailure(Failure failure);

    List<Failure> getByTypeFailure(String typeFailure);

    Failure getByDate(Date date);
    Failure getByDateTime(Date date, Time time);

    List<Failure> getByStreetName(String streetName);

    List<String> getStreetNames();

    List<Failure> getByType(String type);

    List<String> getTypes();

}

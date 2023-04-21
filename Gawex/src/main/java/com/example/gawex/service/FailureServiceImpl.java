package com.example.gawex.service;

import com.example.gawex.entity.Failure;
import com.example.gawex.repo.FailureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FailureServiceImpl implements FailureService{
    @Autowired
    FailureRepo failureRepo;

    @Override
    public List<Failure> getAll() {
        List<Failure> failureList = failureRepo.findAll();
        if (failureList==null){
            failureList=new ArrayList<>();
        }
        return failureList;
    }

    @Override
    public void addFailure(Failure failure) {
        failureRepo.save(failure);
    }

    @Override
    public void deleteFailure(Failure failure) {
        failureRepo.deleteById(failure.getId());
    }

    @Override
    public List<Failure> getByTypeFailure(String typeFailure) {
        List<Failure> failureList = getAll();
        List<Failure> failureTypeList = failureList
                .stream()
                .filter(failure -> failure.getTypeFailure().equals(typeFailure))
                .toList();

        return failureTypeList;

    }

    @Override
    public Failure getByDate(Date date) {
        List<Failure> failureList = getAll();
        Optional<Failure> dateOptional = failureList
                .stream()
                .filter(failure -> failure.getDate().equals(date))
                .findFirst();
        if (dateOptional.isPresent()){
            return dateOptional.get();
        }else   {
            return null;
        }
    }

    @Override
    public Failure getByDateTime(Date date, Time time) {
        List<Failure> failureList = getAll();
        Optional<Failure> optionalFailure = failureList
                .stream()
                .filter(failure -> failure.getDate().equals(date) && failure.getTime().equals(time))
                .findFirst();
        if (optionalFailure.isPresent()){
            return optionalFailure.get();
        }else{
            return null;
        }
    }

    @Override
    public List<Failure> getByStreetName(String streetName) {
        List<Failure> failureList = getAll();
        List<Failure> streetNameFailure = failureList
                .stream()
                .filter(failure -> failure.getStreetName().equals(streetName))
                .toList();
        return streetNameFailure;
    }

    @Override
    public List<String> getStreetNames() {
        List<Failure> allFailures = getAll();
        return allFailures.stream().map(Failure::getStreetName).distinct().toList();
    }

    @Override
    public List<Failure> getByType(String type) {
        List<Failure> failureList = getAll();
        List<Failure> typeFailure = failureList
                .stream()
                .filter(failure -> failure.getStreetName().equals(type))
                .toList();
        return typeFailure;
    }

    @Override
    public List<String> getTypes() {
        List<Failure> allFailures = getAll();
        return allFailures.stream().map(Failure::getTypeFailure).distinct().toList();
    }
}

package com.example.gawex.service;

import com.example.gawex.entity.TypeFailure;
import com.example.gawex.repo.TypeFailureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeFailureServiceImpl implements TypeFailureService{

    @Autowired
    TypeFailureRepo typeFailureRepo;

    @Override
    public List<TypeFailure> getAll() {
        List<TypeFailure> typeFailureList = typeFailureRepo.findAll();
        if (typeFailureList == null) {
            typeFailureList = new ArrayList<>();
        }
        return typeFailureList;
    }

    @Override
    public List<String> getTypeFailure() {
        List<TypeFailure> allTypeFailure = getAll();
        return allTypeFailure.stream().map(TypeFailure::getType).toList();
    }

}

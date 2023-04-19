package com.example.gawex.service;

import com.example.gawex.entity.TypeInstallation;
import com.example.gawex.repo.TypeInstallationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeInstallationServiceImpl implements TypeInstallationService{

    @Autowired
    TypeInstallationRepo typeInstallationRepo;

    @Override
    public List<TypeInstallation> getAll() {
        List<TypeInstallation> typeInstallationList = typeInstallationRepo.findAll();
        if (typeInstallationList == null) {
            typeInstallationList = new ArrayList<>();
        }
        return typeInstallationList;

    }

    @Override
    public List<String> getTypeInstallation() {
        List<TypeInstallation> allTypeInstallation = getAll();
        return allTypeInstallation.stream().map(TypeInstallation::getType).toList();
    }
}

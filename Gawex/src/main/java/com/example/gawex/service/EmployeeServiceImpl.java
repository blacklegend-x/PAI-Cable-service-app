package com.example.gawex.service;

import com.example.gawex.entity.Employee;
import com.example.gawex.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepo employeeRepo;

    @Override
    public List<Employee> getAll() {
        List<Employee> employeeList = employeeRepo.findAll();
        if (employeeList == null) {
            employeeList = new ArrayList<>();
        }
        return employeeList;
    }


    @Override
    public Employee getByLogin(String login) {
        List<Employee> employeeList = getAll();
        Optional<Employee> loginOptional = employeeList
                .stream()
                .filter(employee -> employee.getLogin().equals(login))
                .findFirst();

        return loginOptional.orElse(null);
    }

    @Override
    public Employee getByPassword(String password) {
        List<Employee> employeeList = getAll();
        Optional<Employee> passwordOptional = employeeList
                .stream()
                .filter(employee -> employee.getPassword().equals(password))
                .findFirst();

        if (passwordOptional.isPresent()) {
            return passwordOptional.get();
        } else {
            return null;
        }

    }

    @Override
    public String getPasswordByLogin(String login) {
        List<Employee> employeeList = getAll();
        Optional<Employee> loginOptional = employeeList
                .stream()
                .filter(employee -> employee.getLogin().equals(login))
                .findFirst();

        if (loginOptional.isPresent()) {
            return loginOptional.get().getPassword();
        } else {
            return null;
        }
    }
}
package com.example.gawex.service;

import com.example.gawex.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAll();

    Employee getByLogin(String login);
    Employee getByPassword(String password);
    String getPasswordByLogin(String login);
}

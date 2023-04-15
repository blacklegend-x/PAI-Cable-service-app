package com.example.gawex.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Time;

@Entity
public class Installation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String typeInstallation;
    Date date;
    Time time;
    String status;
    String contractNumber;
    String name;
    String surname;
    String streetName;
    String buildingNumber;
    String flatNumber;
    String isBuilding;
    String numberPhone;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTypeInstallation(String typeInstallation) {
        this.typeInstallation = typeInstallation;
    }

    public String getTypeInstallation() {
        return typeInstallation;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Time getTime() {
        return time;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setIsBuilding(String isBuilding) {
        this.isBuilding = isBuilding;
    }

    public String getIsBuilding() {
        return isBuilding;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getNumberPhone() {
        return numberPhone;
    }
}

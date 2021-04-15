package com.krukovska.vaccinationsystem.persistence;

public enum VaccineType {

    PHIZER("Phizer"),
    JOHNSON("Johnson & Johnson"),
    MODERNA("Moderna");

    private String name;

    VaccineType(String name) {
        this.name = name;
    }
}

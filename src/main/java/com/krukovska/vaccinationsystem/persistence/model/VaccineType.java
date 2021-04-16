package com.krukovska.vaccinationsystem.persistence.model;

import lombok.Getter;

@Getter
public enum VaccineType {

    PFIZER("Pfizer"),
    JOHNSON("Johnson & Johnson"),
    MODERNA("Moderna");

    private final String name;

    VaccineType(String name) {
        this.name = name;
    }
}

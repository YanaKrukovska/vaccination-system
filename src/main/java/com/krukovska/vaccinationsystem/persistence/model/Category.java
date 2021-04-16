package com.krukovska.vaccinationsystem.persistence.model;

import lombok.Getter;

@Getter
public enum Category {

    MEDICAL_WORKER("Medical worker"),
    SOCIAL_WORKER("Social worker"),
    ELDERLY("Elderly");

    private final String name;

    Category(String name) {
        this.name = name;
    }
}

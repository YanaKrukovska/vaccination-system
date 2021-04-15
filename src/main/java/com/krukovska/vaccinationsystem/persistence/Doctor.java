package com.krukovska.vaccinationsystem.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "doctor")
public class Doctor extends Person {

    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false)
    private String unit;

}

package com.krukovska.vaccinationsystem.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "patient")
public class Patient extends Person {

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String emergencyContact;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vaccination> vaccinations;



}

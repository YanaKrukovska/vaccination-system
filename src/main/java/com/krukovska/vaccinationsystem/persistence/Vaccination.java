package com.krukovska.vaccinationsystem.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "vaccination")
public class Vaccination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VaccineType vaccineType;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date vaccinationDate;

    @Column(nullable = false)
    private int dozeNumber;

    @Column
    private String additionalInformation;

    @ManyToOne
    @JoinColumn(name = "doctor_id", foreignKey = @ForeignKey(name = "DOCTOR_ID_FK"))
    private Doctor doctor;


}

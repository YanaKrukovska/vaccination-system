package com.krukovska.vaccinationsystem.persistence.model;

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
@Entity(name = "queue")
public class VaccinationQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", foreignKey = @ForeignKey(name = "PATIENT_ID_FK"))
    private Patient patient;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date applicationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date vaccinationDate;

}

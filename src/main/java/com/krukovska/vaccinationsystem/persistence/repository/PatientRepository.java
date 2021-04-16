package com.krukovska.vaccinationsystem.persistence.repository;

import com.krukovska.vaccinationsystem.persistence.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient getById(Long id);
    int countById(Long id);

}

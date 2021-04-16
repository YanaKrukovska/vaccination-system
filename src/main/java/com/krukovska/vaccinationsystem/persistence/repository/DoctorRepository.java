package com.krukovska.vaccinationsystem.persistence.repository;

import com.krukovska.vaccinationsystem.persistence.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor getById(Long doctorId);
}

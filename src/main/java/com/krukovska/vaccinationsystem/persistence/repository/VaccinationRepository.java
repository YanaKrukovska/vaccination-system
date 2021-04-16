package com.krukovska.vaccinationsystem.persistence.repository;

import com.krukovska.vaccinationsystem.persistence.model.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccinationRepository extends JpaRepository<Vaccination, Long> {
}

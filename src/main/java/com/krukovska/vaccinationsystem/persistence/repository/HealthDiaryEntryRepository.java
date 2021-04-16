package com.krukovska.vaccinationsystem.persistence.repository;

import com.krukovska.vaccinationsystem.persistence.model.HealthDiaryEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthDiaryEntryRepository extends JpaRepository<HealthDiaryEntry, Long> {

    HealthDiaryEntry getById(Long id);

}

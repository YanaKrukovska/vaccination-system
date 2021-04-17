package com.krukovska.vaccinationsystem.persistence.repository;

import com.krukovska.vaccinationsystem.persistence.model.HealthDiaryEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HealthDiaryEntryRepository extends JpaRepository<HealthDiaryEntry, Long> {

    HealthDiaryEntry getById(Long id);

    List<HealthDiaryEntry> getAllByTemperatureIsGreaterThanEqual(Double temperature);

}

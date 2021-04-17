package com.krukovska.vaccinationsystem.persistence.repository;

import com.krukovska.vaccinationsystem.persistence.model.Category;
import com.krukovska.vaccinationsystem.persistence.model.HealthDiaryEntry;
import com.krukovska.vaccinationsystem.persistence.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient getById(Long id);

    Patient getByUsername(String username);

    int countById(Long id);

    List<Patient> findDistinctByHealthDiaryEntriesIn(List<HealthDiaryEntry> healthDiaryEntries);

    List<Patient> findAllByCategory(Category category);
}

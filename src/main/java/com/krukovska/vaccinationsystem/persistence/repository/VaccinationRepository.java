package com.krukovska.vaccinationsystem.persistence.repository;

import com.krukovska.vaccinationsystem.persistence.model.Vaccination;
import com.krukovska.vaccinationsystem.persistence.model.VaccineStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VaccinationRepository extends JpaRepository<Vaccination, Long> {

    @Query("SELECT v.vaccineType as vaccineType, count(v.id) as count FROM vaccination v GROUP BY v.vaccineType")
    List<VaccineStatistic> countEveryVaccineUse();


}

package com.krukovska.vaccinationsystem.persistence.repository;

import com.krukovska.vaccinationsystem.persistence.model.Patient;
import com.krukovska.vaccinationsystem.persistence.model.VaccinationQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface VaccinationQueueRepository extends JpaRepository<VaccinationQueue, Long> {

    VaccinationQueue getById(Long requestId);

    @Query("select q from queue q where q.vaccinationDate >= :currentDateTime")
    List<VaccinationQueue> getAllWithVaccinationDateAfter(@Param("currentDateTime") Date currentDate);

    @Query("select q from queue q where q.vaccinationDate < :currentDateTime")
    List<VaccinationQueue> getAllWithVaccinationDateBefore(@Param("currentDateTime") Date currentDate);

    List<VaccinationQueue> findAllByVaccinationDateIsNull();

    List<VaccinationQueue> findAllByPatientIdAndVaccinationDateIsNull(Long patientId);

    @Query("select q from queue q where q.vaccinationDate >= :currentDateTime and q.patient = :patient")
    List<VaccinationQueue> getAllByPatientAndVaccinationDateAfter(Patient patient, @Param("currentDateTime") Date currentDate);

    @Query("select q from queue q where q.vaccinationDate < :currentDateTime and q.patient = :patient")
    List<VaccinationQueue> getAllByPatientAndVaccinationDateBefore(Patient patient, @Param("currentDateTime") Date currentDate);
}

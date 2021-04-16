package com.krukovska.vaccinationsystem.service;

import com.krukovska.vaccinationsystem.persistence.model.Patient;
import com.krukovska.vaccinationsystem.persistence.repository.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public int countVaccinations(Long patientId) {
        return patientRepository.countById(patientId);
    }

    public Patient findPatientById(Long patientId) {
        return patientRepository.getById(patientId);
    }

    public void updatePatient(Patient patient) {
        patientRepository.save(patient);
    }
}

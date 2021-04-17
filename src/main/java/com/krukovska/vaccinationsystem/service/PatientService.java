package com.krukovska.vaccinationsystem.service;

import com.krukovska.vaccinationsystem.persistence.model.Category;
import com.krukovska.vaccinationsystem.persistence.model.HealthDiaryEntry;
import com.krukovska.vaccinationsystem.persistence.model.Patient;
import com.krukovska.vaccinationsystem.persistence.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class PatientService {

    public static final double TEMPERATURE_THRESHOLD = 37.2;
    private final PatientRepository patientRepository;
    private final HealthDiaryEntryService healthDiaryEntryService;

    public PatientService(PatientRepository patientRepository, HealthDiaryEntryService healthDiaryEntryService) {
        this.patientRepository = patientRepository;
        this.healthDiaryEntryService = healthDiaryEntryService;
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

    public Patient findPatientByUsername(String username) {
        return patientRepository.getByUsername(username);
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public List<Patient> findAllWithHighTemperature() {
        List<HealthDiaryEntry> entries = healthDiaryEntryService.findAllEntriesWithHighTemperature(TEMPERATURE_THRESHOLD);
        return new LinkedList<>(patientRepository.findDistinctByHealthDiaryEntriesIn(entries));
    }

    public List<Patient> findAllElderlyPatients() {
        return new LinkedList<>(patientRepository.findAllByCategory(Category.ELDERLY));
    }
}

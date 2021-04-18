package com.krukovska.vaccinationsystem.service;

import com.krukovska.vaccinationsystem.persistence.model.Category;
import com.krukovska.vaccinationsystem.persistence.model.HealthDiaryEntry;
import com.krukovska.vaccinationsystem.persistence.model.Patient;
import com.krukovska.vaccinationsystem.persistence.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {
    @InjectMocks
    private PatientService underTest;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private HealthDiaryEntryService healthDiaryEntryService;

    @Test
    void countVaccinations() {
        underTest.countVaccinations(1L);
        verify(patientRepository, times(1)).countById(1L);
    }

    @Test
    void findPatientById() {
        underTest.findPatientById(1L);
        verify(patientRepository, times(1)).getById(1L);
    }

    @Test
    void updatePatient() {
        Patient p = new Patient();
        underTest.updatePatient(p);
        verify(patientRepository, times(1)).save(p);
    }

    @Test
    void findPatientByUsername() {
        underTest.findPatientByUsername("name");
        verify(patientRepository, times(1)).getByUsername("name");
    }

    @Test
    void findAll() {
        underTest.findAll();
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void findAllWithHighTemperature() {
        HealthDiaryEntry e1 = new HealthDiaryEntry(1L, new Date(), 38.8);
        HealthDiaryEntry e2 = new HealthDiaryEntry(2L, new Date(), 39.8);

        List<HealthDiaryEntry> entries = Arrays.asList(e1, e2);
        when(healthDiaryEntryService.findAllEntriesWithHighTemperature(37.2)).thenReturn(entries);

        underTest.findAllWithHighTemperature();
        verify(patientRepository,times(1)).findDistinctByHealthDiaryEntriesIn(entries);
    }

    @Test
    void findAllElderlyPatients() {
        underTest.findAllElderlyPatients();
        verify(patientRepository, times(1)).findAllByCategory(Category.ELDERLY);
    }
}
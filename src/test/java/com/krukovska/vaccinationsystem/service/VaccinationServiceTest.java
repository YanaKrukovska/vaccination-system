package com.krukovska.vaccinationsystem.service;

import com.krukovska.vaccinationsystem.persistence.model.Doctor;
import com.krukovska.vaccinationsystem.persistence.model.Response;
import com.krukovska.vaccinationsystem.persistence.model.Vaccination;
import com.krukovska.vaccinationsystem.persistence.model.VaccineType;
import com.krukovska.vaccinationsystem.persistence.repository.VaccinationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VaccinationServiceTest {
    @InjectMocks
    private VaccinationService underTest;

    @Mock
    VaccinationRepository repository;




    @Test
    void addVaccinationDoctorIsNull() {
        Vaccination v = new Vaccination();
        Response<Vaccination> result = underTest.addVaccination(v);
        assertEquals(1, result.getErrors().size());
        assertEquals("Doctor can't be null", result.getErrors().get(0));
        verify(repository, never()).save(any());
    }

    @Test
    void addVaccinationDateIsNull() {
        Vaccination v = new Vaccination();
        v.setDoctor(new Doctor());
        Response<Vaccination> result = underTest.addVaccination(v);
        assertEquals(1, result.getErrors().size());
        assertEquals("Vaccination date can't be null", result.getErrors().get(0));
        verify(repository, never()).save(any());
    }

    @Test
    void addVaccinationVaccineTypeIsNull() {
        Vaccination v = new Vaccination();
        v.setDoctor(new Doctor());
        v.setVaccinationDate(new Date());
        Response<Vaccination> result = underTest.addVaccination(v);
        assertEquals(1, result.getErrors().size());
        assertEquals("Vaccine type can't be null", result.getErrors().get(0));
        verify(repository, never()).save(any());
    }


    @Test
    void addVaccination() {
        Vaccination v = new Vaccination();
        v.setDoctor(new Doctor());
        v.setVaccinationDate(new Date());
        v.setVaccineType(VaccineType.JOHNSON);
        when(repository.save(v)).thenReturn(v);
        Response<Vaccination> result = underTest.addVaccination(v);
        assertEquals(0, result.getErrors().size());
        assertEquals(v, result.getObject());
        verify(repository, times(1)).save(any());

    }

    @Test
    void getTotalAmount() {
        underTest.getTotalAmount();
        verify(repository, times(1)).count();
    }

    @Test
    void getVaccineStatistics() {
        underTest.getVaccineStatistics();
        verify(repository, times(1)).countEveryVaccineUse();

    }
}
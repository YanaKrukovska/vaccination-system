package com.krukovska.vaccinationsystem.service;

import com.krukovska.vaccinationsystem.persistence.model.Patient;
import com.krukovska.vaccinationsystem.persistence.model.VaccinationQueue;
import com.krukovska.vaccinationsystem.persistence.repository.VaccinationQueueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VaccinationQueueServiceTest {

    @InjectMocks
    private VaccinationQueueService underTest;

    @Mock
    VaccinationQueueRepository repository;

    @Captor
    ArgumentCaptor<Date> dateCaptor;

    @Test
    void createNewRequest() {
        VaccinationQueue item = new VaccinationQueue();
        underTest.createNewRequest(item);
        verify(repository, times(1)).save(item);
    }

    @Test
    void getAllPendingRequests() {
        underTest.getAllPendingRequests();
        verify(repository, times(1)).findAllByVaccinationDateIsNull();
    }

    @Test
    void deleteRequest() {
        VaccinationQueue item = new VaccinationQueue();
        underTest.deleteRequest(item);
        verify(repository, times(1)).delete(item);
    }

    @Test
    void findRequestById() {
        underTest.findRequestById(1L);
        verify(repository, times(1)).getById(1L);
    }

    @Test
    void updateRequest() {
        VaccinationQueue item = new VaccinationQueue();
        underTest.updateRequest(item);
        verify(repository, times(1)).save(item);
    }



    @Test
    void getAllUpcomingVaccinations() {
        underTest.getAllUpcomingVaccinations();
        verify(repository, times(1)).getAllWithVaccinationDateAfter(dateCaptor.capture());
        Date date = dateCaptor.getValue();
        assertTrue(convertDate(date).isEqual(LocalDate.now()));

    }

    @Test
    void getAllVaccinationsForToday() {
        underTest.getAllVaccinationsForToday();
        verify(repository, times(1)).getAllByVaccinationDateBetween(dateCaptor.capture(), dateCaptor.capture());
        assertTrue(convertDate(dateCaptor.getAllValues().get(0)).isEqual(LocalDate.now()));
        assertTrue(convertDate(dateCaptor.getAllValues().get(1)).isEqual(LocalDate.now().plusDays(1L)));

    }

    @Test
    void getAllPastVaccinations() {
        underTest.getAllPastVaccinations();
        verify(repository, times(1)).getAllWithVaccinationDateBefore(dateCaptor.capture());
        assertTrue(convertDate(dateCaptor.getValue()).isEqual(LocalDate.now()));
    }



    @Test
    void findAllPendingRequestsByPatientId() {
        underTest.findAllPendingRequestsByPatientId(1L);
        verify(repository, times(1)).findAllByPatientIdAndVaccinationDateIsNull(1L);
    }

    @Test
    void findAllPatientPastVaccinations() {
        Patient patient = new Patient();
        patient.setId(1L);

        underTest.findAllPatientPastVaccinations(patient);
        verify(repository, times(1)).getAllByPatientAndVaccinationDateBefore(eq(patient), dateCaptor.capture());
        assertTrue(convertDate(dateCaptor.getValue()).isEqual(LocalDate.now()));
    }

    @Test
    void findAllPatientUpcomingVaccinations() {
        Patient patient = new Patient();
        patient.setId(1L);

        underTest.findAllPatientUpcomingVaccinations(patient);
        verify(repository, times(1)).getAllByPatientAndVaccinationDateAfter(eq(patient), dateCaptor.capture());
        assertTrue(convertDate(dateCaptor.getValue()).isEqual(LocalDate.now()));
    }


    private LocalDate convertDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
package com.krukovska.vaccinationsystem.service;

import com.krukovska.vaccinationsystem.persistence.model.HealthDiaryEntry;
import com.krukovska.vaccinationsystem.persistence.repository.HealthDiaryEntryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HealthDiaryEntryServiceTest {

    @InjectMocks
    private HealthDiaryEntryService underTest;

    @Mock
    private  HealthDiaryEntryRepository repository;

    @Test
    void findById() {

        underTest.findById(1L);
        verify(repository, times(1)).getById(1L);
    }

    @Test
    void updateEntry() {
        HealthDiaryEntry e = new HealthDiaryEntry();
        underTest.updateEntry(e);
        verify(repository, times(1)).save(e);
    }

    @Test
    void findAllEntriesWithHighTemperature() {
        underTest.findAllEntriesWithHighTemperature(38.0);
        verify(repository, times(1)).getAllByTemperatureIsGreaterThanEqual(38.0);
    }
}
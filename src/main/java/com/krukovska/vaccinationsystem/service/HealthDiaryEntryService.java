package com.krukovska.vaccinationsystem.service;

import com.krukovska.vaccinationsystem.persistence.model.HealthDiaryEntry;
import com.krukovska.vaccinationsystem.persistence.repository.HealthDiaryEntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthDiaryEntryService {

    private final HealthDiaryEntryRepository healthDiaryEntryRepository;

    public HealthDiaryEntryService(HealthDiaryEntryRepository healthDiaryEntryRepository) {
        this.healthDiaryEntryRepository = healthDiaryEntryRepository;
    }

    public HealthDiaryEntry findById(Long entryId) {
        return healthDiaryEntryRepository.getById(entryId);
    }

    public void updateEntry(HealthDiaryEntry entry) {
        healthDiaryEntryRepository.save(entry);
    }

    public List<HealthDiaryEntry> findAllEntriesWithHighTemperature(Double temperature) {
        return healthDiaryEntryRepository.getAllByTemperatureIsGreaterThanEqual(temperature);
    }
}

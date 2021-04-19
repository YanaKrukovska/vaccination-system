package com.krukovska.vaccinationsystem.service;

import com.krukovska.vaccinationsystem.persistence.model.Response;
import com.krukovska.vaccinationsystem.persistence.model.Vaccination;
import com.krukovska.vaccinationsystem.persistence.model.VaccineStatistic;
import com.krukovska.vaccinationsystem.persistence.repository.VaccinationRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class VaccinationService {

    private final VaccinationRepository vaccinationRepository;

    public VaccinationService(VaccinationRepository vaccinationRepository) {
        this.vaccinationRepository = vaccinationRepository;
    }

    public Response<Vaccination> addVaccination(Vaccination vaccination) {

        Response<Vaccination> vaccinationValidationResults = validateVaccination(vaccination);
        if (!vaccinationValidationResults.isOkay()) {
            return new Response<>(null, vaccinationValidationResults.getErrors());
        }

        return new Response<>(vaccinationRepository.save(vaccination), new LinkedList<>());
    }

    private Response<Vaccination> validateVaccination(Vaccination vaccination) {
        List<String> errors = new LinkedList<>();
        if (vaccination.getDoctor() == null) {
            errors.add("Doctor can't be null");
        }

        if (vaccination.getVaccinationDate() == null) {
            errors.add("Vaccination date can't be null");
        }

        if (vaccination.getVaccineType() == null) {
            errors.add("Vaccine type can't be null");
        }

        return new Response<>(vaccination, errors);
    }

    public long getTotalAmount() {
        return vaccinationRepository.count();
    }

    public List<VaccineStatistic> getVaccineStatistics() {
        return vaccinationRepository.countEveryVaccineUse();
    }
}

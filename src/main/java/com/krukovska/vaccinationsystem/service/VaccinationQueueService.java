package com.krukovska.vaccinationsystem.service;

import com.krukovska.vaccinationsystem.persistence.model.VaccinationQueue;
import com.krukovska.vaccinationsystem.persistence.repository.VaccinationQueueRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VaccinationQueueService {

    private final VaccinationQueueRepository vaccinationQueueRepository;

    public VaccinationQueueService(VaccinationQueueRepository vaccinationQueueRepository) {
        this.vaccinationQueueRepository = vaccinationQueueRepository;
    }

    public void createNewRequest(VaccinationQueue vaccinationQueue) {
        vaccinationQueueRepository.save(vaccinationQueue);
    }

    public List<VaccinationQueue> getAllPendingRequests() {
        return vaccinationQueueRepository.findAllByVaccinationDateIsNull();
    }

    public void deleteRequest(VaccinationQueue vaccinationQueue) {
        vaccinationQueueRepository.delete(vaccinationQueue);
    }

    public VaccinationQueue findRequestById(Long requestId) {
        return vaccinationQueueRepository.getById(requestId);
    }

    public void updateRequest(VaccinationQueue vaccinationQueue) {
        vaccinationQueueRepository.save(vaccinationQueue);
    }

    public List<VaccinationQueue> getAllUpcomingVaccinations() {
        return vaccinationQueueRepository.getAllWithVaccinationDateAfter(new Date());
    }

    public List<VaccinationQueue> getAllPastVaccinations() {
        return vaccinationQueueRepository.getAllWithVaccinationDateBefore(new Date());
    }
}
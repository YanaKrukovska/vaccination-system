package com.krukovska.vaccinationsystem.service;

import com.krukovska.vaccinationsystem.persistence.model.Doctor;
import com.krukovska.vaccinationsystem.persistence.repository.DoctorRepository;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }
    public Doctor findById(Long doctorId){
        return doctorRepository.getById(doctorId);
    }
}

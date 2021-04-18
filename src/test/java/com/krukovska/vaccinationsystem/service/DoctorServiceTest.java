package com.krukovska.vaccinationsystem.service;

import com.krukovska.vaccinationsystem.persistence.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @InjectMocks
    private DoctorService underTest;

    @Mock
    private DoctorRepository doctorRepository;

    @BeforeEach
    public void setUp() {
        underTest = new DoctorService(doctorRepository);

    }

    @Test
    void findById() {
        underTest.findById(1L);
        verify(doctorRepository, times(1)).getById(1L);
    }

    @Test
    void findAll() {
        underTest.findAll();
        verify(doctorRepository, times(1)).findAll();
    }

}
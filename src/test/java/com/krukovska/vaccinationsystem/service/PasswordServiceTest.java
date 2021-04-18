package com.krukovska.vaccinationsystem.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PasswordServiceTest {

    @InjectMocks
    private PasswordService underTest;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void encodePassword() {
        underTest.encodePassword("1");
        verify(bCryptPasswordEncoder, times(1)).encode("1");
    }

    @Test
    void compareRawAndEncodedPassword() {
        underTest.compareRawAndEncodedPassword("1","2");
        verify(bCryptPasswordEncoder, times(1)).matches("1","2");
    }
}
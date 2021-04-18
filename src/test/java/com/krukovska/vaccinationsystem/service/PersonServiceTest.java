package com.krukovska.vaccinationsystem.service;

import com.krukovska.vaccinationsystem.persistence.model.Patient;
import com.krukovska.vaccinationsystem.persistence.model.Person;
import com.krukovska.vaccinationsystem.persistence.model.Response;
import com.krukovska.vaccinationsystem.persistence.model.Role;
import com.krukovska.vaccinationsystem.persistence.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @InjectMocks
    private PersonService underTest;

    @Mock
    private PasswordService passwordService;
    @Mock
    private PersonRepository personRepository;

    @BeforeEach
    public void setUp() {
        underTest = new PersonService(passwordService, personRepository);

    }

    @Test
    void findUserByUsername() {
        when(personRepository.findPersonByUsernameIgnoreCase("username")).thenReturn(createPerson());
        Assertions.assertNotNull(underTest.findUserByUsername("username"));

    }

    @Test
    void loadUserByUsernameNotExist() {
        when(personRepository.findPersonByUsernameIgnoreCase("notexist")).thenReturn(null);
        Assertions.assertThrows(UsernameNotFoundException.class, () ->
            underTest.loadUserByUsername("notexist")
        );
    }

    @Test
    void loadUserByUsernameWhenExist() {
        when(personRepository.findPersonByUsernameIgnoreCase("username")).thenReturn(createPerson());
        Assertions.assertNotNull(underTest.loadUserByUsername("username"));
    }


    @Test
    void saveFailedWhenUserNameEmpty() {
        Patient patient = createPatient();
        patient.setUsername(null);
        Response<Patient> result = underTest.save(patient);
        assertEquals(1, result.getErrors().size());
        assertEquals("Username can't be empty", result.getErrors().get(0));
    }

    @Test
    void saveFailedWhenPasswordIsEmpty() {
        Patient patient = createPatient();
        patient.setPassword(null);
        Response<Patient> result = underTest.save(patient);
        assertEquals(1, result.getErrors().size());
        assertEquals("Password can't be empty", result.getErrors().get(0));
    }

    @Test
    void saveFailedWhenContactIsEmpty() {
        Patient patient = createPatient();
        patient.setEmergencyContact(null);
        Response<Patient> result = underTest.save(patient);
        assertEquals(1, result.getErrors().size());
        assertEquals("Emergency contact can't be empty", result.getErrors().get(0));
    }

    @Test
    void saveFailedWhenAddressIsEmpty() {
        Patient patient = createPatient();
        patient.setAddress(null);
        Response<Patient> result = underTest.save(patient);
        assertEquals(1, result.getErrors().size());
        assertEquals("Address can't be empty", result.getErrors().get(0));
    }


    @Test
    void saveFailedWhenUserExists() {

        when(personRepository.findPersonByUsernameIgnoreCase("username")).thenReturn(createPerson());

        Response<Patient> result = underTest.save(createPatient());

        assertEquals(1, result.getErrors().size());
        assertEquals("Username is already taken", result.getErrors().get(0));
    }

    @Test
    void saveFailedWhenUserNotExist() {

        Patient resultPatient = createPatient();
        resultPatient.setId(1L);
        when(personRepository.findPersonByUsernameIgnoreCase("username")).thenReturn(null);
        when(passwordService.encodePassword("1")).thenReturn("2222");
        Response<Patient> result = underTest.save(createPatient());

        assertEquals(0, result.getErrors().size());
        assertEquals(2L, result.getObject().getRole().getId());
        assertEquals("2222", result.getObject().getPassword());
        assertEquals("ROLE_PATIENT", result.getObject().getRole().getName());

    }





    private Person createPerson() {
        Person person = new Person();
        person.setUsername("username");
        person.setFullName("full name");
        person.setPhoneNumber("4585885");
        person.setRole(new Role(1L, "PATIENT_ROLE"));

        return person;
    }

    private Patient createPatient() {
        Patient patient = new Patient();
        patient.setUsername("username");
        patient.setFullName("full name");
        patient.setPhoneNumber("4585885");
        patient.setRole(new Role(1L, "PATIENT_ROLE"));
        patient.setAddress("address");
        patient.setEmergencyContact("contact");
        patient.setPassword("1");

        return patient;
    }
}
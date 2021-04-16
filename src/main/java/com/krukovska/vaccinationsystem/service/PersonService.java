package com.krukovska.vaccinationsystem.service;

import com.krukovska.vaccinationsystem.persistence.model.Patient;
import com.krukovska.vaccinationsystem.persistence.model.Person;
import com.krukovska.vaccinationsystem.persistence.model.Response;
import com.krukovska.vaccinationsystem.persistence.model.Role;
import com.krukovska.vaccinationsystem.persistence.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class PersonService implements UserDetailsService {

    private final PasswordService passwordService;
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PasswordService passwordService, PersonRepository personRepository) {
        this.passwordService = passwordService;
        this.personRepository = personRepository;
    }

    public Person findUserByUsername(String username) {
        return personRepository.findPersonByUsernameIgnoreCase(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findPersonByUsernameIgnoreCase(username);

        if (person == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return person;
    }

    public Response<Patient> save(Patient patient) {

        List<String> errors = validatePatient(patient).getErrors();
        if (!errors.isEmpty()) {
            return new Response<>(patient, errors);
        }

        Person checkUsername = personRepository.findPersonByUsernameIgnoreCase(patient.getUsername());

        if (checkUsername != null) {
            return new Response<>(patient, Collections.singletonList("Username is already taken"));
        }

        patient.setRole(new Role(1L, "ROLE_PATIENT"));
        patient.setPassword(passwordService.encodePassword(patient.getPassword()));
        personRepository.save(patient);
        return new Response<>(patient, new LinkedList<>());
    }

    private Response<Patient> validatePatient(Patient patient) {

        if (patient.getUsername() == null || patient.getUsername().isBlank()) {
            return new Response<>(patient, Collections.singletonList("Username can't be empty"));
        }

        if (patient.getPassword() == null || patient.getPassword().isBlank()) {
            return new Response<>(patient, Collections.singletonList("Password can't be empty"));
        }

        if (patient.getEmergencyContact() == null || patient.getEmergencyContact().isBlank()) {
            return new Response<>(patient, Collections.singletonList("Emergency contact can't be empty"));
        }

        if (patient.getAddress() == null || patient.getAddress().isBlank()) {
            return new Response<>(patient, Collections.singletonList("Address can't be empty"));
        }

        return new Response<>(patient, new LinkedList<>());
    }
}

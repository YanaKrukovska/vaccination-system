package com.krukovska.vaccinationsystem.controller;

import com.krukovska.vaccinationsystem.service.DoctorService;
import com.krukovska.vaccinationsystem.service.PatientService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationControllerTest {

    public static final String BASE_URL = "http://localhost:";
    public static final String TEST_USER = "OlegVynnyk";
    public static final String USER_PASS = "1";
    @LocalServerPort
    private Integer port;

    @MockBean
    private PatientService patientService;
    @MockBean
    private DoctorService doctorService;

    @Test
    void shouldGetAllPatientCritical() {

        RestAssured
                .given().auth().form(TEST_USER, USER_PASS)
                .when()
                .get(BASE_URL + port + "/patient/critical")
                .then()
                .statusCode(200)
                .extract();

        verify(patientService, times(1)).findAllWithHighTemperature();
    }



    @Test
    void shouldGetAllPatientAll() {

        RestAssured
                .given().auth().form(TEST_USER, USER_PASS)
                .when()
                .get(BASE_URL + port + "/patient/all")
                .then()
                .statusCode(200)
                .extract();

        verify(patientService, times(1)).findAll();

    }

    @Test
    void shouldGetAllPatientElderly() {

        RestAssured
                .given().auth().form(TEST_USER, USER_PASS)
                .when()
                .get(BASE_URL + port + "/patient/elderly")
                .then()
                .statusCode(200)
                .extract();

        verify(patientService, times(1)).findAllElderlyPatients();

    }


    @Test
    void shouldGetAllDoctors() {

        RestAssured
                .when()
                .get(BASE_URL + port + "/doctor/all")
                .then()
                .statusCode(200)
                .extract();

        verify(doctorService, times(1)).findAll();

    }

}
package com.krukovska.vaccinationsystem.controller;

import com.krukovska.vaccinationsystem.service.*;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationControllerTest {

    @LocalServerPort
    private Integer port;
    @MockBean
    private VaccinationQueueService vaccinationQueueService;
    @MockBean
    private HealthDiaryEntryService healthDiaryEntryService;
    @MockBean
    private VaccinationService vaccinationService;
    @MockBean
    private PatientService patientService;
    @MockBean
    private DoctorService doctorService;



    @Test
    void shouldGetAllPatientElderly() {

        ExtractableResponse<Response> resp = RestAssured
                .given().auth().form("OlegVynnyk", "1")
                .when()
                .get("http://localhost:" + port + "/patient/elderly")
                .then()
                .statusCode(200)
                .extract();

        verify(patientService, times(1)).findAllElderlyPatients();

    }



    @Test
    void shouldGetAllDoctors() {

        RestAssured
                .when()
                .get("http://localhost:" + port + "/doctor/all")
                .then()
                .statusCode(200)
                .extract();

        verify(doctorService, times(1)).findAll();

    }

}
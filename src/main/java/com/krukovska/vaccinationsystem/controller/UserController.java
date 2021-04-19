package com.krukovska.vaccinationsystem.controller;

import com.krukovska.vaccinationsystem.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.krukovska.vaccinationsystem.util.LabelConstants.PATIENTS_LABEL;

@Controller
public class UserController {
    private final PatientService patientService;
    private final DoctorService doctorService;

    public UserController(PatientService patientService, DoctorService doctorService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @GetMapping("/patient/all")
    public String getAllPatients(Model model) {
        model.addAttribute(PATIENTS_LABEL, patientService.findAll());
        return PATIENTS_LABEL;
    }

    @GetMapping("/patient/critical")
    public String getAllCriticalPatients(Model model) {
        model.addAttribute(PATIENTS_LABEL, patientService.findAllWithHighTemperature());
        return PATIENTS_LABEL;
    }

    @GetMapping("/patient/elderly")
    public String getAllElderlyPatients(Model model) {
        model.addAttribute(PATIENTS_LABEL, patientService.findAllElderlyPatients());
        return PATIENTS_LABEL;
    }

    @GetMapping("/doctor/all")
    public String getAllDoctors(Model model) {
        model.addAttribute("doctors", doctorService.findAll());
        return "doctors";
    }

}

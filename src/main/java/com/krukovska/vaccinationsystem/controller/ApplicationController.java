package com.krukovska.vaccinationsystem.controller;

import com.krukovska.vaccinationsystem.persistence.model.Patient;
import com.krukovska.vaccinationsystem.persistence.model.Person;
import com.krukovska.vaccinationsystem.service.VaccinationQueueService;
import com.krukovska.vaccinationsystem.service.VaccinationService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.krukovska.vaccinationsystem.util.DateUtil.*;
import static com.krukovska.vaccinationsystem.util.LabelConstants.*;

@Controller
public class ApplicationController {

    private final VaccinationQueueService vaccinationQueueService;
    private final VaccinationService vaccinationService;



    public ApplicationController(VaccinationQueueService vaccinationQueueService,  VaccinationService vaccinationService)  {
        this.vaccinationQueueService = vaccinationQueueService;
        this.vaccinationService = vaccinationService;
    }

    @GetMapping("/")
    public String getMainPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return "index";
        }

        Person person = (Person) authentication.getPrincipal();
        model.addAttribute("userId", person.getId());

        if (person.getRole().getName().equals("ROLE_DOCTOR")) {
            model.addAttribute("totalVaccinations", vaccinationService.getTotalAmount());
            model.addAttribute("vaccineStats", vaccinationService.getVaccineStatistics());
            model.addAttribute("pendingAmount", vaccinationQueueService.getAllPendingRequests().size());
            model.addAttribute("upcomingAmount", vaccinationQueueService.getAllUpcomingVaccinations().size());
            model.addAttribute("todayAmount", vaccinationQueueService.getAllVaccinationsForToday().size());
        }
        return "index";
    }



    @GetMapping("/profile")
    public String getPatientProfile(Model model) {
        Patient patient = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("pending", vaccinationQueueService.findAllPendingRequestsByPatientId(patient.getId()));
        model.addAttribute("past", vaccinationQueueService.findAllPatientPastVaccinations(patient));
        model.addAttribute("upcoming", vaccinationQueueService.findAllPatientUpcomingVaccinations(patient));
        return "patient";
    }

}

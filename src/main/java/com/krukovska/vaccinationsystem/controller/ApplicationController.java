package com.krukovska.vaccinationsystem.controller;

import com.krukovska.vaccinationsystem.persistence.model.*;
import com.krukovska.vaccinationsystem.service.DoctorService;
import com.krukovska.vaccinationsystem.service.PatientService;
import com.krukovska.vaccinationsystem.service.VaccinationService;
import com.krukovska.vaccinationsystem.util.DateUtil;
import com.krukovska.vaccinationsystem.service.VaccinationQueueService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@Controller
public class ApplicationController {

    private final VaccinationQueueService vaccinationQueueService;
    private final VaccinationService vaccinationService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public ApplicationController(VaccinationQueueService vaccinationQueueService, VaccinationService vaccinationService, PatientService patientService, DoctorService doctorService) {
        this.vaccinationQueueService = vaccinationQueueService;
        this.vaccinationService = vaccinationService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @GetMapping("/")
    public String getMainPage(Model model) {
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userId", person.getId());
        return "index";
    }

    @PostMapping("/request")
    @ResponseStatus(HttpStatus.OK)
    public void sendRequest() {
        Patient patient = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        VaccinationQueue vaccinationQueue = new VaccinationQueue(patient, new Date(), null);
        vaccinationQueueService.createNewRequest(vaccinationQueue);
    }

    @PostMapping("/request/delete")
    public String deleteRequest(@ModelAttribute("requestId") Long requestId) {
        VaccinationQueue vaccinationQueue = vaccinationQueueService.findRequestById(requestId);
        vaccinationQueueService.deleteRequest(vaccinationQueue);
        return "redirect:/queue/all";
    }

    @PostMapping("/request/accept")
    public String acceptRequest(@ModelAttribute("requestId") Long requestId) {
        VaccinationQueue vaccinationQueue = vaccinationQueueService.findRequestById(requestId);
        vaccinationQueue.setVaccinationDate(DateUtil.getTwoWeeksFromNow());
        vaccinationQueueService.updateRequest(vaccinationQueue);
        return "redirect:/queue/all";
    }

    @GetMapping("/queue/all")
    public String getAllRequests(Model model) {
        model.addAttribute("requests", vaccinationQueueService.getAllPendingRequests());
        return "requests";
    }

    @GetMapping("/queue/upcoming")
    public String getUpcomingVaccinations(Model model) {
        model.addAttribute("appointments", vaccinationQueueService.getAllUpcomingVaccinations());
        model.addAttribute("upcoming", true);
        return "queue";
    }

    @GetMapping("/queue/past")
    public String getPastVaccinations(Model model) {
        model.addAttribute("appointments", vaccinationQueueService.getAllPastVaccinations());
        model.addAttribute("upcoming", false);
        return "queue";
    }

    @GetMapping("/vaccination/add")
    public String editVaccination(@ModelAttribute("appointmentId") Long appointmentId, Model model) {
        VaccinationQueue vaccinationQueue = vaccinationQueueService.findRequestById(appointmentId);

        Vaccination vaccination = new Vaccination();
        vaccination.setVaccinationDate(vaccinationQueue.getVaccinationDate());

        int dozeNumber = patientService.countVaccinations(vaccinationQueue.getId());
        vaccination.setDozeNumber(dozeNumber + 1);

        Doctor doctor = (Doctor) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        vaccination.setDoctor(doctor);

        model.addAttribute("vaccination", vaccination);
        model.addAttribute("vaccines", VaccineType.values());
        model.addAttribute("patient", vaccinationQueue.getPatient());
        return "vaccination-add";
    }

    @PostMapping("/vaccination")
    public String addVaccination(@ModelAttribute("vaccination1") Vaccination vaccination,
                                 @ModelAttribute("patientId") Long patientId, @ModelAttribute("doctorId") Long doctorId,
                                 @ModelAttribute("vaccDate") String vaccinationDate,
                                 @ModelAttribute("vaccineNumber") Integer categoryNumber, Model model) {

        vaccination.setVaccineType(VaccineType.values()[categoryNumber]);
        vaccination.setVaccinationDate(DateUtil.convertStringToDate(vaccinationDate));
        vaccination.setDoctor(doctorService.findById(doctorId));

        Response<Vaccination> response = vaccinationService.addVaccination(vaccination);
        if (!response.isOkay()) {
            model.addAttribute("errors", response.getErrors());
            return "vaccination-add";
        }

        Patient patient = patientService.findPatientById(patientId);
        patient.getVaccinations().add(response.getObject());
        patientService.updatePatient(patient);

        return "redirect:/queue/past";
    }
}

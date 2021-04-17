package com.krukovska.vaccinationsystem.controller;

import com.krukovska.vaccinationsystem.persistence.model.*;
import com.krukovska.vaccinationsystem.service.*;
import com.krukovska.vaccinationsystem.util.DateUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Controller
public class ApplicationController {

    private final VaccinationQueueService vaccinationQueueService;
    private final HealthDiaryEntryService healthDiaryEntryService;
    private final VaccinationService vaccinationService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public ApplicationController(VaccinationQueueService vaccinationQueueService, HealthDiaryEntryService healthDiaryEntryService, VaccinationService vaccinationService, PatientService patientService, DoctorService doctorService) {
        this.vaccinationQueueService = vaccinationQueueService;
        this.healthDiaryEntryService = healthDiaryEntryService;
        this.vaccinationService = vaccinationService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @GetMapping("/")
    public String getMainPage(Model model) {
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

    @PostMapping("/appointment/cancel")
    public String cancelAppointment(@ModelAttribute("requestId") Long requestId) {
        VaccinationQueue vaccinationQueue = vaccinationQueueService.findRequestById(requestId);
        vaccinationQueueService.deleteRequest(vaccinationQueue);
        return "redirect:/profile";
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

    @GetMapping("/queue/today")
    public String getTodayVaccinations(Model model) {
        model.addAttribute("appointments", vaccinationQueueService.getAllVaccinationsForToday());
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

        int dozeNumber = patientService.countVaccinations(vaccinationQueue.getPatient().getId());
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


    @GetMapping("/profile")
    public String getPatientProfile(Model model) {
        Patient patient = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<VaccinationQueue> pendingRequests = vaccinationQueueService.findAllPendingRequestsByPatientId(patient.getId());
        List<VaccinationQueue> pastVaccinations = vaccinationQueueService.findAllPatientPastVaccinations(patient);
        List<VaccinationQueue> upcomingVaccinations = vaccinationQueueService.findAllPatientUpcomingVaccinations(patient);
        model.addAttribute("pending", pendingRequests);
        model.addAttribute("past", pastVaccinations);
        model.addAttribute("upcoming", upcomingVaccinations);
        return "patient";
    }

    @GetMapping("/diary/{patientUsername}")
    @PreAuthorize("#patientUsername == authentication.name " + "|| hasRole('ROLE_DOCTOR')")
    public String getTemperatureDiary(@PathVariable("patientUsername") String patientUsername, Model model) {
        Patient patient = patientService.findPatientByUsername(patientUsername);
        List<HealthDiaryEntry> entries = patient.getHealthDiaryEntries();
        entries.sort(Comparator.comparing(HealthDiaryEntry::getDate));

        model.addAttribute("diary", entries);
        return "diary";
    }

    @GetMapping("/diary/edit/{entryId}")
    public String editDiaryEntry(@PathVariable("entryId") Long entryId, Model model) {
        HealthDiaryEntry healthDiaryEntry = healthDiaryEntryService.findById(entryId);

        model.addAttribute("entry", healthDiaryEntry);
        model.addAttribute("date", healthDiaryEntry.getDate());
        model.addAttribute("temperature", healthDiaryEntry.getTemperature());
        return "entry-edit";
    }

    @PostMapping("/diary/edit/{entryId}")
    public String updateDiaryEntry(@PathVariable("entryId") Long entryId, @ModelAttribute("newDate") String date,
                                   @ModelAttribute("newTemperature") Double temperature) {

        HealthDiaryEntry healthDiaryEntry = healthDiaryEntryService.findById(entryId);
        healthDiaryEntry.setDate(DateUtil.convertStringToDate(date));
        healthDiaryEntry.setTemperature(temperature);
        healthDiaryEntryService.updateEntry(healthDiaryEntry);

        return "redirect:/diary/" + SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/diary/add")
    public String addDiaryEntry() {
        return "entry-add";
    }

    @PostMapping("/diary/add")
    public String updateDiaryEntry(@ModelAttribute("newDate") String date, @ModelAttribute("newTemperature") Double temperature) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Patient patient = patientService.findPatientByUsername(username);

        HealthDiaryEntry healthDiaryEntry = new HealthDiaryEntry();
        healthDiaryEntry.setDate(DateUtil.convertStringToDate(date));
        healthDiaryEntry.setTemperature(temperature);
        patient.getHealthDiaryEntries().add(healthDiaryEntry);
        patientService.updatePatient(patient);

        return "redirect:/diary/" + username;
    }

    @PostMapping("/entry/delete")
    public String deleteEntry(@ModelAttribute("entryId") Long entryId) {
        HealthDiaryEntry entry = healthDiaryEntryService.findById(entryId);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Patient patient = patientService.findPatientByUsername(username);
        patient.getHealthDiaryEntries().remove(entry);
        patientService.updatePatient(patient);
        return "redirect:/diary/" + username;
    }
}

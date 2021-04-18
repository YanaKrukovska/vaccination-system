package com.krukovska.vaccinationsystem.controller;

import com.krukovska.vaccinationsystem.persistence.model.*;
import com.krukovska.vaccinationsystem.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
        vaccinationQueue.setVaccinationDate(getDateInDaysFromNow(TWO_WEEKS_IN_DAYS));
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
        model.addAttribute(APPOINTMENTS_LABEL, vaccinationQueueService.getAllUpcomingVaccinations());
        model.addAttribute(UPCOMING_LABEL, true);
        return QUEUE_LABEL;
    }

    @GetMapping("/queue/today")
    public String getTodayVaccinations(Model model) {
        model.addAttribute(APPOINTMENTS_LABEL, vaccinationQueueService.getAllVaccinationsForToday());
        model.addAttribute("today", true);
        return QUEUE_LABEL;
    }

    @GetMapping("/queue/past")
    public String getPastVaccinations(Model model) {
        model.addAttribute(APPOINTMENTS_LABEL, vaccinationQueueService.getAllPastVaccinations());
        model.addAttribute(UPCOMING_LABEL, false);
        return QUEUE_LABEL;
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
        vaccination.setVaccinationDate(convertStringToDate(vaccinationDate));
        vaccination.setDoctor(doctorService.findById(doctorId));

        Response<Vaccination> response = vaccinationService.addVaccination(vaccination);
        if (!response.isOkay()) {
            model.addAttribute("errors", response.getErrors());
            return "vaccination-add";
        }

        Patient patient = patientService.findPatientById(patientId);
        patient.getVaccinations().add(response.getObject());
        patientService.updatePatient(patient);

        if (response.getObject().getDozeNumber() < 2) {
            vaccinationQueueService.createNewRequest(new VaccinationQueue(patient, new Date(),
                    getDateInDaysFromNow(SIX_WEEKS_IN_DAYS)));
        }

        return "redirect:/queue/past";
    }


    @GetMapping("/profile")
    public String getPatientProfile(Model model) {
        Patient patient = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("pending", vaccinationQueueService.findAllPendingRequestsByPatientId(patient.getId()));
        model.addAttribute("past", vaccinationQueueService.findAllPatientPastVaccinations(patient));
        model.addAttribute("upcoming", vaccinationQueueService.findAllPatientUpcomingVaccinations(patient));
        return "patient";
    }

    @GetMapping("/diary/{patientUsername}")
    @PreAuthorize("#patientUsername == authentication.name " + "|| hasRole('ROLE_DOCTOR')")
    public String getTemperatureDiary(@PathVariable("patientUsername") String patientUsername, Model model) {
        Patient patient = patientService.findPatientByUsername(patientUsername);
        List<HealthDiaryEntry> entries = patient.getHealthDiaryEntries();
        entries.sort(Comparator.comparing(HealthDiaryEntry::getDate));

        model.addAttribute("diary", entries);
        model.addAttribute("vaccines", patient.getVaccinations());
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
        healthDiaryEntry.setDate(convertStringToDate(date));
        healthDiaryEntry.setTemperature(temperature);
        healthDiaryEntryService.updateEntry(healthDiaryEntry);

        return REDIRECT_TO_DIARY_LABEL + SecurityContextHolder.getContext().getAuthentication().getName();
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
        healthDiaryEntry.setDate(convertStringToDate(date));
        healthDiaryEntry.setTemperature(temperature);
        patient.getHealthDiaryEntries().add(healthDiaryEntry);
        patientService.updatePatient(patient);

        return REDIRECT_TO_DIARY_LABEL + username;
    }

    @PostMapping("/entry/delete")
    public String deleteEntry(@ModelAttribute("entryId") Long entryId) {
        HealthDiaryEntry entry = healthDiaryEntryService.findById(entryId);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Patient patient = patientService.findPatientByUsername(username);
        patient.getHealthDiaryEntries().remove(entry);
        patientService.updatePatient(patient);
        return REDIRECT_TO_DIARY_LABEL + username;
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

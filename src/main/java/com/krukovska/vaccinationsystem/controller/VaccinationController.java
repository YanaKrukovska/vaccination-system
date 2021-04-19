package com.krukovska.vaccinationsystem.controller;

import com.krukovska.vaccinationsystem.persistence.model.*;
import com.krukovska.vaccinationsystem.service.DoctorService;
import com.krukovska.vaccinationsystem.service.PatientService;
import com.krukovska.vaccinationsystem.service.VaccinationQueueService;
import com.krukovska.vaccinationsystem.service.VaccinationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

import static com.krukovska.vaccinationsystem.util.DateUtil.*;
import static com.krukovska.vaccinationsystem.util.LabelConstants.*;

@Controller
public class VaccinationController {

    private final VaccinationQueueService vaccinationQueueService;

    private final VaccinationService vaccinationService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public VaccinationController(VaccinationQueueService vaccinationQueueService, VaccinationService vaccinationService, PatientService patientService, DoctorService doctorService) {
        this.vaccinationQueueService = vaccinationQueueService;

        this.vaccinationService = vaccinationService;
        this.patientService = patientService;
        this.doctorService = doctorService;
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

}

package com.krukovska.vaccinationsystem.controller;

import com.krukovska.vaccinationsystem.persistence.model.HealthDiaryEntry;
import com.krukovska.vaccinationsystem.persistence.model.Patient;
import com.krukovska.vaccinationsystem.service.HealthDiaryEntryService;
import com.krukovska.vaccinationsystem.service.PatientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Comparator;
import java.util.List;

import static com.krukovska.vaccinationsystem.util.DateUtil.convertStringToDate;
import static com.krukovska.vaccinationsystem.util.LabelConstants.REDIRECT_TO_DIARY_LABEL;

@Controller
public class DiaryController {

    private final HealthDiaryEntryService healthDiaryEntryService;
    private final PatientService patientService;


    public DiaryController(HealthDiaryEntryService healthDiaryEntryService, PatientService patientService) {
        this.healthDiaryEntryService = healthDiaryEntryService;
        this.patientService = patientService;

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





}

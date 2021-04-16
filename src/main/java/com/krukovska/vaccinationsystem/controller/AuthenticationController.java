package com.krukovska.vaccinationsystem.controller;

import com.krukovska.vaccinationsystem.persistence.model.Category;
import com.krukovska.vaccinationsystem.persistence.model.Patient;
import com.krukovska.vaccinationsystem.persistence.model.Person;
import com.krukovska.vaccinationsystem.persistence.model.Response;
import com.krukovska.vaccinationsystem.service.PasswordService;
import com.krukovska.vaccinationsystem.service.PersonService;
import com.krukovska.vaccinationsystem.util.DateUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class AuthenticationController {

    public static final String ERROR_LABEL = "error";
    public static final String LOGIN_LABEL = "login";

    private final PersonService personService;
    private final PasswordService passwordService;

    @Autowired
    public AuthenticationController(PersonService personService, PasswordService passwordService) {
        this.personService = personService;
        this.passwordService = passwordService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("person", new Person());
        model.addAttribute(ERROR_LABEL, null);
        return LOGIN_LABEL;
    }

    @PostMapping("/login-processing")
    public String loginUser(@ModelAttribute Person person, Model model) {

        Person foundUser = personService.findUserByUsername(person.getUsername());

        if (foundUser == null) {
            model.addAttribute(ERROR_LABEL, "User doesn't exists");
            return LOGIN_LABEL;
        }

        if (!passwordService.compareRawAndEncodedPassword(person.getPassword(), (foundUser.getPassword()))) {
            model.addAttribute(ERROR_LABEL, "Wrong password");
            return LOGIN_LABEL;
        }

        return "redirect:/";
    }

    @GetMapping("/signup")
    public String registration(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("birthDate", new Date());
        model.addAttribute("categories", Category.values());
        return "signup";
    }

    @SneakyThrows
    @PostMapping("/signup-processing")
    public String addUser(@ModelAttribute Patient patient, @ModelAttribute("birthday") String birthday,
                          @ModelAttribute("categoryNumber") Integer categoryNumber, Model model) {

        patient.setBirthDate(DateUtil.convertStringToDate(birthday));
        patient.setCategory(Category.values()[categoryNumber]);

        Response<Patient> errorList = personService.save(patient);
        if (!errorList.isOkay()) {
            model.addAttribute("errors", errorList.getErrors());
            return "signup";
        }

        return "redirect:/login";
    }
}

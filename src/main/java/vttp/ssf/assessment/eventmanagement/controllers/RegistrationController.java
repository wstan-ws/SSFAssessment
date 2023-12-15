package vttp.ssf.assessment.eventmanagement.controllers;

import java.text.ParseException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.models.EventRegister;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;

@Controller
@RequestMapping(path = "/registration")
public class RegistrationController {

    @Autowired
    RedisRepository redisRepo;

    @PostMapping(path = "/register")
    public String register(@Valid @ModelAttribute EventRegister register, BindingResult result, HttpSession session, Model model) throws ParseException {

        Event event = (Event) session.getAttribute("event");
        model.addAttribute("event", event);
        model.addAttribute("register", register);

        if (result.hasErrors()) {
            return "eventregister";
        }

        LocalDate startOfToday = LocalDate.now();
        long today = startOfToday.toEpochDay() * (1000 * 60 * 60 * 24);
        // System.out.printf(">>> Today: %s\n", startOfToday);
        // System.out.printf(">>> Today: %s\n", today);
        // System.out.printf(">>> birthDate: %s\n", register.getBirthDate().getTime());

        if (((today - register.getBirthDate().getTime()) / (1000 * 60 * 60 * 24 * 365)) < 21) {
            FieldError err = new FieldError("eventregister", "birthDate", "Age cannot be below 21");
            result.addError(err);
            model.addAttribute("error", err.getDefaultMessage());
            return "errorregistration";
        }

        if (register.getTicketsRequested() + event.getParticipants() > event.getEventSize()) {
            // System.out.printf(">>> Added: %s\n", register.getTicketsRequested() + event.getParticipants());
            // System.out.printf(">>> Event Size: %s\n", event.getEventSize());
            FieldError err = new FieldError("eventregister", "ticketsRequested", "Max occupancy reached");
            result.addError(err);
            model.addAttribute("error", err.getDefaultMessage());
            return "errorregistration";
        }

        System.out.printf(">>> %s\n", (today - register.getBirthDate().getTime()) / (1000 * 60 * 60 * 24 * 365));
        
        redisRepo.delEvent(event);
        
        event.setParticipants(event.getParticipants() + register.getTicketsRequested());

        redisRepo.addEvent(event);

        return "successregistration";
    }
}

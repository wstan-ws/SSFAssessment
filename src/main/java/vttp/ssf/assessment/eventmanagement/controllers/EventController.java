package vttp.ssf.assessment.eventmanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.models.EventRegister;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@Controller
@RequestMapping (path = "/events")
public class EventController {

	@Autowired
	private DatabaseService databaseSvc;

	@GetMapping(path = "/listing")
	public String displayEvents(Model model) {

		List<Event> list = databaseSvc.getAllEvents();

		model.addAttribute("list", list);

		return "listing";
	}

	@GetMapping(path = "/register/{eventId}")
	public String register(@PathVariable Integer eventId, Model model, HttpSession session) {

		EventRegister register = new EventRegister();

		Event event = databaseSvc.getEvent(eventId - 1);

		model.addAttribute("eventRegister", register);
		model.addAttribute("event", event);

		session.setAttribute("event", event);

		return "eventregister";
	}


}

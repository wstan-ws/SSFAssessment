package vttp.ssf.assessment.eventmanagement;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@SpringBootApplication
public class EventmanagementApplication implements CommandLineRunner {

	@Autowired
	private DatabaseService databaseSvc;

	@Autowired
	private RedisRepository redisRepo;

	public static void main(String[] args) {
		SpringApplication.run(EventmanagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		String fileName = "events.json";

		File file = new File(fileName);

		List<Event> events = databaseSvc.readFile(file);

		for (Event event : events) {
			System.out.printf(">>> Event: %s\n", event.getEventName());
			// System.out.printf(">>> Date: %s\n", event.getEventDate());
		}

		redisRepo.saveRecord(events);

		// System.out.printf(">>> %s\n", redisRepo.getNumberOfEvents());
		// System.out.printf(">>> Event: %s\n", redisRepo.getEvent(1));
	}
}

package vttp.ssf.assessment.eventmanagement.services;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;

@Service
public class DatabaseService {

    @Autowired
    RedisRepository redisRepo;
    
    private List<Event> events = new ArrayList<>();

    public List<Event> readFile(File file) throws Exception {

        try (JsonReader reader = Json.createReader(new FileReader(file))) {
            JsonArray array = reader.readArray();

            for (JsonValue value : array) {
                JsonObject object = value.asJsonObject();
                
                Integer eventId = object.getInt("eventId");

                String eventName = object.getString("eventName");

                Integer eventSize = object.getInt("eventSize");

                Long eventDate = Long.valueOf(object.get("eventDate").toString());

                Integer participants = object.getInt("participants");

                Event event = new Event(eventId, eventName, eventSize, eventDate, participants);

                events.add(event);
            }
        }

        return events;
    }

    public List<Event> getAllEvents() {

        List<Event> listOfEvents = redisRepo.getAllEvents();

        return listOfEvents;
    }

    public Event getEvent(Integer eventId) {

        Event event = redisRepo.getEvent(eventId);

        return event;
    }
}

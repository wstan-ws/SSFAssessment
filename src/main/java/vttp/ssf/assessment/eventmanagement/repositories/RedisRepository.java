package vttp.ssf.assessment.eventmanagement.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp.ssf.assessment.eventmanagement.models.Event;

@Repository
public class RedisRepository {

	@Autowired @Qualifier("redis")
	private RedisTemplate<String, Event> template;

	private ListOperations<String, Event> list;

	public void saveRecord(List<Event> events) {

		list = template.opsForList();
		list.rightPushAll("eventList", events);
		
	}

	public Integer getNumberOfEvents() {

		Integer size = Integer.parseInt(list.size("eventList").toString());

		return size;
	}


	public Event getEvent(Integer index) {

		Event event = list.index("eventList", index);

		return event;
	}

	public List<Event> getAllEvents() {
		
		List<Event> listofEvents = new ArrayList<>();

		for (int i = 0; i < getNumberOfEvents(); i++) {
			listofEvents.add(getEvent(i));
		}

		return listofEvents;
	}

	public void delEvent(Event event) {
		list.remove("eventList", 0, event);
	}

	public void addEvent(Event event) {
		list.rightPush("eventList", event);
	}
}

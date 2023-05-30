package org.example.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.discipline.Discipline;
import org.example.discipline.DisciplineDTO;
import org.example.discipline.DisciplineMapping;
import org.example.discipline.DisciplineRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class EventService {
    EventRepository eventRepository;
    EventMapping eventMapping;
    DisciplineRepository disciplineRepository;
    DisciplineMapping disciplineMapping;

    public void saveEvent(String disciplineId, String message){
        Discipline discipline = disciplineRepository.findDisciplinesByIdContaining(disciplineId).get(0);
        Event event = new Event();
        event.setDate(new Timestamp(new Date().getTime()));
        event.setDiscipline(discipline);
        event.setMessage(message);
        eventRepository.save(event);
    };

    public List<EventDTO> getEventsForUser(Long id) {
        return eventRepository.findEventsByUser_id(id).stream().map(eventMapping::mapToEventDTO).toList();
    }

    public List<DisciplineDTO> getDisciplinesForUser(Long id) {
        return disciplineRepository.findDisciplinesByUser_id(id).stream().map(disciplineMapping::mapToDisciplineDto).toList();
    }
}

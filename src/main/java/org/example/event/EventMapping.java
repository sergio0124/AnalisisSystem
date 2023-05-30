package org.example.event;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EventMapping {
    private ModelMapper mapper;

    //из entity в dto
    public EventDTO mapToEventDTO(Event event) {
        if (event == null) {
            return null;
        }

        EventDTO eventDTO = mapper.map(event, EventDTO.class);
        eventDTO.setDisciplineId(event.getDiscipline().getId());
        return eventDTO;
    }

    //из dto в entity
    public Event mapToEventEntity(EventDTO eventDTO) {
        var event = mapper.map(eventDTO, Event.class);
        return event;
    }
}

package org.example.event;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class EventDTO {
    Long id;

    Timestamp date;

    String message;
    String disciplineId;
}

package org.example.event;

import lombok.Data;
import org.example.discipline.Discipline;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "event")
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    Timestamp date;

    String message;
    @ManyToOne()
    @JoinColumn(name = "discipline_id")
    Discipline discipline;
}

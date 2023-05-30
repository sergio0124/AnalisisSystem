package org.example.comparison;

import lombok.Data;
import org.example.book.BookDTO;
import org.example.discipline.DisciplineDTO;
import org.example.user.UserDTO;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Data
public class ComparisonDTO {
    Long id;

    String type;

    Integer mark;
    String description;
    Timestamp date;
    String username;
    String disciplineId;
}

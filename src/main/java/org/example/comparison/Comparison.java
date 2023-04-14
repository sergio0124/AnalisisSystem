package org.example.comparison;

import lombok.Data;
import org.example.book.Book;
import org.example.discipline.Discipline;
import org.example.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Сomparison")
@Data
public class Comparison {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Enumerated(EnumType.STRING)
    ComparisonType type;

    Integer mark;
    String description;
    Timestamp date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    @ManyToOne
    @JoinColumn(name = "book_id")
    Book book;
    @ManyToOne
    @JoinColumn(name = "discipline_id")
    Discipline discipline;
}

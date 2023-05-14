package org.example.comparison;

import lombok.Data;
import org.example.book.Book;
import org.example.discipline.Discipline;
import org.example.user.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "Сomparison")
@Data
public class Comparison {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Enumerated(EnumType.STRING)
    ComparisonType type = ComparisonType.AUTO;

    Integer mark;
    String description;
    Timestamp date = new Timestamp(new Date().getTime());

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    Book book;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "discipline_id")
    Discipline discipline;
}

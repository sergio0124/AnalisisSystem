package org.example.book;

import lombok.Data;
import org.example.comparison.Comparison;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "book")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    String annotation;
    Timestamp creationDate;
    String author;
    String introduction;

    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    List<Comparison> comparisons;
}

package org.example.book;

import lombok.Data;
import org.example.comparison.Comparison;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "book")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    @Column(length = 1000)
    String annotation;
    Timestamp creationDate;
    String author;
    @Column(length = 7000)
    String introduction;
    String url;
    Integer pages;

    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    List<Comparison> comparisons = new ArrayList<>();

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(name, book.name) && Objects.equals(url, book.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, url);
    }
}

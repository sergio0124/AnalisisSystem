package org.example.book;

import lombok.Data;
import org.example.comparison.ComparisonDTO;

import java.sql.Timestamp;
import java.util.List;

@Data
public class BookDTO {

    Long id;
    String name;
    String annotation;
    Timestamp creationDate;
    String author;
    String introduction;

    List<ComparisonDTO> comparisons;
}

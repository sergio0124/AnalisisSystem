package org.example.book;

import lombok.AllArgsConstructor;
import org.example.comparison.Comparison;
import org.example.comparison.ComparisonDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookMapping {

    private ModelMapper mapper;

    //из entity в dto
    public BookDTO mapToBookDTO(Book book) {
        if (book == null) {
            return null;
        }

        BookDTO bookDTO = mapper.map(book, BookDTO.class);
        bookDTO.setComparisons(book
                .getComparisons().stream().map(v->mapper.map(v, ComparisonDTO.class)).toList());
        return bookDTO;
    }

    //из dto в entity
    public Book mapToComparisonEntity(BookDTO bookDTO) {
        var book = mapper.map(bookDTO, Book.class);
        book.setComparisons(book
                .getComparisons().stream().map(v->mapper.map(v, Comparison.class)).toList());
        return book;
    }
}

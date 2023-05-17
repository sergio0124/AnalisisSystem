package org.example.book;

import lombok.AllArgsConstructor;
import org.example.comparison.Comparison;
import org.example.comparison.ComparisonDTO;
import org.example.comparison.ComparisonMapping;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookMapping {

    private ModelMapper mapper;
    private ComparisonMapping comparisonMapping;

    //из entity в dto
    public BookDTO mapToBookDTO(Book book) {
        if (book == null) {
            return null;
        }

        BookDTO bookDTO = mapper.map(book, BookDTO.class);
        if (book.getComparisons() != null){
            bookDTO.setComparisons(book
                    .getComparisons().stream().map(comparisonMapping::mapToComparisonDTO).toList());
        }
        return bookDTO;
    }

    //из dto в entity
    public Book mapToBookEntity(BookDTO bookDTO) {
        var book = mapper.map(bookDTO, Book.class);
        if (book.getComparisons() != null){
            book.setComparisons(bookDTO
                    .getComparisons().stream().map(v->mapper.map(v, Comparison.class)).toList());
        }
        return book;
    }
}

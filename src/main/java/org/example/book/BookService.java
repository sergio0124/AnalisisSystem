package org.example.book;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookService {
    BookRepository bookRepository;
}

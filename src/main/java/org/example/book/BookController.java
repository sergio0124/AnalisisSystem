package org.example.book;

import lombok.AllArgsConstructor;
import org.example.comparison.ComparisonDTO;
import org.example.comparison.ComparisonService;
import org.example.discipline.DisciplineDTO;
import org.example.discipline.DisciplineService;
import org.example.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class BookController {

    BookService bookService;
    DisciplineService disciplineService;
    ComparisonService comparisonService;

    @GetMapping("/book/load")
    public String getLoadBookPage(@AuthenticationPrincipal User user,
                                  @RequestParam() String disciplineId,
                                  Map<String, Object> model
    ) {
        DisciplineDTO disciplineDTO = disciplineService.getDisciplineById(disciplineId);
        model.put("discipline", disciplineDTO);
        model.put("user", user);
        return "load_book_page";
    }

    @PostMapping("/book/load/venec")
    public ResponseEntity<Object> loadVenec(@RequestParam String disciplineId) {
        DisciplineDTO disciplineDTO = disciplineService.getDisciplineById(disciplineId);
        List<BookDTO> books = bookService.findBooks(disciplineDTO);
        return ResponseEntity.ok(books);
    }

    @PostMapping("/book/load/save_loaded")
    public ResponseEntity<Object> saveLoaded(
            @RequestBody BookDTO bookDTO,
            @RequestParam String disciplineId) {
        bookService.saveBook(bookDTO, disciplineId);
        return ResponseEntity.ok("Добавление закончено");
    }

    @GetMapping("/book/update")
    public String checkBook(
            @RequestParam Long bookId,
            @RequestParam String disciplineId,
            @RequestParam Long comparisonId,
            Map<String, Object> map,
            @AuthenticationPrincipal User user
    ) {
        ComparisonDTO comparisonDTO = comparisonService.getComparisonById(comparisonId);
        DisciplineDTO disciplineDTO = disciplineService.getDisciplineById(disciplineId);
        BookDTO bookDTO = bookService.findBookById(bookId);

        map.put("comparison", comparisonDTO);
        map.put("book", bookDTO);
        map.put("discipline", disciplineDTO);
        map.put("user", user);

        return "book_page";
    }

    @PostMapping("/book/update")
    ResponseEntity<Object> updateBookAndComparison(@RequestBody BookDTO bookDTO,
                        @AuthenticationPrincipal User user){
        bookService.updateBook(bookDTO);
        ComparisonDTO comparisonDTO = bookDTO.getComparisons().get(0);
        if (comparisonDTO == null) {
            return ResponseEntity.badRequest().body("Не получилось обновить сопоставление, обновление книги завершено");
        }
        comparisonDTO.setUsername(user.getUsername());
        comparisonService.updateComparison(comparisonDTO);
        return ResponseEntity.ok("Сохранение прошло успешно");
    }

    @PostMapping("/book/delete")
    public ResponseEntity<Object> deleteBook(
            @RequestParam String disciplineId,
            @RequestParam Long bookId
    ) {
        BookDTO bookDTO = bookService.findBookById(bookId);
        if (bookDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Книги с таким id не найдено");
        }
        if (bookDTO.getComparisons().stream().filter(c -> !c.getDisciplineId().contains(disciplineId)).count() > 1) {
            //Many comparisons
            comparisonService.deleteComparisonByDisciplineIdAndBookId(disciplineId, bookId);
            return ResponseEntity.ok("Книга будет убрана из книгообеспеченности дисциплины");
        } else {
            bookService.deleteBookById(bookId);
            return ResponseEntity.ok("Книга удалена из системы");
        }
    }


    @PostMapping("/book/load/byurl")
    public ResponseEntity<Object> loadBookByUrl(
            @RequestParam String bookUrl
    ) {
        BookDTO bookDTO = bookService.parseBookByPdf(bookUrl);
        return ResponseEntity.ok(bookDTO);
    }
}

package org.example.book;

import lombok.AllArgsConstructor;
import org.example.comparison.ComparisonDTO;
import org.example.comparison.ComparisonService;
import org.example.discipline.Discipline;
import org.example.discipline.DisciplineDTO;
import org.example.discipline.DisciplineService;
import org.example.event.EventService;
import org.example.plan.PlanMapping;
import org.example.plan.PlanService;
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
    PlanService planService;
    PlanMapping planMapping;
    EventService eventService;

    @GetMapping("/book/load")
    public String getLoadBookPage(@AuthenticationPrincipal User user,
                                  @RequestParam() String disciplineId,
                                  Map<String, Object> model
    ) {
        DisciplineDTO disciplineDTO = disciplineService.getDisciplineById(disciplineId);
        model.put("discipline", disciplineDTO);
        model.put("user", user);
        model.put("plan", planMapping.mapToPlanDTO(planService.getPlanById(disciplineDTO.getAcademicPlanId())));
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
            @RequestParam String disciplineId,
            @AuthenticationPrincipal User user) {
        BookDTO bookLoaded = bookService.parseBookFromVenec(bookDTO);
        bookLoaded = bookService.saveBook(bookLoaded, disciplineId);
        DisciplineDTO disciplineDTO = disciplineService.getDisciplineById(disciplineId);
        eventService.saveEvent(disciplineId,
                "Пользователь " + user.getFullname()
                        + " (" + user.getUsername() + ") обновил книгообеспеченность дисциплины " + disciplineDTO.getAcademicPlanDisciplineName()
                        + ". Была добавлена книга: " + bookLoaded.getName());
        return ResponseEntity.ok("Добавление закончено");
    }

    @GetMapping("/book/update")
    public String updateBook(
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

    @GetMapping("/book/check")
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

        return "check_book";
    }

    @PostMapping("/book/update")
    ResponseEntity<Object> updateBookAndComparison(@RequestBody BookDTO bookDTO,
                                                   @AuthenticationPrincipal User user) {
        bookService.updateBook(bookDTO);
        ComparisonDTO comparisonDTO = bookDTO.getComparisons().get(0);
        if (comparisonDTO == null) {
            return ResponseEntity.badRequest().body("Не получилось обновить сопоставление, обновление книги завершено");
        }
        comparisonDTO.setUsername(user.getUsername());
        comparisonService.updateComparison(comparisonDTO);

        DisciplineDTO disciplineDTO = disciplineService.getDisciplineById(comparisonDTO.getDisciplineId());
        eventService.saveEvent(comparisonDTO.getDisciplineId(),
                "Пользователь " + user.getFullname()
                        + " (" + user.getUsername() + ") обновил книгообеспеченность дисциплины " + disciplineDTO.getAcademicPlanDisciplineName()
                        + ". Была изменена книга: " + bookDTO.getName());

        return ResponseEntity.ok("Сохранение прошло успешно");
    }

    @PostMapping("/book/delete")
    public ResponseEntity<Object> deleteBook(
            @RequestParam String disciplineId,
            @RequestParam Long bookId,
            @AuthenticationPrincipal User user
    ) {
        BookDTO bookDTO = bookService.findBookById(bookId);
        if (bookDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Книги с таким id не найдено");
        }
        if (bookDTO.getComparisons().stream().filter(c -> !c.getDisciplineId().contains(disciplineId)).count() > 1) {
            //Many comparisons
            comparisonService.deleteComparisonByDisciplineIdAndBookId(disciplineId, bookId);
            DisciplineDTO disciplineDTO = disciplineService.getDisciplineById(disciplineId);
            eventService.saveEvent(disciplineDTO.getId(),
                    "Пользователь " + user.getFullname()
                            + " (" + user.getUsername() + ") обновил книгообеспеченность дисциплины " + disciplineDTO.getAcademicPlanDisciplineName()
                            + ". Была удалена связь дисциплины с книгой: " + bookDTO.getName());
            return ResponseEntity.ok("Книга будет убрана из книгообеспеченности дисциплины");
        } else {
            bookService.deleteBookById(bookId);
            DisciplineDTO disciplineDTO = disciplineService.getDisciplineById(disciplineId);
            eventService.saveEvent(disciplineDTO.getId(),
                    "Пользователь " + user.getFullname()
                            + " (" + user.getUsername() + ") обновил книгообеспеченность дисциплины " + disciplineDTO.getAcademicPlanDisciplineName()
                            + ". Из системы была удалена книга: " + bookDTO.getName());
            return ResponseEntity.ok("Книга удалена из системы");
        }
    }


    @PostMapping("/book/load/byurl")
    public ResponseEntity<Object> loadBookByUrl(
            @RequestParam String url,
            @RequestParam String disciplineId,
            @AuthenticationPrincipal User user
    ) {
        BookDTO bookDTO = bookService.parseBookByPdf(url);
        bookService.saveBook(bookDTO, disciplineId);

        DisciplineDTO disciplineDTO = disciplineService.getDisciplineById(disciplineId);
        eventService.saveEvent(disciplineDTO.getId(),
                "Пользователь " + user.getFullname()
                        + " (" + user.getUsername() + ") обновил книгообеспеченность дисциплины " + disciplineDTO.getAcademicPlanDisciplineName()
                        + ". Из системы была удалена книга: " + bookDTO.getName());
        return ResponseEntity.ok(bookDTO);
    }


    @PostMapping("/book/load/similar")
    public ResponseEntity<Object> loadSimilar(@RequestParam String disciplineId) {
        List<BookDTO> bookDTOS = comparisonService.loadAllBooksByDisciplineId(disciplineId);
        return ResponseEntity.ok(bookDTOS);
    }


    @PostMapping("/book/load/add_exising")
    public ResponseEntity<Object> addExisingBook(@RequestParam String disciplineId,
                                                 @RequestParam Long bookId) {
        BookDTO bookDTO = bookService.findBookById(bookId);
        DisciplineDTO disciplineDTO = disciplineService.getDisciplineById(disciplineId);
        comparisonService.createComparison(bookDTO, disciplineDTO);
        return ResponseEntity.ok("Сопоставление добавлено");
    }
}

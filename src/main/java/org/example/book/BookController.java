package org.example.book;

import lombok.AllArgsConstructor;
import org.example.discipline.DisciplineDTO;
import org.example.discipline.DisciplineService;
import org.example.user.User;
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

    @GetMapping("/book/load")
    public String getLoadBookPage(@AuthenticationPrincipal User user,
                                  @RequestParam() String disciplineId,
                                  Map<String, Object> model
    ){
        DisciplineDTO disciplineDTO = disciplineService.getDisciplineById(disciplineId);
        model.put("discipline", disciplineDTO);
        model.put("user", user);
        return "load_book_page";
    }

    @PostMapping("/book/load/venec")
    public ResponseEntity<Object> loadVenec(@RequestParam String disciplineId){
        DisciplineDTO disciplineDTO = disciplineService.getDisciplineById(disciplineId);
        List<BookDTO> books = bookService.findBooks(disciplineDTO);
        return ResponseEntity.ok(books);
    }

    @PostMapping("/book/load/save_loaded")
    public ResponseEntity<Object> saveLoaded(
            @RequestBody BookDTO bookDTO,
            @RequestParam String disciplineId){
        bookService.saveBook(bookDTO, disciplineId);
        return ResponseEntity.ok("Добавление закончено");
    }
}

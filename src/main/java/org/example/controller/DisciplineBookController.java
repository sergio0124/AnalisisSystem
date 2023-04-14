package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.book.Book;
import org.example.comparison.Comparison;
import org.example.discipline.Discipline;
import org.example.discipline.DisciplineService;
import org.example.user.MappingUser;
import org.example.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class DisciplineBookController {

    DisciplineService disciplineService;
    MappingUser mappingUser;

    @GetMapping("/disciplines/book")
    public String getDisciplinePage(@AuthenticationPrincipal User user,
                                    @RequestParam() Long disciplineId,
                                    Map<String, Object> model){

        Discipline discipline = disciplineService.getDisciplineById(disciplineId);
        List<Book> books = discipline.getComparisons().stream().map(Comparison::getBook).toList();

        model.put("books", books);
        model.put("discipline", mappingUser.mapToUserDto(user));
        model.put("user", mappingUser.mapToUserDto(user));

        return "disicplineBooks.ftlh";
    }



}

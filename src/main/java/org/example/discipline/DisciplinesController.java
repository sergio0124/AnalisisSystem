package org.example.discipline;

import lombok.AllArgsConstructor;
import org.example.book.BookDTO;
import org.example.book.BookMapping;
import org.example.comparison.Comparison;
import org.example.plan.PlanDTO;
import org.example.plan.PlanService;
import org.example.user.User;
import org.example.user.UserMapping;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class DisciplinesController {
    private final Integer PAGE_SIZE = 20;
    DisciplineService disciplineService;
    UserMapping userMapping;
    PlanService planService;
    DisciplineMapping disciplineMapping;
    BookMapping bookMapping;

    @GetMapping("disciplines/")
    String showDisciplines(Map<String, Object> model,
                           @RequestParam(required = false) Optional<String> academicPlanId,
                           @RequestParam(required = false) Optional<String> search,
                           @AuthenticationPrincipal User user) {
        List<PlanDTO> plans = planService.getAllPlans();
        String originalSearch = search.orElse("");
        List<DisciplineDTO> disciplines;

        Pageable pageable = PageRequest.of(0, PAGE_SIZE, Sort.by(Sort.Direction.ASC, "academicPlanDisciplineName"));
        if (academicPlanId.isPresent()) {
            String id = academicPlanId.get();
            disciplines = disciplineService.getDisciplinesByPlanIdAndSearch(id, originalSearch, pageable);
        } else {
            disciplines = disciplineService.getDisciplinesByPlanIdAndSearch(plans.get(0).getAcademicPlanId(), originalSearch, pageable);
        }
        model.put("disciplines", disciplines);
        model.put("user", user);
        model.put("plans", plans);
        return "discpline_page";
    }


    @PostMapping("disciplines/")
    ResponseEntity<List<DisciplineDTO>> showDisciplines(
            @RequestParam(required = false) Optional<String> academicPlanId,
            @RequestParam Integer page,
            @RequestParam(required = false) Optional<String> search) {
        List<PlanDTO> plans = planService.getAllPlans();
        String originalSearch = search.orElse("");

        Pageable pageable = PageRequest.of(
                page, PAGE_SIZE, Sort.by(Sort.Direction.ASC, "academicPlanDisciplineName"));
        if (academicPlanId.isPresent()) {
            String id = academicPlanId.get();
            return ResponseEntity.ok(
                    disciplineService.getDisciplinesByPlanIdAndSearch(id, originalSearch, pageable));
        } else {
            return ResponseEntity.ok(
                    disciplineService.getDisciplinesByPlanIdAndSearch(plans.get(0).getAcademicPlanId(), originalSearch, pageable));
        }
    }


    @GetMapping("/disciplines/books")
    public String getDisciplinePage(@AuthenticationPrincipal User user,
                                    @RequestParam() String disciplineId,
                                    Map<String, Object> model) {

        DisciplineDTO discipline = disciplineService.getDisciplineById(disciplineId);

        if (discipline == null) {
            model.put("user", userMapping.mapToUserDto(user));
            return "disicpline_books";
        }

        List<BookDTO> books = discipline
                .getComparisons()
                .stream()
                .map(Comparison::getBook)
                .map(v -> bookMapping.mapToBookDTO(v))
                .toList();

        books.forEach(v -> {
            v.setComparisons(v
                    .getComparisons()
                    .stream()
                    .filter(comp -> Objects.equals(comp.getDiscipline().getId(), disciplineId))
                    .toList());
        });

        model.put("books", books);
        model.put("discipline", discipline);
        model.put("user", userMapping.mapToUserDto(user));

        return "disicpline_books";
    }
}

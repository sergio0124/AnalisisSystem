package org.example.discipline;

import lombok.AllArgsConstructor;
import org.example.book.Book;
import org.example.book.BookDTO;
import org.example.book.BookMapping;
import org.example.comparison.Comparison;
import org.example.discipline.Discipline;
import org.example.discipline.DisciplineService;
import org.example.plan.Plan;
import org.example.plan.PlanService;
import org.example.user.User;
import org.example.user.UserMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class DisciplinesController {
    DisciplineService disciplineService;
    UserMapping userMapping;
    PlanService planService;
    DisciplineMapping disciplineMapping;
    BookMapping bookMapping;

    @GetMapping("disciplines/")
    String showDisciplines(Map<String, Object> model,
                           @RequestParam(required = false) Optional<String> academicPlanId,
                           @AuthenticationPrincipal User user) {
        List<Plan> plans = planService.getAllPlans();
        List<Discipline> disciplines;
        if (academicPlanId.isPresent()) {
            String id = academicPlanId.get();
            disciplines = disciplineService.getDisciplinesByPlanId(id);
        } else {
            disciplines = disciplineService.getDisciplinesByPlanId(plans.get(0).getAcademicPlanId());
        }
        model.put("disciplines", disciplines);
        model.put("user", user);
        model.put("plans", plans);
        return "discplinePage";
    }


    @GetMapping("/disciplines/books")
    public String getDisciplinePage(@AuthenticationPrincipal User user,
                                    @RequestParam() Long disciplineId,
                                    Map<String, Object> model) {

        Discipline discipline = disciplineService.getDisciplineById(disciplineId);
        List<BookDTO> books = discipline
                .getComparisons()
                .stream()
                .map(Comparison::getBook)
                .map(v -> bookMapping.mapToBookDTO(v))
                .toList();

        books.forEach(v->{
            v.setComparisons(v
                    .getComparisons()
                    .stream()
                    .filter(comp-> Objects.equals(comp.getDiscipline().getId(), disciplineId))
                    .toList());
        });

        model.put("books", books);
        model.put("discipline", disciplineMapping.mapToDisciplineDto(discipline));
        model.put("user", userMapping.mapToUserDto(user));

        return "disicplineBooks";
    }
}

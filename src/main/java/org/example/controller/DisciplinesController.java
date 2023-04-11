package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.discipline.Discipline;
import org.example.discipline.DisciplineService;
import org.example.plan.Plan;
import org.example.plan.PlanService;
import org.example.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class DisciplinesController {
    DisciplineService disciplineService;
    PlanService planService;

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

}

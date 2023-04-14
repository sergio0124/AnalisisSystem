package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.discipline.DisciplineDTO;
import org.example.plan.Plan;
import org.example.plan.PlanRepository;
import org.example.plan.PlanService;
import org.example.service.ReportService;
import org.example.user.MappingUser;
import org.example.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
@AllArgsConstructor
public class ReportController {
    MappingUser mappingUser;
    ReportService reportService;
    PlanService planService;

    @GetMapping("report/")
    public String getChoosePage(@AuthenticationPrincipal User user,
                                Map<String, Object> model) {
        List<Plan> plans = planService.getAllPlans();
        model.put("user", mappingUser.mapToUserDto(user));
        model.put("plans", plans);
        return "report_choose";
    }

    @GetMapping("report/create")
    public String getDisciplineReport(@AuthenticationPrincipal User user,
                                      @RequestParam(required = false) Optional<String> fak,
                                      @RequestParam(required = false) Optional<String> kaf,
                                      @RequestParam(required = false) Optional<String> nap,
                                      @RequestParam(required = false) Optional<String> academicPlanId,
                                      Map<String, Object> model) {
        List<DisciplineDTO> nodisc = null;
        List<DisciplineDTO> normdisc = null;
        List<DisciplineDTO> okdisc = null;
        Plan plan = null;

        if (fak.isPresent()) {
        } else if (kaf.isPresent()) {
        } else if (nap.isPresent()) {
        } else if (academicPlanId.isPresent()) {
            List<DisciplineDTO> disciplineDTOList = reportService.getDisciplinesByPlanId(String.valueOf(academicPlanId));
            sortDisciplinesByPriority(disciplineDTOList);

            nodisc = getDisciplinesWithNoBooks(disciplineDTOList);
            normdisc = getDisciplinesWithNoComparison(disciplineDTOList);
            okdisc = getDisciplinesWithComparison(disciplineDTOList);

            plan = planService.getPlanById(academicPlanId.get());
        }

        model.put("user", mappingUser.mapToUserDto(user));
        model.put("nodisc", nodisc);
        model.put("normdisc", normdisc);
        model.put("okdisc", okdisc);
        model.put("plan", plan);

        return "report_page";
    }

    private List<DisciplineDTO> getDisciplinesWithNoBooks(List<DisciplineDTO> disciplineDTOS) {
        return disciplineDTOS.stream()
                .filter(v -> v.getComparisons() == null || v.getComparisons().size() == 0)
                .toList();
    }

    private List<DisciplineDTO> getDisciplinesWithNoComparison(List<DisciplineDTO> disciplineDTOS) {
        return disciplineDTOS.stream()
                .filter(v -> v.getComparisons().stream().noneMatch(c -> c.getMark() > 0))
                .toList();
    }

    private List<DisciplineDTO> getDisciplinesWithComparison(List<DisciplineDTO> disciplineDTOS) {
        return disciplineDTOS.stream()
                .filter(v -> v.getComparisons().stream().filter(c -> c.getMark() > 0).count() > 0)
                .toList();
    }

    private void sortDisciplinesByPriority(List<DisciplineDTO> disciplineDTOS) {
        disciplineDTOS.sort(Comparator.comparing(DisciplineDTO::getValue));
    }
}

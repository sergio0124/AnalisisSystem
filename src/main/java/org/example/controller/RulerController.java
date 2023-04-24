package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.result.DisciplineResultService;
import org.example.discipline.DisciplineService;
import org.example.plan.PlanService;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class RulerController {
    DisciplineService disciplineService;
    PlanService planService;
    DisciplineResultService disciplineResultService;
}

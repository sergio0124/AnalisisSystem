package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.plan.PlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class PlanController {
    PlanService planService;

    @GetMapping("/plan")
    String getPlan() {
        return "plan";
    }

    @GetMapping("/plan/load")
    ResponseEntity<String> loadPlan() {

        try {
            planService.getPlanAndSave();
            return ResponseEntity.ok("Планы обновлены");
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }
}
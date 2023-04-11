package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.plan.Plan;
import org.example.plan.PlanService;
import org.example.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class PlanController {
    PlanService planService;

    @GetMapping("/plan/")
    String getPlan(Map<String, Object> model,
                   @AuthenticationPrincipal User user) {
        List<Plan> plans = planService.getAllPlans();

        model.put("plans", plans);
        model.put("user", user);
        return "planPage";
    }

    @PostMapping("/plan/load")
    ResponseEntity<String> loadPlan() {

        try {
            planService.getPlanAndSave();
            return ResponseEntity.ok("Планы обновлены");
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @PostMapping("/plan/delete")
    ResponseEntity<String> deletePlan(@RequestBody Plan plan){
        try {
            planService.deletePlan(plan.getAcademicPlanId());
            return ResponseEntity.ok("Планы обновлены");
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }
}

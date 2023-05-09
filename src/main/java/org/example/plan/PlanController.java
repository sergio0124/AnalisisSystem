package org.example.plan;

import lombok.AllArgsConstructor;
import org.example.plan.Plan;
import org.example.plan.PlanService;
import org.example.user.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    final int PAGE_SIZE = 20;

    @GetMapping("/plan/")
    String getPlan(@RequestParam(value = "search", required = false) Optional<String> search,
                    Map<String, Object> model,
                   @AuthenticationPrincipal User user) {
        Pageable pageable = PageRequest.of(0, PAGE_SIZE, Sort.by(Sort.Direction.ASC, "academicPlanSpecialtyProfile"));

        List<PlanDTO> plans;
        if (search.isPresent()){
            plans = planService.getPlansByAcademicName(search.get(), pageable);
        } else {
            plans = planService.getAllPlansByPage(pageable);
        }

        model.put("plans", plans);
        model.put("user", user);
        return "plan_page";
    }


    @PostMapping("/plan/")
    public ResponseEntity<List<PlanDTO>> getPlansByPage(@RequestParam(value = "search", required = false) Optional<String> search,
                                                        @RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.ASC, "academicPlanSpecialtyProfile"));

        List<PlanDTO> plans;
        if (search.isPresent()){
            plans = planService.getPlansByAcademicName(search.get(), pageable);
        } else {
            plans = planService.getAllPlansByPage(pageable);
        }

        return ResponseEntity.ok(plans);
    }

    @PostMapping("/plan/load")
    ResponseEntity<String> loadPlan(@RequestParam Integer start,
                                    @RequestParam Integer end) {

        try {
            planService.getPlanAndSave(start, end);
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

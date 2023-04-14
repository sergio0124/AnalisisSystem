package org.example.plan;

import org.example.discipline.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    List<Plan> findAllByOrderByAcademicPlanIdDesc();
    Plan findPlanByAcademicPlanId(String academicPlanId);

    void deletePlanByAcademicPlanId(String academicPlanId);
}

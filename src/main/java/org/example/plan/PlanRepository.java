package org.example.plan;

import org.example.discipline.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    List<Plan> findAllByOrderByAcademicPlanIdDesc();

    void deletePlanByAcademicPlanId(String academicPlanId);
}

package org.example.plan;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    Page<Plan> findAll(Pageable pageable);
    Page<Plan> findPlansByAcademicPlanSpecialtyProfileContainingIgnoreCase(String name, Pageable pageable);

    List<Plan> findAll(Sort sort);


    Plan findPlanByAcademicPlanId(String academicPlanId);

    void deletePlanByAcademicPlanId(String academicPlanId);
}

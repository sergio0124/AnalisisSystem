package org.example.discipline;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DisciplineRepository extends JpaRepository<Discipline, String> {

    Page<Discipline> findDisciplineByAcademicPlanIdAndAcademicPlanDisciplineNameContainsIgnoreCase(String academicPlanId, String search, Pageable pageable);
    List<Discipline> findDisciplineByAcademicPlanId(String academicPlanId);

    Discipline findDisciplineByIdContaining(String id);
}

package org.example.discipline;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisciplineRepository extends JpaRepository<Discipline, String> {

    Page<Discipline> findDisciplineByAcademicPlanId(String academicPlanId, Pageable pageable);
    List<Discipline> findDisciplineByAcademicPlanId(String academicPlanId);

}

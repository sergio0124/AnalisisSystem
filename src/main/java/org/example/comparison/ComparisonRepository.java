package org.example.comparison;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComparisonRepository extends JpaRepository<Comparison, Long> {

    Comparison findComparisonByDisciplineIdContainsAndBookId(String discipline_id, Long book_id);
    List<Comparison> findComparisonsByDiscipline_AcademicPlanDisciplineIdContains(String discipline_academicPlanDisciplineName);
}

package org.example.comparison;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ComparisonRepository extends JpaRepository<Comparison, Long> {

    Comparison findComparisonByDisciplineIdContainsAndBookId(String discipline_id, Long book_id);
}

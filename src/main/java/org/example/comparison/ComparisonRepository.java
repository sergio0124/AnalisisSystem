package org.example.comparison;

import org.example.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComparisonRepository extends JpaRepository<Comparison, Long> {
}

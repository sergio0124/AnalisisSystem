package org.example.comparison;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ComparisonService {

    ComparisonRepository comparisonRepository;
    ComparisonMapping comparisonMapping;

    public ComparisonDTO getComparisonById(Long id){
        Comparison comparison = comparisonRepository.findById(id).orElse(null);
        if (comparison != null) {
            return comparisonMapping.mapToComparisonDTO(comparison);
        }
        else {
            return null;
        }
    }

    public void deleteComparisonByDisciplineIdAndBookId(String disciplineId, Long bookId){
        Comparison comparison = comparisonRepository.findComparisonByDisciplineIdContainsAndBookId(disciplineId, bookId);
        if (comparison != null){
            comparisonRepository.delete(comparison);
        }
    }
}

package org.example.comparison;

import lombok.AllArgsConstructor;
import org.example.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ComparisonService {

    ComparisonRepository comparisonRepository;
    ComparisonMapping comparisonMapping;
    UserRepository userRepository;

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

    public void updateComparison(ComparisonDTO comparisonDTO){
        Comparison comparison = comparisonRepository.findById(comparisonDTO.getId()).orElse(null);
        if (comparison == null) {
            return;
        }
        comparison.setMark(comparisonDTO.getMark());
        comparison.setType(comparisonDTO.getType());
        comparison.setDate(comparisonDTO.getDate());
        comparison.setDescription(comparisonDTO.getDescription());
        comparison.setUser(userRepository.findByUsername(comparisonDTO.getUsername()).orElse(null));
        comparisonRepository.save(comparison);
    }
}

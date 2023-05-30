package org.example.comparison;

import lombok.AllArgsConstructor;
import org.example.result.DisciplineResult;
import org.example.result.DisciplineResultDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ComparisonMapping {

    private ModelMapper mapper;

    //из entity в dto
    public ComparisonDTO mapToComparisonDTO(Comparison comparison) {
        if (comparison == null) {
            return null;
        }
        ComparisonDTO comparisonDTO = mapper.map(comparison, ComparisonDTO.class);
        comparisonDTO.setDisciplineId(comparison.getDiscipline().getId());
        if (comparison.getUser() != null) {
            comparisonDTO.setUsername(comparison.getUser().getFullname());
        } else {
            comparisonDTO.setUsername(" - ");
        }
        comparisonDTO.setType(comparison.getType().getTitle());
        return comparisonDTO;
    }

    //из dto в entity
    public Comparison mapToComparisonEntity(ComparisonDTO comparisonDTO) {
        return mapper.map(comparisonDTO, Comparison.class);
    }
}

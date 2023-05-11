package org.example.discipline;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DisciplineService {
    DisciplineRepository disciplineRepository;
    DisciplineMapping disciplineMapping;

    public void save(List<Discipline> disciplines){
        disciplineRepository.saveAll(disciplines);
    }

    public List<DisciplineDTO> getDisciplinesByPlanIdAndSearch(String academicPlanId, String search, Pageable pageable) {
        return disciplineRepository
                .findDisciplineByAcademicPlanIdAndAcademicPlanDisciplineNameContainsIgnoreCase(academicPlanId, search, pageable)
                .getContent()
                .stream()
                .map(disciplineMapping::mapToDisciplineDto)
                .toList();
    }

    public DisciplineDTO getDisciplineById(String id){
        Optional<Discipline> discipline = disciplineRepository.findDisciplineByIdContaining(id);
        return discipline.isPresent() ?
                disciplineMapping.mapToDisciplineDto(discipline.orElse(null))
                : null;
    }
}

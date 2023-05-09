package org.example.discipline;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DisciplineService {
    DisciplineRepository disciplineRepository;
    DisciplineMapping disciplineMapping;

    public void save(List<Discipline> disciplines){
        disciplineRepository.saveAll(disciplines);
    }

    public List<DisciplineDTO> getDisciplinesByPlanId(String academicPlanId, Pageable pageable) {
        return disciplineRepository
                .findDisciplineByAcademicPlanId(academicPlanId, pageable)
                .getContent()
                .stream()
                .map(disciplineMapping::mapToDisciplineDto)
                .toList();
    }

    public Discipline getDisciplineById(String id){
        return disciplineRepository.findById(id).orElse(null);
    }
}

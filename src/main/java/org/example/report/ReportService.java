package org.example.report;

import lombok.AllArgsConstructor;
import org.example.discipline.*;
import org.example.result.DisciplineResultDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportService {
    DisciplineRepository disciplineRepository;
    DisciplineMapping disciplineMapping;

    public List<DisciplineDTO> getDisciplinesByPlanId(String academicPlanId) {
        List<DisciplineDTO> disciplineDTOS = mapToDisciplineDTO(
                disciplineRepository.findDisciplineByAcademicPlanId(academicPlanId));
        countPriority(disciplineDTOS);
        return disciplineDTOS;
    }

//    public List<DisciplineDTO> getDisciplinesByKafedraName(String name){
//
//    }
//
//
//    public List<DisciplineDTO> getDisciplinesByFacultyName(String name){
//
//    }
//
//    public List<DisciplineDTO> getDisciplinesByDirectionName(String name){
//
//    }

    private void countPriority(List<DisciplineDTO> disciplineDTOS) {
        disciplineDTOS.forEach(d -> {
            d.setValue(d.getDisciplineResults().stream().mapToInt(DisciplineResultDTO::getPriority).sum());
        });
    }

    private List<DisciplineDTO> mapToDisciplineDTO(List<Discipline> disciplines) {
        List<DisciplineDTO> list = new ArrayList<>();
        disciplines.forEach(v -> {
            var elem = disciplineMapping.mapToDisciplineDto(v);
            list.add(elem);
        });
        return list;
    }
}

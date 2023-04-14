package org.example.service;

import org.example.discipline.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    DisciplineRepository disciplineRepository;
    MappingDiscipline mappingDiscipline;

    public List<DisciplineDTO> getDisciplinesByPlanId(String academicPlanId){
        List<DisciplineDTO> disciplineDTOS = mapToDisciplineDTO(
                disciplineRepository.getDisciplinesByAcademicPlanId(academicPlanId));
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

    private void countPriority(List<DisciplineDTO> disciplineDTOS){
        disciplineDTOS.forEach(d->{
            d.setValue(d.getDisciplineResults().stream().mapToInt(DisciplineResult::getPriority).sum());
        });
    }
    private List<DisciplineDTO> mapToDisciplineDTO(List<Discipline> disciplines){
        return disciplines.stream()
                .map(mappingDiscipline::mapToDisciplineDto).toList();
    }
}

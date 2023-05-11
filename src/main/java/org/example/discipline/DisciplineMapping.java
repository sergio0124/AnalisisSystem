package org.example.discipline;

import lombok.AllArgsConstructor;
import org.example.result.DisciplineResult;
import org.example.result.DisciplineResultDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DisciplineMapping {
    private ModelMapper mapper;

    //из entity в dto
    public DisciplineDTO mapToDisciplineDto(Discipline discipline) {
        if (discipline == null) {
            return null;
        }

        DisciplineDTO disciplineDTO = mapper.map(discipline, DisciplineDTO.class);
        return disciplineDTO;
    }

    //из dto в entity
    public Discipline mapToDisciplineEntity(DisciplineDTO disciplineDTO) {
        var discipline = mapper.map(disciplineDTO, Discipline.class);
        discipline.setDisciplineResults(disciplineDTO
                .getDisciplineResults().stream().map(v->mapper.map(v, DisciplineResult.class)).toList());
        return discipline;
    }
}

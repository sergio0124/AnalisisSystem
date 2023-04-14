package org.example.discipline;

import lombok.AllArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.example.user.Role;
import org.example.user.User;
import org.example.user.UserDTO;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MappingDiscipline {
    private DozerBeanMapper mapper;

    //из entity в dto
    public DisciplineDTO mapToDisciplineDto(Discipline discipline) {
        if (discipline == null) {
            return null;
        }

        DisciplineDTO disciplineDTO = mapper.map(discipline, DisciplineDTO.class);

        return disciplineDTO;
    }

    //из dto в entity
    public Discipline mapToDisciplineEntity(DisciplineDTO dto) {
        var discipline = mapper.map(dto, Discipline.class);
        return discipline;
    }
}

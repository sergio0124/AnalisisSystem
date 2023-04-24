package org.example.result;

import org.modelmapper.ModelMapper;

public class DisciplineResultMapping {

    private ModelMapper mapper;

    //из entity в dto
    public DisciplineResultDTO mapToDisciplineResultDto(DisciplineResult disciplineResult) {
        if (disciplineResult == null) {
            return null;
        }
        DisciplineResultDTO disciplineResultDTO = mapper.map(disciplineResult, DisciplineResultDTO.class);
        disciplineResultDTO.setId(disciplineResult.getId().toString());
        return disciplineResultDTO;
    }

    //из dto в entity
    public DisciplineResult mapToDisciplineResultEntity(DisciplineResultDTO disciplineResultDTO) {
        DisciplineResult disciplineResult = mapper.map(disciplineResultDTO, DisciplineResult.class);
        if (!"".equals(disciplineResultDTO.getId())) {
            disciplineResult.setId(Long.parseLong(disciplineResultDTO.getId()));
        }
        return disciplineResult;
    }

}

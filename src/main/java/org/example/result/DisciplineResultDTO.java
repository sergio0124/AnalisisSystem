package org.example.result;

import lombok.Getter;
import lombok.Setter;
import org.example.discipline.DisciplineDTO;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DisciplineResultDTO {

    String id;
    String resultId;
    Long academicPlanId;
    String typeOfResult;
    Integer priority;
    String DisciplineResultName;
    String DisciplineResultNameFull;

    List<DisciplineDTO> disciplines = new ArrayList<>();
}

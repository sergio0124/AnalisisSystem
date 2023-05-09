package org.example.plan;

import lombok.AllArgsConstructor;
import org.example.discipline.Discipline;
import org.example.discipline.DisciplineDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PlanMapping {

    private ModelMapper mapper;

    //из entity в dto
    public PlanDTO mapToPlanDTO(Plan plan) {
        if (plan == null) {
            return null;
        }

        PlanDTO planDTO = mapper.map(plan, PlanDTO.class);
//        planDTO.setDisciplinesOfPlan(plan
//                .getDisciplinesOfPlan().stream().map(v->mapper.map(v, DisciplineDTO.class)).toList());
        return planDTO;
    }

    //из dto в entity
    public Plan mapToPlanEntity(PlanDTO planDTO) {
        var plan = mapper.map(planDTO, Plan.class);
//        plan.setDisciplinesOfPlan(planDTO
//                .getDisciplinesOfPlan().stream().map(v->mapper.map(v, Discipline.class)).toList());
        return plan;
    }
}

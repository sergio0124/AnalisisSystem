package org.example.discipline;

import lombok.Getter;
import lombok.Setter;
import org.example.comparison.Comparison;
import org.example.plan.Plan;
import org.example.result.DisciplineResultDTO;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DisciplineDTO {


    String id;
    String academicPlanDisciplineId;

    String academicPlanSemesterName;
    String academicPlanId;
    Short academicPlanSemesterNumber;
    String academicPlanDisciplineBlock;
    String academicPlanTypeOfBlock;
    String academicPlanDisciplineName;
    String academicPlanForm;
    String academicPlanFormMeasure;
    Integer academicPlanAmountByPlan;
    Long academicPlanUnitId;
    String academicPlanUnitName;
    String academicPlanUnitFormMeasure;
    String academicPlanUnitFormRule;
    Integer academicPlanUnitAmountByPlan;
    Integer academicPlanDisciplineZET;
    Float academicPlanDisciplineFormZET;

    List<String> DisciplineResultsList = new ArrayList<>();

    Plan plan;

    List<Comparison> comparisons;

    List<DisciplineResultDTO> disciplineResults = new ArrayList<>();

    Integer value = 0;
}




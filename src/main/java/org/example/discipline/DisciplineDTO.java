package org.example.discipline;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.comparison.Comparison;
import org.example.plan.Plan;
import org.example.subscribe.Subscribe;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class DisciplineDTO {


    Long id;
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

    List<Subscribe> subscribes;

    List<DisciplineResult> disciplineResults = new ArrayList<>();

    Integer value = 0;
}




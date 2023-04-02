package org.example.discipline;

import lombok.Data;
import org.example.plan.Plan;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "discipline")
@Data
public class Discipline {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @ElementCollection
    List<String> DisciplineResultsList  = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "plan_id")
    Plan plan;
}

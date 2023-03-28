package org.example.discipline;

import lombok.Data;
import org.example.plan.Plan;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "discipline")
@Data
public class Discipline {
    @Id
    private String academicPlanDisciplineId;

    String academicPlanSemesterName;
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

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "result_discipline",
            joinColumns = { @JoinColumn(name = "discipline_id") },
            inverseJoinColumns = { @JoinColumn(name = "result_id") }
    )
    List<DisciplineResult> DisciplineResultsList;

    @ManyToOne()
    @JoinColumn(name = "plan_id")
    Plan plan;
}

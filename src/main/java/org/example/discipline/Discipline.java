package org.example.discipline;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.comparison.Comparison;
import org.example.plan.Plan;
import org.example.result.DisciplineResult;
import org.example.subscribe.Subscribe;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "discipline")
@Data
public class Discipline {

    @Id
    String id;
    String academicPlanDisciplineId;

    String academicPlanSemesterName;
    String academicPlanId;
    Integer academicPlanSemesterNumber;
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

    @Transient
    @JsonProperty("DisciplineResultsList")
    List<String> DisciplineResultsList = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "plan_id")
    Plan plan;

    @OneToMany(mappedBy = "discipline", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    List<Comparison> comparisons;

    @OneToMany(mappedBy = "discipline", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    List<Subscribe> subscribes;

    @ManyToMany(mappedBy = "disciplines", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<DisciplineResult> disciplineResults = new ArrayList<>();
}



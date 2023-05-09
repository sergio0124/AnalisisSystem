package org.example.plan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.discipline.Discipline;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "plan")
@Data
public class Plan {

    @Id
    String academicPlanId;

    String academicPlan;
    String academicPlanType;
    Long academicPlanParentId;
    String academicPlanStudyType;
    String academicPlanStudyForm;
    String isPlanIndividual;
    String isPlanReducedStudyPeriod;
    String academicPlanStudyLevel;
    String academicPlanSpecialtyCode;
    String academicPlanSpecialtyName;
    String academicPlanSpecialtyProfile;
    String academicPlanQualificationName;
    String academicPlanSpecialTitle;
    String academicPlanTypeOfStandart;
    String academicPlanFacultyId;
    String academicPlanFacultyName;
    Long academicPlanUnitId;
    String academicPlanUnitName;
    String academicPlanYearOfStudy;
    String academicPlanEducationProgram;
    Short academicPlanMainPeriod;
    String academicPlanMainPeriodMeasure;
    String academicPlanExtraPeriod;
    String academicPlanExtraPeriodMeasure;
    String academicPlanIsMainPeriodShorted;
    Integer academicPlanHoursInZET;
    Float academicPlanWeekZET;
    String academicPlanComment;

    @JsonProperty("DisciplinesOfPlan")
    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Discipline> DisciplinesOfPlan;

}

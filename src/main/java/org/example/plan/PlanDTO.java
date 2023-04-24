package org.example.plan;

import lombok.Getter;
import lombok.Setter;
import org.example.discipline.DisciplineDTO;

import java.util.List;

@Setter
@Getter
public class PlanDTO {

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

    List<DisciplineDTO> DisciplinesOfPlan;

}

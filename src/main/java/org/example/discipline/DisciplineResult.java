package org.example.discipline;

import lombok.Data;
import org.example.plan.Plan;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "result")
@Data
public class DisciplineResult {
    @Id
    String resultId;

    Long academicPlanId;
    String typeOfResult;
    Integer priority;
    String DisciplineResultName;
    String DisciplineResultNameFull;

    @ManyToMany(mappedBy = "DisciplineResultsList")
    List<Discipline> disciplines;

    @ManyToOne()
    @JoinColumn(name = "plan_id")
    Plan plan;
}

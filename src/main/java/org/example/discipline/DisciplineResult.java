package org.example.discipline;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.plan.Plan;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "result")
@Data
public class DisciplineResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String resultId;

    Long academicPlanId;
    String typeOfResult;
    Integer priority;
    @JsonProperty
    String DisciplineResultName;
    @JsonProperty
    String DisciplineResultNameFull;

}

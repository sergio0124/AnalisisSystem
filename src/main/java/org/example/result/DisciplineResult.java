package org.example.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.discipline.Discipline;
import org.example.plan.Plan;

import javax.persistence.*;
import java.util.ArrayList;
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

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "result_discipline",
            joinColumns = { @JoinColumn(name = "result_id") },
            inverseJoinColumns = { @JoinColumn(name = "discipline_id") }
    )
    List<Discipline> disciplines = new ArrayList<>();
}

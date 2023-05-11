package org.example.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.discipline.Discipline;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "result")
@Data
public class DisciplineResult {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @JsonProperty("resultId")
    String id;

    Long academicPlanId;
    String typeOfResult;
    Integer priority;
    @JsonProperty
    String DisciplineResultName;
    @JsonProperty
    String DisciplineResultNameFull;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "result_discipline",
            joinColumns = {@JoinColumn(name = "result_id")},
            inverseJoinColumns = {@JoinColumn(name = "discipline_id")}
    )
    List<Discipline> disciplines = new ArrayList<>();
}

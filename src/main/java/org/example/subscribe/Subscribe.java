package org.example.subscribe;

import lombok.Data;
import org.example.comparison.Comparison;
import org.example.discipline.Discipline;
import org.example.plan.Plan;
import org.example.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subscribe")
@Data
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne()
    @JoinColumn(name = "discipline_id")
    Discipline discipline;
}

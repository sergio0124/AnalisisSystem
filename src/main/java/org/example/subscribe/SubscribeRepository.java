package org.example.subscribe;

import org.example.plan.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    Subscribe findSubscribeByUser_IdAndDiscipline_IdContains(Long user_id, String discipline_id);
}

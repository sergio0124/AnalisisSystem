package org.example.event;

import org.example.discipline.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "select e.* from (select * from usr where id = ?1) u join subscribe s on s.user_id = u.id join discipline d on d.id = s.discipline_id join event e on e.discipline_id = d.id", nativeQuery = true)
    List<Event> findEventsByUser_id(Long userId);

}

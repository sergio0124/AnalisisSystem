package org.example.discipline;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DisciplineRepository extends JpaRepository<Discipline, String> {

    Page<Discipline> findDisciplineByAcademicPlanIdAndAcademicPlanDisciplineNameContainsIgnoreCase(String academicPlanId, String search, Pageable pageable);
    List<Discipline> findDisciplineByAcademicPlanId(String academicPlanId);
    List<Discipline> findDisciplinesByIdContaining(String id);
    @Query("SELECT DISTINCT d.academicPlanUnitName FROM Discipline d")
    List<String> findUnitNames();

    List<Discipline> findDisciplinesByAcademicPlanUnitNameContains(String academicPlanUnitName);
    @Query(value = "select d.* from (select * from plan where academic_plan_faculty_name like CONCAT('%',?1,'%')) as p join\n" +
            "    discipline d on d.academic_plan_id = p.academic_plan_id", nativeQuery = true)
    List<Discipline> findDisciplinesByPlan_academicPlanFacultyNameContains(String plan_academicPlanFacultyName);
    @Query(value="select d.* from (select * from plan where academic_plan_specialty_profile like CONCAT('%',?1,'%')) as p join\n" +
            "    discipline d on d.academic_plan_id = p.academic_plan_id", nativeQuery = true)
    List<Discipline> findDisciplinesByPlan_academicPlanSpecialtyProfileContains(String plan_academicPlanSpecialtyProfile);


    @Query(value = "select d.* from (select * from usr where id = ?1) u join subscribe s on s.user_id = u.id " +
            "join discipline d on d.id = s.discipline_id", nativeQuery = true)
    List<Discipline> findDisciplinesByUser_id(Long id);
}

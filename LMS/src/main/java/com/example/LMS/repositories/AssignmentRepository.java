package com.example.LMS.repositories;


import com.example.LMS.models.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment,Integer> {
    @Query("SELECT a FROM Assignment a WHERE a.deadline BETWEEN :startDateTime AND :endDateTime")
    List<Assignment> findAssignmentsDueBetween(
            @Param("startDateTime") String startDateTime,
            @Param("endDateTime") String endDateTime
    );

}

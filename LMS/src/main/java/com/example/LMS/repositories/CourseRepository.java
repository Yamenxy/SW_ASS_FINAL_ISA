package com.example.LMS.repositories;

import com.example.LMS.models.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;



public interface CourseRepository extends JpaRepository<CourseModel, Long> {
    CourseModel findByCourseId(String courseId);
     Optional<CourseModel> findByTitle(String title);

    // To ensure that the course id is unique
    boolean existsByCourseId(String courseId);

    @Query("SELECT c FROM CourseModel c JOIN c.students s WHERE s.id = :studentId")
    List<CourseModel> findByStudentId(@Param("studentId") Long studentId);

}

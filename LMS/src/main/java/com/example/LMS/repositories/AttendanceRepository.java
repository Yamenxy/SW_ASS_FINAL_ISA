package com.example.LMS.repositories;

import com.example.LMS.models.AttendanceModel;
import com.example.LMS.models.LessonModel;
import com.example.LMS.models.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<AttendanceModel, Long> {
    List<AttendanceModel> findByLesson(LessonModel lesson);
    List<AttendanceModel> findByStudent(StudentModel student);
    AttendanceModel findByLessonAndStudent(LessonModel lesson, StudentModel student);
    List<AttendanceModel> findByLessonIn(List<LessonModel> lessons);
}

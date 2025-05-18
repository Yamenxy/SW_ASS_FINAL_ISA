package com.example.LMS.services;

import com.example.LMS.models.AttendanceModel;
import com.example.LMS.models.CourseModel;
import com.example.LMS.models.LessonModel;
import com.example.LMS.models.StudentModel;
import com.example.LMS.repositories.AttendanceRepository;
import com.example.LMS.repositories.CourseRepository;
import com.example.LMS.repositories.LessonRepository;
import com.example.LMS.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LessonRepository lessonRepository;

    public List<AttendanceModel> displayAllAttendance() {
        List<AttendanceModel> attendances = attendanceRepository.findAll();
        return attendances;
    }
    public List<AttendanceModel> getAttendanceByCourse(Long courseId) {
        CourseModel course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found for ID: " + courseId));
        List<LessonModel> lessons = course.getListLessons();
        if (lessons == null || lessons.isEmpty()) {
            System.out.println("No lessons found for course ID: " + courseId);
            return new ArrayList<>();
        }
        List<AttendanceModel> attendanceRecords = attendanceRepository.findByLessonIn(lessons);
        System.out.println("Found " + attendanceRecords.size() + " attendance records for course ID: " + courseId);
        return attendanceRecords;
    }

    public List<AttendanceModel> displayLessonAttendance(long lessonId) {
        LessonModel lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));
        List<AttendanceModel> attendances = attendanceRepository.findByLesson(lesson);
        return attendances;
    }
    public String attendLesson(int studentId, long lessonId, String OTP) {
        StudentModel student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));
        LessonModel lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));
        String returnMessage;
        LocalDateTime currentTime = LocalDateTime.now();
        boolean isLessonOngoing = currentTime.isAfter(lesson.getStartDate()) && currentTime.isBefore(lesson.getEndDate());
        boolean isCorrectOTP = lesson.getOTP().equals(OTP);
        if (!isLessonOngoing) {
            returnMessage = "Lesson is not ongoing.";
        }
        else if (!isCorrectOTP) {
            returnMessage = "OTP is not correct.";
        }
        else {
            List<CourseModel> courses = student.getCourses();
            if (courses.contains(lesson.getCourseModel())) {
                boolean isAttended = true;
                AttendanceModel attendance = new AttendanceModel(lesson, student, isAttended, currentTime);
                attendanceRepository.save(attendance);
                returnMessage = "You are successfully attend.";
            }
            else {
                returnMessage = "You are not enrolled in the course!";
            }
        }
        return returnMessage;

    }
}

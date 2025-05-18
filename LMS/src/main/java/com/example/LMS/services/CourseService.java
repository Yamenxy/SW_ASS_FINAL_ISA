package com.example.LMS.services;

import com.example.LMS.DTOs.CourseDTO;
import com.example.LMS.models.CourseModel;
import com.example.LMS.models.LessonModel;
import com.example.LMS.models.StudentModel;
import com.example.LMS.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public void createCourse(CourseModel course) {
        courseRepository.save(course);
    }

    public List<CourseDTO> displayCourses() {
        List <CourseModel> courses = courseRepository.findAll();
        List<CourseDTO> courseDTOS= courses.stream().map(c->{
            CourseDTO x= new CourseDTO(c);
            x.setStudents(c.getStudents());
            return x;
        }).collect(Collectors.toList());
        return courseDTOS;
    }

    public void addLessonToCourse(Long courseId, LessonModel lesson) {
        CourseModel course = courseRepository.findById(courseId).orElse(null);
        if (course != null) {
            lesson.setCourseModel(course);
            course.addLesson(lesson);
            courseRepository.save(course);
        }
    }

    public void addMediaFile(String courseId, String filePath) {
        CourseModel course = courseRepository.findByCourseId(courseId);
        if (course != null) {
            course.getMediaFiles().add(filePath);
            courseRepository.save(course);
        }

    }
    public void deleteStudentFromCourse(Long courseId, Integer studentId) {
        CourseModel course = courseRepository.findById(courseId).orElse(null);
        if (course != null) {
            course.getStudents().removeIf(student -> student.getId() == studentId);
            courseRepository.save(course);
        }
    }



    // New method to delete all enrolled students
    public void deleteAllStudentsFromCourse(Long courseId) {
        CourseModel course = courseRepository.findById(courseId).orElse(null);
        if (course != null) {
            course.getStudents().clear();
            courseRepository.save(course);
        }
    }

    public void updateCourseDetails(Long courseId, CourseModel updatedCourse) {
        CourseModel existingCourse = courseRepository.findById(courseId).orElse(null);
        if (existingCourse != null) {
            existingCourse.setTitle(updatedCourse.getTitle());
            existingCourse.setDescription(updatedCourse.getDescription());
            existingCourse.setDurationHours(updatedCourse.getDurationHours());
            courseRepository.save(existingCourse);
        }
    }
    public List<StudentModel> getStudentsByCourseId(Long courseId) {
        CourseModel course = courseRepository.findById(courseId).orElse(null);
        if (course != null) {
            return course.getStudents(); // Return the list of students enrolled in the course
        }
        return new ArrayList<>(); // Return an empty list if the course is not found
    }
    //access media files
    public List<String> getMediaFilesByCourseId(Long courseId) {
        CourseModel course = courseRepository.findById(courseId).orElse(null);
        if (course != null) {
            return course.getMediaFiles(); // Return the list of media files
        }
        return new ArrayList<>(); // Return an empty list if the course is not found
    }

    //    This to check that the course id is unique
    public boolean courseIdExists(String courseId) {
        return courseRepository.existsByCourseId(courseId);
    }





}

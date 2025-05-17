package com.example.LMS.services;
import com.example.LMS.models.Assignment;
import com.example.LMS.repositories.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Collections;
import java.util.Optional;

@Service
public class AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;
    //submit assignment -> student
    public List<Assignment> getAssignmentsDueIn24Hours() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime targetTime = currentTime.plusHours(24);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        return assignmentRepository.findAssignmentsDueBetween(
                currentTime.format(formatter),
                targetTime.format(formatter)
        );
    }




    public Assignment submitAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    //create assignment ->instructor
    public void createAssignment(Assignment assignment) {
        assignmentRepository.save(assignment);
    }

    // grading and feedback -> instructor
    public Assignment gradeAssignment(Integer assignmentId, double grade, String feedback) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElse(null);
        if (assignment != null) {
            assignment.setGrades(grade);
            assignment.setFeedback(feedback);
            return assignmentRepository.save(assignment);
        }
        return null;
    }
}

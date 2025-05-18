package com.example.LMS.services;

import com.example.LMS.models.Assignment;
import com.example.LMS.repositories.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;

    @Autowired
    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

 
    public Assignment submitAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    // create assignment -> instructor
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

    /** NEW **/
    /********************************************************************************************/
    // Reminder feature: notify users before deadline
    @Scheduled(fixedRate = 3600000) // every hour
    public void sendDeadlineReminders() {
        List<Assignment> allAssignments = assignmentRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextHour = now.plusHours(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Assignment assignment : allAssignments) {
            try {
                LocalDateTime deadline = LocalDateTime.parse(assignment.getDeadline(), formatter);
                if (deadline.isAfter(now) && deadline.isBefore(nextHour)) {
                    System.out.println("[Reminder] Assignment '" + assignment.getTitle() + "' is due at " + assignment.getDeadline());
                }
            } catch (Exception e) {
                System.err.println("Failed to parse deadline for assignment ID " + assignment.getAssignmentID());
            }
        }
    }
    /*********************************************************************************************/
}

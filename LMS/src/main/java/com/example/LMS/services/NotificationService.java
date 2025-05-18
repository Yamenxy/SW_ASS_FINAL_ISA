package com.example.LMS.services;

import com.example.LMS.models.Assignment;
import com.example.LMS.models.CourseModel;
import com.example.LMS.models.NotificationModel;
import com.example.LMS.models.StudentModel;
import com.example.LMS.repositories.NotificationRepository;
import com.example.LMS.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
// for bouns
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JavaMailSender mailSender;
    @Scheduled(cron = "0 0 * * * ?") // Run every hour
    public void sendAssignmentDeadlineNotifications() {
        List<Assignment> assignmentsDue = assignmentService.getAssignmentsDueIn24Hours();

        for (Assignment assignment : assignmentsDue) {
            CourseModel course = assignment.getCourse();
            if (course != null) {
                List<StudentModel> students = course.getStudents();

                for (StudentModel student : students) {
                    String subject = "Reminder: Assignment Deadline Approaching";
                    String message = String.format("Hello %s,\n\nThis is a reminder that the assignment '%s' in course '%s' is due in 24 hours.\n\nPlease ensure you submit it before the deadline.",
                            student.getName(), assignment.getTitle(), course.getTitle());

                    sendEmailNotification(student.getEmail(), subject, message);
                }
            }
        }
    }

    // Fetch unread notifications for a specific user
    public List<NotificationModel> fetchUnreadNotifications(Integer userId) {
        return notificationRepository.findByUserIdAndIsReadFalse(userId);
    }

    // Fetch all notifications for a specific user
    public List<NotificationModel> fetchAllNotifications(Integer userId) {
        return notificationRepository.findByUserId(userId);
    }

    // Send or save a new notification
    public void sendNotification(NotificationModel notification) {
        notificationRepository.save(notification);
    }

    public void markAsRead(Integer notificationID) {
        NotificationModel notification = notificationRepository.findById(notificationID).orElse(null);
        if (notification != null) {
            notification.setIsRead(true);  // Mark the notification as read
            notificationRepository.save(notification);  // Save the updated notification to the database
        }
    }

    // Bouns
// Send email notification
    public void sendEmailNotification(String recipientEmail, String subject, String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientEmail);
        email.setSubject(subject);
        email.setText(message);
        mailSender.send(email);
    }


}
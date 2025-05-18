package com.example.LMS.services;

import com.example.LMS.models.StudentCourseCompletion;
import org.springframework.stereotype.Service;

/***NEW***/
@Service
public class CertificateService {

    public String generateCertificateText(StudentCourseCompletion completion) {
        StringBuilder certificate = new StringBuilder();

        certificate.append("===== Certificate of Completion =====\n");
        certificate.append("Student ID: ").append(completion.getStudentId()).append("\n");
        certificate.append("Course: ").append(completion.getCourseName()).append("\n");
        certificate.append("Completion Date: ").append(completion.getCompletionDate()).append("\n");
        certificate.append("\nCongratulations on your achievement!\n");
        certificate.append("\n-- LMS Team");

        return certificate.toString();
    }
}
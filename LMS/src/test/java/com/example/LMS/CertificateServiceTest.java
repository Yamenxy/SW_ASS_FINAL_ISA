package com.example.LMS;
import com.example.LMS.models.StudentCourseCompletion;
import com.example.LMS.services.CertificateService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


/***NEW**/
class CertificateServiceTest {

    @Test
    void testGenerateCertificateText() {
        CertificateService service = new CertificateService();
        StudentCourseCompletion completion = new StudentCourseCompletion();
        completion.setStudentId(101);
        completion.setCourseName("Spring Boot Essentials");
        completion.setCompletionDate(LocalDate.of(2025, 5, 18));

        String result = service.generateCertificateText(completion);

        assertTrue(result.contains("Certificate of Completion"));
        assertTrue(result.contains("Student ID: 101"));
        assertTrue(result.contains("Course: Spring Boot Essentials"));
        assertTrue(result.contains("Completion Date: 2025-05-18"));
    }
}

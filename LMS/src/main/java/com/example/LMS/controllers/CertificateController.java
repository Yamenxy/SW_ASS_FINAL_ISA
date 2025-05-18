package com.example.LMS.controllers;

import com.example.LMS.models.StudentCourseCompletion;
import com.example.LMS.services.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/***NEW**/
@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping("/text")
    public String getCertificateText(@RequestBody StudentCourseCompletion completion) {
        completion.setCompletionDate(LocalDate.now());
        return certificateService.generateCertificateText(completion);
    }
}

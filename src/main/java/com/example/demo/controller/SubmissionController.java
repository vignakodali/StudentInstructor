package com.example.demo.controller;
import com.example.demo.model.Submission;
import com.example.demo.service.SubmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {
    private final SubmissionService submissionService;
    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }
    @PostMapping
    public ResponseEntity<String> submitAssignment(@RequestBody Submission submission) {
        submissionService.submitAssignment(submission);
        return ResponseEntity.ok("Assignment submitted successfully.");
    }
    @GetMapping("/{assignmentId}")
    public ResponseEntity<List<Submission>> getSubmissionsByAssignmentId(@PathVariable String assignmentId) {
        return ResponseEntity.ok(submissionService.getSubmissionsByAssignmentId(assignmentId));
    }
    @PatchMapping("/{assignmentId}/{studentId}")
    public ResponseEntity<String> gradeSubmission(
            @PathVariable String assignmentId,
            @PathVariable String studentId,
            @RequestBody int marks) {
        submissionService.gradeSubmission(assignmentId, studentId, marks);
        return ResponseEntity.ok("Submission graded successfully.");
    }
}

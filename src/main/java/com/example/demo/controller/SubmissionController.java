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
    @GetMapping("/{assignmentId}/{studentId}")
    public ResponseEntity<Submission> getSubmissionByAssignmentAndStudent(
            @PathVariable String assignmentId,
            @PathVariable String studentId) {
        return ResponseEntity.ok(submissionService.getSubmissionByAssignmentAndStudent(assignmentId, studentId));
    }
    @PatchMapping("/{assignmentId}/{studentId}")//manually updating grades
    public ResponseEntity<String> gradeSubmission(
            @PathVariable String assignmentId,
            @PathVariable String studentId,
            @RequestBody Integer marks) {
        submissionService.gradeSubmission(assignmentId, studentId, marks);
        return ResponseEntity.ok("Submission graded successfully.");
    }
}

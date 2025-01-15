package com.example.demo.controller;
import com.example.demo.model.Assignment;
import com.example.demo.service.AssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {
    private final AssignmentService assignmentService;
    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }
    @PostMapping
    public ResponseEntity<String> createAssignment(@RequestBody Assignment assignment) {
        assignmentService.createAssignment(assignment);
        return ResponseEntity.ok("Assignment created successfully.");
    }
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Assignment>> getAssignmentsByCourseId(@PathVariable String courseId) {
        return ResponseEntity.ok(assignmentService.getAssignmentsByCourseId(courseId));
    }
}

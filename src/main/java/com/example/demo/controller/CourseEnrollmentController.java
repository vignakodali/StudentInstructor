package com.example.demo.controller;
import com.example.demo.model.CourseEnrollment;
import com.example.demo.service.CourseEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/course-enrollments")
public class CourseEnrollmentController {
    private final CourseEnrollmentService courseEnrollmentService;
    @Autowired
    public CourseEnrollmentController(CourseEnrollmentService courseEnrollmentService) {
        this.courseEnrollmentService = courseEnrollmentService;
    }
    @PostMapping("/enroll")
    public ResponseEntity<String> enrollUser(@RequestBody CourseEnrollment enrollment) {
        courseEnrollmentService.enrollUser(enrollment);
        return ResponseEntity.ok("User enrolled successfully.");
    }
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<String>> getStudentIdsByCourseId(@PathVariable String courseId) {
        List<String> studentIds = courseEnrollmentService.getStudentIdsByCourseId(courseId);
        return ResponseEntity.ok(studentIds);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<String>> getCourseIdsByUserId(@PathVariable String userId) {
        List<String> courseIds = courseEnrollmentService.getCourseIdsByUserId(userId);
        return ResponseEntity.ok(courseIds);
    }
}

package com.example.demo.controller;
import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    @PostMapping
    public ResponseEntity<String> createCourse(@RequestBody Course course) {
        courseService.createCourse(course);
        return ResponseEntity.ok("Course created successfully.");
    }
    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourse(@PathVariable String courseId) {
        Course course = courseService.getCourse(courseId);
        if (course != null) {
            return ResponseEntity.ok(course);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }
    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable String courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok("Course deleted successfully.");
    }
}

package com.example.demo.controller;
import com.example.demo.model.Discussion;
import com.example.demo.service.DiscussionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/discussions")
public class DiscussionController {
    private final DiscussionService discussionService;
    public DiscussionController(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }
    @PostMapping
    public ResponseEntity<String> createDiscussion(@RequestBody Discussion discussion) {
        discussionService.createDiscussion(discussion);
        return ResponseEntity.ok("Discussion created successfully.");
    }
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Discussion>> getDiscussionsByCourseId(@PathVariable String courseId) {
        return ResponseEntity.ok(discussionService.getDiscussionsByCourseId(courseId));
    }
}

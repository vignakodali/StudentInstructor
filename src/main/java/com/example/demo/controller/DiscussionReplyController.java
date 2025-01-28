package com.example.demo.controller;
import com.example.demo.model.DiscussionReply;
import com.example.demo.service.DiscussionReplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/discussions/replies")
public class DiscussionReplyController {
    private final DiscussionReplyService replyService;

    public DiscussionReplyController(DiscussionReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping
    public ResponseEntity<String> addReply(@RequestBody DiscussionReply reply) {
        replyService.addReply(reply);
        return ResponseEntity.ok("Reply added successfully.");
    }

    @GetMapping("/{discussionId}")
    public ResponseEntity<List<DiscussionReply>> getRepliesByDiscussionId(@PathVariable String discussionId) {
        return ResponseEntity.ok(replyService.getRepliesByDiscussionId(discussionId));
    }
}


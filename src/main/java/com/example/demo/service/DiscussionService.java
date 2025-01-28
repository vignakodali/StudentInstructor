package com.example.demo.service;

import com.example.demo.model.Discussion;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscussionService {
    private final DynamoDbTable<Discussion> discussionTable;

    public DiscussionService(DynamoDbEnhancedClient enhancedClient) {
        this.discussionTable = enhancedClient.table("Discussion", TableSchema.fromBean(Discussion.class));
    }

    public void createDiscussion(Discussion discussion) {
        discussionTable.putItem(discussion);
    }

    public List<Discussion> getDiscussionsByCourseId(String courseId) {
        return discussionTable.scan()
                .items()
                .stream()
                .filter(d -> d.getCourseId().equals(courseId))
                .collect(Collectors.toList());
    }
}

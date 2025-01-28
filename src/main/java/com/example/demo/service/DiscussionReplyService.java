package com.example.demo.service;
import com.example.demo.model.DiscussionReply;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@Service
public class DiscussionReplyService {
    private final DynamoDbTable<DiscussionReply> replyTable;
    public DiscussionReplyService(DynamoDbEnhancedClient enhancedClient) {
        this.replyTable = enhancedClient.table("DiscussionReply", TableSchema.fromBean(DiscussionReply.class));
    }
    public void addReply(DiscussionReply reply) {
        replyTable.putItem(reply);
    }
    public List<DiscussionReply> getRepliesByDiscussionId(String discussionId) {
        System.out.println("Fetching replies for discussionId: " + discussionId);
            List<DiscussionReply> replies = StreamSupport.stream(
                replyTable
                    .query(r -> r.queryConditional(
                        QueryConditional.keyEqualTo(k -> k.partitionValue(discussionId)) 
                    ))
                    .items()
                    .spliterator(),
                false
            ).collect(Collectors.toList());
    
        if (replies.isEmpty()) {
            System.out.println("No replies found for discussionId: " + discussionId);
        } else {
            System.out.println("Retrieved " + replies.size() + " replies for discussionId: " + discussionId);
        }
        return replies;
    }
    
    
}

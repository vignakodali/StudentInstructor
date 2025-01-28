package com.example.demo.model;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;
@DynamoDbBean
public class DiscussionReply {
    private String replyId;
    private String discussionId;
    private String userId;
    private String parentReplyId;
    private String content;
    private String createdAt;
    @DynamoDbPartitionKey
    public String getReplyId() {
        return replyId;
    }
    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }
    @DynamoDbSortKey
    public String getDiscussionId() {
        return discussionId;
    }
    public void setDiscussionId(String discussionId) {
        this.discussionId = discussionId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getParentReplyId() {
        return parentReplyId;
    }
    public void setParentReplyId(String parentReplyId) {
        this.parentReplyId = parentReplyId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}

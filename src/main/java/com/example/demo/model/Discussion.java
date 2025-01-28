package com.example.demo.model;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
@DynamoDbBean
public class Discussion {
    private String discussionId;
    private String courseId;
    private String instructorId;
    private String question;
    private String createdAt;
    @DynamoDbPartitionKey
    public String getDiscussionId() {
        return discussionId;
    }
    public void setDiscussionId(String discussionId) {
        this.discussionId = discussionId;
    }
    public String getCourseId() {
        return courseId;
    }
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    public String getInstructorId() {
        return instructorId;
    }
    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}

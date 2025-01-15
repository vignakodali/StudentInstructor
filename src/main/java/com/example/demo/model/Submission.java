package com.example.demo.model;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;
@DynamoDbBean
public class Submission {
    private String assignmentId;
    private String studentId;
    private String submissionContent;
    private String submittedAt;
    private Integer marks;
    @DynamoDbPartitionKey
    public String getAssignmentId() {
        return assignmentId;
    }
    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }
    @DynamoDbSortKey
    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public String getSubmissionContent() {
        return submissionContent;
    }
    public void setSubmissionContent(String submissionContent) {
        this.submissionContent = submissionContent;
    }
    public String getSubmittedAt() {
        return submittedAt;
    }
    public void setSubmittedAt(String submittedAt) {
        this.submittedAt = submittedAt;
    }
    public Integer getMarks() {
        return marks;
    }
    public void setMarks(Integer marks) {
        this.marks = marks;
    }
}

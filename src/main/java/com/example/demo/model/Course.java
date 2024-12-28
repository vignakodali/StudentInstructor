package com.example.demo.model;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
@DynamoDbBean
public class Course {
    private String courseId;      
    private String courseName;    
    private String instructorId;   
    @DynamoDbPartitionKey
    public String getCourseId() {
        return courseId;
    }
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    @DynamoDbAttribute("CourseName")
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    @DynamoDbAttribute("InstructorId")
    public String getInstructorId() {
        return instructorId;
    }
    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }
}

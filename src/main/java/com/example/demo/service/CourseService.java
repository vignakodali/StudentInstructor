package com.example.demo.service;
import com.example.demo.model.Course;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CourseService {
    private final DynamoDbTable<Course> courseTable;
    public CourseService(DynamoDbEnhancedClient enhancedClient) {
        this.courseTable = enhancedClient.table("Course", TableSchema.fromBean(Course.class));
    }
    public void createCourse(Course course) {
        courseTable.putItem(course);
    }
    public Course getCourse(String courseId) {
        return courseTable.getItem(r -> r.key(k -> k.partitionValue(courseId)));
    }
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        courseTable.scan().items().forEach(courses::add);
        return courses;
    }
    public void deleteCourse(String courseId) {
        courseTable.deleteItem(r -> r.key(k -> k.partitionValue(courseId)));
    }
        public List<Course> searchCoursesByPrefix(String prefix) {
            return courseTable.scan()
                    .items()
                    .stream()
                    .filter(course -> course.getCourseName().toLowerCase().startsWith(prefix.toLowerCase()))
                    .collect(Collectors.toList());
        }
    
    }
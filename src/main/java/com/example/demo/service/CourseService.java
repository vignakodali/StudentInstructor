package com.example.demo.service;
import com.example.demo.model.Course;
import com.example.demo.model.CourseEnrollment;
import com.example.demo.model.User;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CourseService {
    private final DynamoDbTable<Course> courseTable;
    private final DynamoDbTable<CourseEnrollment> courseEnrollmentTable;
    private final DynamoDbTable<User> userTable;
    public CourseService(DynamoDbEnhancedClient enhancedClient) {
        this.courseTable = enhancedClient.table("Course", TableSchema.fromBean(Course.class));
        this.courseEnrollmentTable = enhancedClient.table("CourseEnrollment", TableSchema.fromBean(CourseEnrollment.class));
        this.userTable = enhancedClient.table("User", TableSchema.fromBean(User.class));

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
        public List<User> getPeopleInCourse(String courseId) {
        List<User> users = new ArrayList<>();
        Course course = courseTable.getItem(r -> r.key(k -> k.partitionValue(courseId)));
        if (course != null) {
            User instructor = userTable.getItem(r -> r.key(k -> k.partitionValue(course.getInstructorId())));
            if (instructor != null) {
                users.add(instructor);
            }
            List<String> studentIds = courseEnrollmentTable.query(r -> r.queryConditional(QueryConditional.keyEqualTo(k -> k.partitionValue(courseId))))
                    .items()
                    .stream()
                    .map(CourseEnrollment::getUserId)
                    .collect(Collectors.toList());
            studentIds.forEach(studentId -> {
                User student = userTable.getItem(r -> r.key(k -> k.partitionValue(studentId)));
                if (student != null) {
                    users.add(student);
                }
            });
        }
        return users;
    }
    
    }
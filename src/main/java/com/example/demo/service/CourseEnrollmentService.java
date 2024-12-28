package com.example.demo.service;
import com.example.demo.model.CourseEnrollment;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CourseEnrollmentService {
    private final DynamoDbTable<CourseEnrollment> courseEnrollmentTable;
    public CourseEnrollmentService(DynamoDbEnhancedClient enhancedClient) {
        this.courseEnrollmentTable = enhancedClient.table("CourseEnrollment", TableSchema.fromBean(CourseEnrollment.class));
    }
    public void enrollUser(CourseEnrollment enrollment) {
        courseEnrollmentTable.putItem(enrollment);
    }
    public List<String> getStudentIdsByCourseId(String courseId) {
        return courseEnrollmentTable.query(r -> r.queryConditional(QueryConditional.keyEqualTo(k -> k.partitionValue(courseId))))
                .items()
                .stream()
                .map(CourseEnrollment::getUserId)
                .collect(Collectors.toList());
    }
    public List<String> getCourseIdsByUserId(String userId) {
        return courseEnrollmentTable.scan()
                .items()
                .stream()
                .filter(e -> e.getUserId().equals(userId)) 
                .map(CourseEnrollment::getCourseId)
                .collect(Collectors.toList());
    }
}

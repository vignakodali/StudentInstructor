package com.example.demo.service;
import com.example.demo.model.Assignment;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class AssignmentService {
    private final DynamoDbTable<Assignment> assignmentTable;
    public AssignmentService(DynamoDbEnhancedClient enhancedClient) {
        this.assignmentTable = enhancedClient.table("Assignment", TableSchema.fromBean(Assignment.class));
    }
    public void createAssignment(Assignment assignment) {
        assignmentTable.putItem(assignment);
    }
    public List<Assignment> getAssignmentsByCourseId(String courseId) {
        return assignmentTable.scan()
                .items()
                .stream()
                .filter(a -> a.getCourseId().equals(courseId))
                .collect(Collectors.toList());
    }
}

package com.example.demo.service;
import com.example.demo.model.Submission;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@Service
public class SubmissionService {
    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private final DynamoDbTable<Submission> submissionTable;

    public SubmissionService(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
        this.submissionTable = dynamoDbEnhancedClient.table("Submission", TableSchema.fromBean(Submission.class));
    }
    public void submitAssignment(Submission submission) {
        if (submission.getAssignmentId() == null || submission.getStudentId() == null) {
            throw new IllegalArgumentException("Assignment ID and Student ID cannot be null.");
        }
        submissionTable.putItem(submission);
        System.out.println("Submission added: " + submission);
    }
    public List<Submission> getSubmissionsByAssignmentId(String assignmentId) {
        return StreamSupport.stream(submissionTable.query(
                        QueryConditional.keyEqualTo(k -> k.partitionValue(assignmentId)))
                .items().spliterator(), false)
                .collect(Collectors.toList());
    }
    public Submission getSubmissionByAssignmentAndStudent(String assignmentId, String studentId) {
        return submissionTable.getItem(r -> r.key(k -> k.partitionValue(assignmentId).sortValue(studentId)));
    }
    public void gradeSubmission(String assignmentId, String studentId, int marks) {
        Submission submission = getSubmissionByAssignmentAndStudent(assignmentId, studentId);
        if (submission != null) {
            submission.setMarks(marks);
            submissionTable.putItem(submission);
            System.out.println("Graded submission: " + submission);
        } else {
            throw new IllegalArgumentException("Submission not found for grading.");
        }
    }
    public void validateAndGradeSubmissions(String assignmentId, String correctAnswer) {
        List<Submission> submissions = StreamSupport.stream(
                submissionTable.query(QueryConditional.keyEqualTo(k -> k.partitionValue(assignmentId))).items().spliterator(),
                false
        ).collect(Collectors.toList());
        for (Submission submission : submissions) {
            if (submission.getSubmissionContent() != null) {
                boolean isCorrect = submission.getSubmissionContent().equalsIgnoreCase(correctAnswer);
                submission.setMarks(isCorrect ? 100 : 95);
                submissionTable.putItem(submission);
                System.out.println("Graded submission: " + submission);
            } else {
                System.out.println("Skipped submission with missing content: " + submission);
            }
        }
        System.out.println("Completed grading for assignment: " + assignmentId);
    }

}

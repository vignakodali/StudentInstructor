package com.example.demo.service;
import com.example.demo.model.Submission;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class SubmissionService {
    private final List<Submission> submissions = new ArrayList<>();
    public void submitAssignment(Submission submission) {
        if (submission.getAssignmentId() == null || submission.getStudentId() == null) {
            throw new IllegalArgumentException("Assignment ID and Student ID cannot be null.");
        }
        submissions.add(submission); 
        System.out.println("Submission added: " + submission);
    }
    public List<Submission> getSubmissionsByAssignmentId(String assignmentId) {
        List<Submission> result = new ArrayList<>();
        for (Submission s : submissions) {
            if (s.getAssignmentId().equals(assignmentId)) {
                result.add(s);
            }
        }
        return result;
    }
    public void gradeSubmission(String assignmentId, String studentId, int marks) {
        for (Submission s : submissions) {
            if (s.getAssignmentId().equals(assignmentId) && s.getStudentId().equals(studentId)) {
                s.setMarks(marks);
                System.out.println("Graded submission: " + s);
                return;
            }
        }
        throw new IllegalArgumentException("Submission not found for grading.");
    }
}

package com.example.demo.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.List;

@Component
public class AssignmentGradingScheduler {
    private final SqsClient sqsClient;
    private static final String QUEUE_URL = "https://sqs.us-east-1.amazonaws.com/339712832095/AssignmentGradingQueue";
    public AssignmentGradingScheduler(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }
    @Scheduled(cron = "0 0 0 * * ?") 
    public void enqueueTask() {
        try {
            SendMessageRequest request = SendMessageRequest.builder()
                    .queueUrl(QUEUE_URL)
                    .messageBody("{\"assignmentId\": \"Assign1\", \"correctAnswer\": \"Machine Learning\"}")
                    .build();
            sqsClient.sendMessage(request);
            System.out.println("Task enqueued successfully.");
        } catch (Exception e) {
            System.err.println("Failed to enqueue task: " + e.getMessage());
        }
    }
    public void processTask() {
        try {
            ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder()
                    .queueUrl(QUEUE_URL)
                    .maxNumberOfMessages(1)
                    .visibilityTimeout(60)
                    .waitTimeSeconds(10)  
                    .build();

            List<Message> messages = sqsClient.receiveMessage(receiveRequest).messages();

            for (Message message : messages) {
                System.out.println("Processing task: " + message.body());
                handleTask(message.body());
                deleteMessage(message);
            }
        } catch (Exception e) {
            System.err.println("Failed to process task: " + e.getMessage());
        }
    }

    private void handleTask(String messageBody) {
        System.out.println("Task handled with body: " + messageBody);
    }

    private void deleteMessage(Message message) {
        try {
            DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
                    .queueUrl(QUEUE_URL)
                    .receiptHandle(message.receiptHandle())
                    .build();
            sqsClient.deleteMessage(deleteRequest);
            System.out.println("Task completed. Message deleted from queue.");
        } catch (Exception e) {
            System.err.println("Failed to delete message: " + e.getMessage());
        }
    }
}

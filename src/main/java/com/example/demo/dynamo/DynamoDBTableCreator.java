package com.example.demo.dynamo;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

public class DynamoDBTableCreator {
    private final DynamoDbClient dynamoDbClient;

    public DynamoDBTableCreator() {
        String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
        String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");
        if (accessKey == null || secretKey == null) {
            throw new IllegalArgumentException("AWS Access Key or Secret Key is missing.");
        }
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
        this.dynamoDbClient = DynamoDbClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(Region.US_EAST_1)
                .build();
    }

    public void initializeTables() {
        createTableIfNotExists("User", "userId");
        createTableIfNotExists("Course", "courseId");
        createCourseEnrollmentTableIfNotExists();
        createAssignmentTableIfNotExists();
        createSubmissionTableIfNotExists();
        createDiscussionTableIfNotExists();
        createDiscussionReplyTableIfNotExists();
        createTaskLocksTableIfNotExists(); // Add TaskLocks table creation
    }

    private void createTaskLocksTableIfNotExists() {
        String tableName = "TaskLocks";
        try {
            dynamoDbClient.describeTable(r -> r.tableName(tableName));
            System.out.println("Table already exists: " + tableName);
        } catch (ResourceNotFoundException e) {
            System.out.println("Creating table: " + tableName);
            CreateTableRequest createTableRequest = CreateTableRequest.builder()
                    .tableName(tableName)
                    .keySchema(KeySchemaElement.builder().attributeName("TaskName").keyType(KeyType.HASH).build())
                    .attributeDefinitions(AttributeDefinition.builder().attributeName("TaskName").attributeType(ScalarAttributeType.S).build())
                    .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(5L).writeCapacityUnits(5L).build())
                    .build();
            dynamoDbClient.createTable(createTableRequest);
            System.out.println("Table created successfully: " + tableName);
        }
    }

    private void createTableIfNotExists(String tableName, String primaryKey) {
        try {
            dynamoDbClient.describeTable(r -> r.tableName(tableName));
            System.out.println("Table already exists: " + tableName);
        } catch (ResourceNotFoundException e) {
            System.out.println("Creating table: " + tableName);
            CreateTableRequest createTableRequest = CreateTableRequest.builder()
                    .tableName(tableName)
                    .keySchema(KeySchemaElement.builder().attributeName(primaryKey).keyType(KeyType.HASH).build())
                    .attributeDefinitions(AttributeDefinition.builder().attributeName(primaryKey).attributeType(ScalarAttributeType.S).build())
                    .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(5L).writeCapacityUnits(5L).build())
                    .build();
            dynamoDbClient.createTable(createTableRequest);
            System.out.println("Table created successfully: " + tableName);
        }
    }

    private void createCourseEnrollmentTableIfNotExists() {
        String tableName = "CourseEnrollment";
        try {
            dynamoDbClient.describeTable(r -> r.tableName(tableName));
            System.out.println("Table already exists: " + tableName);
        } catch (ResourceNotFoundException e) {
            System.out.println("Creating table: " + tableName);
            CreateTableRequest createTableRequest = CreateTableRequest.builder()
                    .tableName(tableName)
                    .keySchema(
                            KeySchemaElement.builder().attributeName("courseId").keyType(KeyType.HASH).build(),
                            KeySchemaElement.builder().attributeName("userId").keyType(KeyType.RANGE).build())
                    .attributeDefinitions(
                            AttributeDefinition.builder().attributeName("courseId").attributeType(ScalarAttributeType.S).build(),
                            AttributeDefinition.builder().attributeName("userId").attributeType(ScalarAttributeType.S).build())
                    .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(5L).writeCapacityUnits(5L).build())
                    .build();
            dynamoDbClient.createTable(createTableRequest);
            System.out.println("Table created successfully: " + tableName);
        }
    }

    private void createAssignmentTableIfNotExists() {
        String tableName = "Assignment";
        try {
            dynamoDbClient.describeTable(r -> r.tableName(tableName));
            System.out.println("Table already exists: " + tableName);
        } catch (ResourceNotFoundException e) {
            System.out.println("Creating table: " + tableName);
            CreateTableRequest createTableRequest = CreateTableRequest.builder()
                    .tableName(tableName)
                    .keySchema(KeySchemaElement.builder().attributeName("assignmentId").keyType(KeyType.HASH).build())
                    .attributeDefinitions(AttributeDefinition.builder().attributeName("assignmentId").attributeType(ScalarAttributeType.S).build())
                    .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(5L).writeCapacityUnits(5L).build())
                    .build();
            dynamoDbClient.createTable(createTableRequest);
            System.out.println("Table created successfully: " + tableName);
        }
    }

    private void createSubmissionTableIfNotExists() {
        String tableName = "Submission";
        try {
            dynamoDbClient.describeTable(r -> r.tableName(tableName));
            System.out.println("Table already exists: " + tableName);
        } catch (ResourceNotFoundException e) {
            System.out.println("Creating table: " + tableName);
            CreateTableRequest createTableRequest = CreateTableRequest.builder()
                    .tableName(tableName)
                    .keySchema(
                            KeySchemaElement.builder().attributeName("assignmentId").keyType(KeyType.HASH).build(),
                            KeySchemaElement.builder().attributeName("studentId").keyType(KeyType.RANGE).build())
                    .attributeDefinitions(
                            AttributeDefinition.builder().attributeName("assignmentId").attributeType(ScalarAttributeType.S).build(),
                            AttributeDefinition.builder().attributeName("studentId").attributeType(ScalarAttributeType.S).build())
                    .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(5L).writeCapacityUnits(5L).build())
                    .build();
            dynamoDbClient.createTable(createTableRequest);
            System.out.println("Table created successfully: " + tableName);
        }
    }

    private void createDiscussionTableIfNotExists() {
        String tableName = "Discussion";
        try {
            dynamoDbClient.describeTable(r -> r.tableName(tableName));
            System.out.println("Table already exists: " + tableName);
        } catch (ResourceNotFoundException e) {
            System.out.println("Creating table: " + tableName);
            CreateTableRequest createTableRequest = CreateTableRequest.builder()
                    .tableName(tableName)
                    .keySchema(KeySchemaElement.builder().attributeName("discussionId").keyType(KeyType.HASH).build())
                    .attributeDefinitions(AttributeDefinition.builder().attributeName("discussionId").attributeType(ScalarAttributeType.S).build())
                    .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(5L).writeCapacityUnits(5L).build())
                    .build();
            dynamoDbClient.createTable(createTableRequest);
            System.out.println("Table created successfully: " + tableName);
        }
    }

    private void createDiscussionReplyTableIfNotExists() {
        String tableName = "DiscussionReply";
        try {
            dynamoDbClient.describeTable(r -> r.tableName(tableName));
            System.out.println("Table already exists: " + tableName);
        } catch (ResourceNotFoundException e) {
            System.out.println("Creating table: " + tableName);
            CreateTableRequest createTableRequest = CreateTableRequest.builder()
                    .tableName(tableName)
                    .keySchema(
                            KeySchemaElement.builder().attributeName("discussionId").keyType(KeyType.HASH).build(),
                            KeySchemaElement.builder().attributeName("replyId").keyType(KeyType.RANGE).build())
                    .attributeDefinitions(
                            AttributeDefinition.builder().attributeName("discussionId").attributeType(ScalarAttributeType.S).build(),
                            AttributeDefinition.builder().attributeName("replyId").attributeType(ScalarAttributeType.S).build())
                    .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(5L).writeCapacityUnits(5L).build())
                    .build();
            dynamoDbClient.createTable(createTableRequest);
            System.out.println("Table created successfully: " + tableName);
        }
    }
}

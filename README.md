# Student Enrollment System

This project is a **Student Enrollment System** designed to manage student and instructor data, with features for course enrollment, DynamoDB integration and API support using Spring Boot.

## Features

- API endpoints to manage:
  - Students
  - Instructors
  - Courses
  - Enrollments
- DynamoDB integration for persistent data storage.
- Deployed on AWS EKS using Kubernetes.
- Dockerized application hosted on AWS ECR.
- Configurable Load Balancer for external access to the application.

## Project Structure

```plaintext
├── src/main/java
│   ├── com.example.demo
│   │   ├── controller     # Controllers
│   │   ├── model          # Entity classes
│   │   ├── repository     # DynamoDB repositories
│   │   ├── service        # Service layer
│   │   └── DemoApplication.java  # Main application
├── src/main/resources
│   ├── application.properties # Configuration for AWS DynamoDB and server
├── Dockerfile              # Dockerfile for containerizing the application
├── deployment.yaml         # Kubernetes Deployment and Service configuration
├── README.md               # Project documentation
└── pom.xml                 # Maven dependencies
```

## Prerequisites

- **Java 17**
- **Maven**
- **Docker**
- **AWS CLI**
- **Kubernetes CLI (kubectl)**
- **Postman (for API testing)**
- AWS account with permissions for ECR, EKS, and DynamoDB.

## Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/vignakodali/StudentInstructor.git
cd StudentInstructor
```

### 2. Build the Application
```bash
mvn clean package
```

### 3. Dockerize the Application
- Build the Docker image:
  ```bash
  docker build -t student-enrollment:1.0 .
  ```
- Tag the Docker image for AWS ECR:
  ```bash
  docker tag student-enrollment:1.0 <your-account-id>.dkr.ecr.<region>.amazonaws.com/student-enrollment:1.0
  ```
- Push the image to ECR:
  ```bash
  aws ecr get-login-password --region <region> | docker login --username AWS --password-stdin <your-account-id>.dkr.ecr.<region>.amazonaws.com
  docker push <your-account-id>.dkr.ecr.<region>.amazonaws.com/student-enrollment:1.0
  ```

### 4. Deploy to AWS EKS
- Apply the Kubernetes Deployment:
  ```bash
  kubectl apply -f deployment.yaml
  ```
- Verify the pods and services:
  ```bash
  kubectl get pods
  kubectl get svc
  ```

### 5. Test the API
Use Postman to interact with the API:
- Example API Endpoint:
  ```plaintext
  http://<LoadBalancer-DNS>/api/users
  ```
- Sample Request Body:
  ```json
  {
    "userId": "ra1234",
    "email": "radhi@gmail.com",
    "role": "instructor"
  }
  ```

## Configuration

### `application.properties`
- Configure your AWS credentials and DynamoDB table names:
  ```properties
  aws.accessKeyId=YOUR_ACCESS_KEY
  aws.secretKey=YOUR_SECRET_KEY
  dynamodb.tableName=StudentEnrollment
  ```

### Kubernetes Configuration
- `deployment.yaml` defines the Deployment and LoadBalancer:
  ```yaml
  apiVersion: apps/v1
  kind: Deployment
  metadata:
    name: myapp-deployment
  spec:
      replicas: 2
      selector:
        matchLabels:
          app: myapp
      template:
        metadata:
          labels:
            app: myapp
        spec:
          containers:
          - name: myapp-container
            image: <ECR-repo-uri>/student-enrollment:1.0
  ```

package com.example.demo.uploader;
import com.example.demo.model.Course;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import java.io.IOException;
import java.util.Random;
public class CourseDataUploader {
    private static final String API_URL = "http://localhost:8080/api/courses"; 
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Random random = new Random();
    private static final String[] COURSE_NAMES = {
            "Mathematics for Beginners",
            "Introduction to Physics",
            "Advanced Chemistry",
            "Programming with Python",
            "Java Fundamentals",
            "Data Science Basics",
            "Artificial Intelligence",
            "Web Development with React",
            "Mobile App Development",
            "Database Design and Management",
            "Cybersecurity Essentials",
            "Digital Marketing Strategies",
            "Project Management 101",
            "Cloud Computing",
            "Machine Learning Algorithms",
            "Blockchain Technology",
            "Introduction to Networking",
            "Operating Systems",
            "Big Data Analytics",
            "Ethical Hacking",
            "Graphic Design Fundamentals",
            "Photography Basics",
            "Financial Accounting",
            "Business Analysis Techniques",
            "Entrepreneurship and Innovation",
            "Social Media Marketing",
            "Game Development with Unity",
            "Creative Writing",
            "Introduction to Psychology",
            "Philosophy and Logic",
            "History of Art",
            "Music Theory",
            "Environmental Science",
            "Healthcare Informatics",
            "Robotics and Automation",
            "AI for Robotics",
            "Astronomy and Space Science",
            "Digital Signal Processing",
            "Embedded Systems Design",
            "Supply Chain Management",
            "Economics for Beginners",
            "Public Speaking and Communication",
            "Leadership Development",
            "Sports Analytics",
            "Introduction to Sociology",
            "Civil Engineering Basics",
            "Electrical Engineering Principles",
            "Mechanical Engineering Concepts",
            "Biomedical Engineering Overview",
            "Nanotechnology Fundamentals",
            "Renewable Energy Solutions"
    };
    public static void main(String[] args) {
        for (int i = 1; i <= 50; i++) {
            Course course = new Course();
            course.setCourseId("C" + i);
            course.setCourseName(getRandomCourseName());
            course.setInstructorId("I" + random.nextInt(10));
            try {
                String jsonPayload = objectMapper.writeValueAsString(course);
                sendPostRequest(jsonPayload);
            } catch (Exception e) {
                System.err.println("Failed to send request for course ID: C" + i);
                e.printStackTrace();
            }
        }
    }
    private static String getRandomCourseName() {
        return COURSE_NAMES[random.nextInt(COURSE_NAMES.length)];
    }
    private static void sendPostRequest(String jsonPayload) throws IOException {
        RequestBody body = RequestBody.create(jsonPayload, MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Successfully uploaded: " + response.body().string());
            } else {
                System.err.println("Failed to upload: " + response.body().string());
            }
        }
    }
}

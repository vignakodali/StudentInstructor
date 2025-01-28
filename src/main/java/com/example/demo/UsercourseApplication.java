package com.example.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.example.demo.dynamo.DynamoDBTableCreator;
@SpringBootApplication
@EnableScheduling
public class UsercourseApplication {
	public static void main(String[] args) {
		SpringApplication.run(UsercourseApplication.class, args);	
        DynamoDBTableCreator tableCreator = new DynamoDBTableCreator();
        tableCreator.initializeTables();
} 
}

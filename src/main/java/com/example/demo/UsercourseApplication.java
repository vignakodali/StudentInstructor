package com.example.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.demo.dynamo.DynamoDBTableCreator;
@SpringBootApplication
public class UsercourseApplication {
	public static void main(String[] args) {
		SpringApplication.run(UsercourseApplication.class, args);	
        DynamoDBTableCreator tableCreator = new DynamoDBTableCreator();
        tableCreator.initializeTables();
} 
}

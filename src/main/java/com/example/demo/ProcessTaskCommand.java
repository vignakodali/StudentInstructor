package com.example.demo;

import com.example.demo.scheduler.AssignmentGradingScheduler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProcessTaskCommand implements CommandLineRunner {

    private final AssignmentGradingScheduler scheduler;

    public ProcessTaskCommand(AssignmentGradingScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void run(String... args) {
        while (true) {
            scheduler.processTask(); 
        }
    }
}

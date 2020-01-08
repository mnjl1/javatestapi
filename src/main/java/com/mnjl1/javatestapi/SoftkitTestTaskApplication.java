package com.mnjl1.javatestapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SoftkitTestTaskApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SoftkitTestTaskApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("testing command line...");
}
}

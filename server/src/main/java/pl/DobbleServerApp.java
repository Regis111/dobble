package pl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DobbleServerApp {

    public static void main(final String[] args) {
        System.out.println("Hello World!");
        SpringApplication.run(DobbleServerApp.class, args);
    }
}
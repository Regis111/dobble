package pl.dobblepolskab;

import gamecontent.GameContent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(GameContent.class)
public class DobbleServerApp {
    public static void main(final String[] args) {
        SpringApplication.run(DobbleServerApp.class, args);
    }
}
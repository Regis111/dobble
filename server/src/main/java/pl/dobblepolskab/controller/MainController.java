package pl.dobblepolskab.controller;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.Random;

@Controller
public class MainController {

    @SubscribeMapping("/dobblePair")
    public String helloFromDobble(@Payload Message<?> message) {
        String[] winOrLose = {"WIN", "LOSE"};
        Random r = new Random();
        int ind = r.nextInt(winOrLose.length);
        return "Hello from Dobble! You " + winOrLose[ind];
    }
}

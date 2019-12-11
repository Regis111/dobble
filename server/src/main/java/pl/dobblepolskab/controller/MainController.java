package pl.dobblepolskab.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @RequestMapping("/hello")
    public String helloFromDobble() {
        return "Hello from Dobble!";
    }
}

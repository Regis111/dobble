package pl.dobblepolskab.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import messages.requests.*;
import messages.responses.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class MainController {

    private Logger logger = LoggerFactory.getLogger(MainController.class);

    private Handler handler;

    @Autowired
    public MainController(Handler handler) {
        this.handler = handler;
    }

    @MessageMapping("/amIWinner")
    public void amIWinner(StompHeaderAccessor headers, String json) throws JsonProcessingException {
        AmIWinnerResponse request = new ObjectMapper().readValue(json, AmIWinnerResponse.class);
        this.handler.amIWinnerHandle(request);
    }

    @MessageMapping("/init")
    public void init(String json) throws JsonProcessingException {
        InitRequest request = new ObjectMapper().readValue(json, InitRequest.class);
        logger.info("In init method");
        this.handler.initHandle(request);
    }

    @MessageMapping("/deletePlayer")
    public void deletePlayer(String json) throws JsonProcessingException {
        DeletePlayerRequest request = new ObjectMapper().readValue(json, DeletePlayerRequest.class);
        this.handler.deletePlayerHandle(request);
    }

    @MessageMapping("/addPlayer")
    public void addPlayer(String json) throws JsonProcessingException {
        AddPlayerRequest request = new ObjectMapper().readValue(json, AddPlayerRequest.class);
        this.handler.addPlayerHandle(request);
    }

    @MessageMapping("/endSession")
    public void endSession(String json) {
        this.handler.endSessionHandle();
    }
}

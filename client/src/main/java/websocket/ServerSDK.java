package websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.Parent;
import gamecontent.DifficultyLevel;
import messages.requests.AddPlayerRequest;
import messages.requests.AmIWinnerRequest;
import messages.requests.DeletePlayerRequest;
import messages.requests.InitRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class ServerSDK {
    private static final String URL = "ws://localhost:8080/dobble";

    private Logger logger = LoggerFactory.getLogger(ServerSDK.class);
    private StompSession session;
    private CustomStompSessionHandler stompSessionHandler;

    // The Parent object is by this class to issue messages to the GUI as a response to the messages from the server.
    public ServerSDK(Parent gameObject) throws ExecutionException, InterruptedException {
        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(new StandardWebSocketClient());
        webSocketStompClient.setMessageConverter(new StringMessageConverter());
        this.stompSessionHandler = new CustomStompSessionHandler(UUID.randomUUID().toString(), gameObject);
        this.session = webSocketStompClient.connect(URL, stompSessionHandler).get();
        this.addPlayer(stompSessionHandler.getClientID(), "player" + stompSessionHandler.getClientID());
    }

    public StompSession getSession() {
        return session;
    }

    public CustomStompSessionHandler getStompSessionHandler() {
        return stompSessionHandler;
    }

    public void askIfWonShout(String clientID, int shoutID) {
        AmIWinnerRequest amIWinnerRequest = new AmIWinnerRequest(clientID, shoutID);
        this.logger.info("Sending amIWinnerRequest {}", amIWinnerRequest);
        try {
            String payload = new ObjectMapper().writeValueAsString(amIWinnerRequest);
            this.session.send("/app/amIWinner", payload);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public void initSessionAsAdmin(String clientID, DifficultyLevel computerDifficulty, int computerPlayersNumber) {
        InitRequest initRequest = new InitRequest(clientID, computerDifficulty, computerPlayersNumber);
        this.logger.info("Sending initSessionRequest {}", initRequest);
        try {
            String payload = new ObjectMapper().writeValueAsString(initRequest);
            this.session.send("/app/init", payload);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public void addPlayer(String clientID, String playerName) {
        AddPlayerRequest addPlayerRequest = new AddPlayerRequest(clientID, playerName);
        this.logger.info("Sending addPlayerRequest {}", addPlayerRequest);
        try {
            String payload = new ObjectMapper().writeValueAsString(addPlayerRequest);
            session.send("/app/addPlayer", payload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void deletePlayer(String clientID, String clientToDeleteID) {
        DeletePlayerRequest request = new DeletePlayerRequest(clientID, clientToDeleteID);
        this.logger.info("Sending deletePlayerRequest {}", request);
        try {
            String payload = new ObjectMapper().writeValueAsString(request);
            session.send("/app/deletePlayer", payload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void endGameSession() {
        String payload = "payload";
        session.send("/app/endSession", payload);
    }
}

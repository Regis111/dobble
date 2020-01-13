package websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import messages.requests.AddPlayerRequest;
import messages.requests.AmIWinnerRequest;
import messages.requests.DeletePlayerRequest;
import messages.requests.InitRequest;
import messages.responses.AmIWinnerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.messaging.support.MessageBuilder;
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

    public ServerSDK() throws ExecutionException, InterruptedException {
        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(new StandardWebSocketClient());
        webSocketStompClient.setMessageConverter(new StringMessageConverter());
        this.stompSessionHandler = new CustomStompSessionHandler(UUID.randomUUID().toString());
        this.session = webSocketStompClient.connect(URL, stompSessionHandler).get();
    }

    public StompSession getSession() {
        return session;
    }

    public void askIfWonShout(String clientID, int shoutID) {
        AmIWinnerRequest amIWinnerRequest = new AmIWinnerRequest(clientID, shoutID);
        this.logger.info("Sending amIWinnerRequest {}", amIWinnerRequest);
        try {
            String request = new ObjectMapper().writeValueAsString(amIWinnerRequest);
            this.session.send("/app/amIWinner", request);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public void initSessionAsAdmin(String clientID, int computerDifficulty, int computerPlayersNumber) {
        InitRequest initRequest = new InitRequest(clientID, computerDifficulty, computerPlayersNumber);
        this.logger.info("Sending initSessionRequest {}", initRequest);
        try {
            String request = new ObjectMapper().writeValueAsString(initRequest);
            this.session.send("/app/init", initRequest);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public void addPlayer(String clientID, String playerName) {
        AddPlayerRequest addPlayerRequest = new AddPlayerRequest(clientID, playerName);
        try {
            String payload = new ObjectMapper().writeValueAsString(addPlayerRequest);
            session.send("/app/addPlayer", payload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void deletePlayer(String clientID, String clientToDeleteID) {
        DeletePlayerRequest request= new DeletePlayerRequest(clientID, clientToDeleteID);
        try {
            String payload = new ObjectMapper().writeValueAsString(request);
            session.send("/app/deletePlayer", payload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            ServerSDK sdk = new ServerSDK();
            Thread.sleep(1000);
            sdk.askIfWonShout(sdk.stompSessionHandler.getClientID(), 1);
            new Scanner(System.in).nextLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

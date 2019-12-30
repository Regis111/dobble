package websocket;

import messages.requests.AmIWinnerRequest;
import messages.requests.Request;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutionException;

public class ServerSDK {
    private static final String URL = "ws://localhost:8080/dobble";

    private StompSession session;

    public ServerSDK() throws ExecutionException, InterruptedException {
        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(new StandardWebSocketClient());
        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSessionHandler stompSessionHandler = new MyStompSessionHandler();
        this.session = webSocketStompClient.connect(URL, stompSessionHandler).get();
    }

    public void askIfWonShout(String clientID, int shoutID) {
        AmIWinnerRequest request = new AmIWinnerRequest(clientID, shoutID);
        this.session.send("app/amIWinner", request);
    }
}

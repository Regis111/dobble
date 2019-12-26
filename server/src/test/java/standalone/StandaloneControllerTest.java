package standalone;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import messages.Request;
import messages.Response;
import messages.ResponseType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.support.SimpAnnotationMethodMessageHandler;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.AbstractMessageChannel;
import org.springframework.messaging.support.AbstractSubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.util.JsonPathExpectationsHelper;
import pl.dobblepolskab.controller.MainController;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.HumanPlayer;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.Player;
import pl.dobblepolskab.services.CommunicationService;
import pl.dobblepolskab.services.GameService;
import pl.dobblepolskab.services.PlayerService;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class StandaloneControllerTest {

    private TestSubscribableChannel clientOutboundSubscribableChannel;

    private TestAnnotationMethodHandler subscribeAnnotationMethodHandler;

    private TestCommunicationService testCommunicationService;

    private TestPlayerService testPlayerService;

    @Before
    public void setup() {

        this.testCommunicationService = new TestCommunicationService();

        MainController mainController = new MainController(
                this.testCommunicationService,
                new TestPlayerService(),
                new TestGameService()
        );

        this.clientOutboundSubscribableChannel = new TestSubscribableChannel();

        this.subscribeAnnotationMethodHandler = new TestAnnotationMethodHandler(
                new TestSubscribableChannel(),
                this.clientOutboundSubscribableChannel,
                new SimpMessagingTemplate(new TestSubscribableChannel())
        );

        this.subscribeAnnotationMethodHandler.registerHandler(mainController);
        this.subscribeAnnotationMethodHandler.setDestinationPrefixes(Arrays.asList("/app"));
        this.subscribeAnnotationMethodHandler.setMessageConverter(new MappingJackson2MessageConverter());
        this.subscribeAnnotationMethodHandler.setApplicationContext(new StaticApplicationContext());
        this.subscribeAnnotationMethodHandler.afterPropertiesSet();
    }

    @Test
    public void helloFromDobble() {
        StompHeaderAccessor headers = StompHeaderAccessor.create(StompCommand.SUBSCRIBE);
        headers.setSubscriptionId("0");
        headers.setDestination("/app/dobblePair");
        headers.setSessionId("0");
        headers.setSessionAttributes(new HashMap<>());
        Message<byte[]> message = MessageBuilder.withPayload(new byte[0]).setHeaders(headers).build();

        this.subscribeAnnotationMethodHandler.handleMessage(message);

        assertEquals(1, this.clientOutboundSubscribableChannel.getMessages().size());
        Message<?> reply = this.clientOutboundSubscribableChannel.getMessages().get(0);

        StompHeaderAccessor replyHeaders = StompHeaderAccessor.wrap(reply);
        assertEquals("0", replyHeaders.getSessionId());
        assertEquals("0", replyHeaders.getSubscriptionId());
        assertEquals("/app/dobblePair", replyHeaders.getDestination());

        String json = new String((byte[]) reply.getPayload(), StandardCharsets.UTF_8);

        Assert.assertTrue(json.contains("Hello from Dobble!"));
    }

    @Test
    public void amIWinner() throws JsonProcessingException {
        Request request = new Request("1", 1);

        byte[] payload = new ObjectMapper().writeValueAsBytes(request);

        StompHeaderAccessor headers = StompHeaderAccessor.create(StompCommand.MESSAGE);
        headers.setDestination("/app/amIWinner");
        headers.setSessionId("0");
        headers.setSessionAttributes(new HashMap<>());
        Message<byte[]> message = MessageBuilder.withPayload(payload).setHeaders(headers).build();

        this.subscribeAnnotationMethodHandler.handleMessage(message);
        for(Message<?> responseMessage : this.testCommunicationService.getMessages()) {
            System.out.println(responseMessage.getPayload() + " ");
        }
        assertEquals(1, this.testCommunicationService.getMessages().size());

        Message<Response> reply = this.testCommunicationService.getMessages().get(0);

        Response response = reply.getPayload();

        Assert.assertEquals(ResponseType.WIN, response.getType());
        Assert.assertEquals("1", response.getClientID());
        Assert.assertEquals(1, response.getShoutID());
    }
}

class TestSubscribableChannel extends AbstractSubscribableChannel {

    private final List<Message<?>> messages = new ArrayList<>();

    List<Message<?>> getMessages() {
        return this.messages;
    }

    @Override
    protected boolean sendInternal(Message<?> message, long timeout) {
        this.messages.add(message);
        return true;
    }

}

class TestAnnotationMethodHandler extends SimpAnnotationMethodMessageHandler {

    TestAnnotationMethodHandler(SubscribableChannel inChannel, MessageChannel outChannel,
                                       SimpMessageSendingOperations brokerTemplate) {

        super(inChannel, outChannel, brokerTemplate);
    }

    void registerHandler(Object handler) {
        super.detectHandlerMethods(handler);
    }
}

class TestGameService implements GameService {

    @Override
    public boolean isWinner(String clientID, int shoutID) {
        return true;
    }
}

class TestPlayerService implements PlayerService {

    private Map<String, HumanPlayer> map = new HashMap<>() {{
        put("1", new HumanPlayer(null,"testPlayer1", "1"));
        put("2", new HumanPlayer(null,"testPlayer2", "2"));
        put("3", new HumanPlayer(null,"testPlayer3", "3"));
    }};

    @Override
    public Map<String, HumanPlayer> getHumanPlayers() {
        return this.map;
    }

    @Override
    public Player getPlayer(String clientID) {
        return this.map.get(clientID);
    }
}

class TestCommunicationService implements CommunicationService {

    private final List<Message<Response>> messages = new ArrayList<>();

    List<Message<Response>> getMessages() {
        return messages;
    }

    @Override
    public void send(String clientID, String path, Response response) {
        if (response.getType() == ResponseType.WIN) {
            messages.add(new Message<Response>() {
                @Override
                public Response getPayload() {
                    return response;
                }

                @Override
                public MessageHeaders getHeaders() {
                    return null;
                }
            });
        }
    }
}
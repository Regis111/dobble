package standalone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.support.SimpAnnotationMethodMessageHandler;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.AbstractSubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;
import pl.dobblepolskab.controller.MainController;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StandaloneControllerTest {

    private MainController mainController;
    private TestMessageChannel clientOutboundChannel;
    private TestAnnotationMethodHandler annotationMethodHandler;

    @Before
    public void setup(){
        this.mainController = new MainController();

        this.clientOutboundChannel = new TestMessageChannel();

        this.annotationMethodHandler = new TestAnnotationMethodHandler(
                new TestMessageChannel(), clientOutboundChannel, new SimpMessagingTemplate(new TestMessageChannel()));

        this.annotationMethodHandler.registerHandler(mainController);
        this.annotationMethodHandler.setDestinationPrefixes(Arrays.asList("/app"));
        this.annotationMethodHandler.setMessageConverter(new MappingJackson2MessageConverter());
        this.annotationMethodHandler.setApplicationContext(new StaticApplicationContext());
        this.annotationMethodHandler.afterPropertiesSet();
    }

    @Test
    public void mainController() {
        StompHeaderAccessor headers = StompHeaderAccessor.create(StompCommand.SUBSCRIBE);
        headers.setSubscriptionId("0");
        headers.setDestination("/app/dobblePair");
        headers.setSessionId("0");
        headers.setSessionAttributes(new HashMap<>());
        Message<byte[]> message = MessageBuilder.withPayload(new byte[0]).setHeaders(headers).build();

        this.annotationMethodHandler.handleMessage(message);

        assertEquals(1, this.clientOutboundChannel.getMessages().size());
        Message<?> reply = this.clientOutboundChannel.getMessages().get(0);

        StompHeaderAccessor replyHeaders = StompHeaderAccessor.wrap(reply);
        assertEquals("0", replyHeaders.getSessionId());
        assertEquals("0", replyHeaders.getSubscriptionId());
        assertEquals("/app/dobblePair", replyHeaders.getDestination());

        String json = new String((byte[]) reply.getPayload(), StandardCharsets.UTF_8);

        Assert.assertTrue(json.contains("Hello from Dobble!"));
    }
}

class TestMessageChannel extends AbstractSubscribableChannel {

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
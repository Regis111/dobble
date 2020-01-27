package pl.dobblepolskab.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import messages.responses.AmIWinnerResponse;
import messages.responses.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CommunicationServiceImpl implements CommunicationService {

    private SimpMessageSendingOperations simpMessagingTemplate;

    private Logger logger = LoggerFactory.getLogger(CommunicationServiceImpl.class);

    @Autowired
    public CommunicationServiceImpl(SimpMessageSendingOperations simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

//    @Override
//    public void sendToUser(String userName, Response response) {
//
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            String jsonTarget = mapper.writeValueAsString(response);
//            this.simpMessagingTemplate.convertAndSendToUser(userName, "/queue/reply", jsonTarget);
//        } catch (Exception e) {
//            e.fillInStackTrace();
//        }
//    }

    @Override
    public void sendOnTopic(String topic, Response response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonTarget = mapper.writeValueAsString(response);
            this.simpMessagingTemplate.convertAndSend(topic, jsonTarget);
            logger.info("Sent message {} on topic {}", response, topic);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }
}

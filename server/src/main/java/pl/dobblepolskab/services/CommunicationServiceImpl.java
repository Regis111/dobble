package pl.dobblepolskab.services;

import messages.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
public class CommunicationServiceImpl implements CommunicationService {

    private SimpMessageSendingOperations simpMessagingTemplate;

    @Autowired
    public CommunicationServiceImpl(SimpMessageSendingOperations simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void send(String clientID, String path, Response response) {
        this.simpMessagingTemplate.convertAndSendToUser(clientID, path, response);
    }
}

package pl.dobblepolskab.services;

import messages.responses.AmIWinnerResponse;
import messages.responses.Response;

public interface CommunicationService {
    //void sendToUser(String clientID, Response response);
    void sendOnTopic(String topic, Response response);
}

package pl.dobblepolskab.services;

import messages.responses.AmIWinnerResponse;

public interface CommunicationService {
    void send(String clientID, String path, AmIWinnerResponse response);
}

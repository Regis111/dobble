package pl.dobblepolskab.services;

import messages.Response;

public interface CommunicationService {
    void send(String clientID, String path, Response response);
}

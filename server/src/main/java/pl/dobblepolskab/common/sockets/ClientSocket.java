package pl.dobblepolskab.common.sockets;

import pl.dobblepolskab.common.sockets.message.Message;

public abstract class ClientSocket {
    private String clientId;
    private String serverId;

    public ClientSocket(String clientId){
        this.clientId = clientId;
    }

    protected abstract void sendToServer(Message message);
    protected abstract Message receiveFromServer();

    public void sendMessageToServer(Message message){
        sendToServer(message);
    }

    public Message receiveMessageFromServer(){
        return receiveFromServer();
    }
}

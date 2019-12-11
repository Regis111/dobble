package pl.dobblepolskab.common.sockets;

import pl.dobblepolskab.common.sockets.message.Message;

import java.util.ArrayList;

public abstract class ServerSocket {
    private String serverId;
    private ArrayList<String> clientsList;

    private void initObject(){
        clientsList = new ArrayList<>();
    }

    public ServerSocket(String serverId){
        initObject();
        this.serverId = serverId;
    }

    protected abstract void sendToClient(String clientId, Message message);
    protected abstract Message receiveFromClient(String clientId);

    public void sendViaUnicast(String client, Message message){
        sendToClient(client, message);
    }
    public void sendViaMulticast(ArrayList<String> clientsList, Message message){
        for(String client : clientsList)
            if(this.clientsList.contains(client))
                sendToClient(client, message);
    }
    public void sendViaBroadcast(Message message){
        for(String client : clientsList)
            sendToClient(client, message);
    }

    public Message getLastMessageFromClient(String client){
        return receiveFromClient(client);
    }
}

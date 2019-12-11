package pl.dobblepolskab.common.sockets;

import pl.dobblepolskab.common.sockets.message.Message;

public class AssociationBasedClientSocket extends ClientSocket{
    private AssociationBasedServerSocket serverSocket;

    public AssociationBasedClientSocket(String clientId, AssociationBasedServerSocket serverSocket){
        super(clientId);
        this.serverSocket = serverSocket;
    }

    @Override
    protected void sendToServer(Message message) {

    }

    @Override
    protected Message receiveFromServer() {
        return null;
    }
}

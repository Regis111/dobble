package pl.dobblepolskab.common.sockets;

import pl.dobblepolskab.common.sockets.message.Message;

public class AssociationBasedServerSocket extends ServerSocket{

    public AssociationBasedServerSocket(String serverId){
        super(serverId);
    }

    @Override
    protected void sendToClient(String clientId, Message message) {

    }

    @Override
    protected Message receiveFromClient(String clientId) {
        return null;
    }
}

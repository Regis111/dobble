package pl.dobblepolskab.model.servergamesession.playersmanager.player.aimodules;

import pl.dobblepolskab.common.sockets.AssociationBasedClientSocket;
import pl.dobblepolskab.common.sockets.AssociationBasedServerSocket;
import pl.dobblepolskab.common.sockets.ClientSocket;
import pl.dobblepolskab.common.sockets.ServerSocket;
import pl.dobblepolskab.model.server.Server;

public abstract class AIModule {
    private String serverIpAddress;
    private int serverPordId;
    private String moduleId;

    public AIModule (String moduleId){
        serverIpAddress = "127.0.0.1";
        serverPordId = Server.getInstance().getServerPortId();
        this.moduleId = moduleId;
    }


}

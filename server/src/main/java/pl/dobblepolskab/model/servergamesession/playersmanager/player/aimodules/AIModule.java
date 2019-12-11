package pl.dobblepolskab.model.servergamesession.playersmanager.player.aimodules;

import pl.dobblepolskab.common.sockets.AssociationBasedClientSocket;
import pl.dobblepolskab.common.sockets.AssociationBasedServerSocket;
import pl.dobblepolskab.common.sockets.ClientSocket;
import pl.dobblepolskab.common.sockets.ServerSocket;

public abstract class AIModule {
    private static String serverIpAddress;
    private static int serverPordId;
    private static AssociationBasedServerSocket serverSocket;
    private String moduleId;
    private ClientSocket clientSocket;

    public static void setUpServerSocketInfo(String ipAddress, int pordId){
        serverIpAddress = ipAddress;
        serverPordId = pordId;
    }

    public static void setUpServerSocketInfo(AssociationBasedServerSocket socket){
        serverSocket = socket;
    }

    private void initObject(){
    }

    public AIModule (String moduleId){
        initObject();
        this.moduleId = moduleId;
        clientSocket = new AssociationBasedClientSocket(moduleId, serverSocket);
    }


}

package pl.dobblepolskab.model.servergamesession.playersmanager.player.aimodules;

import pl.dobblepolskab.common.sockets.ClientSocket;

public abstract class AIModule {
    private static String serverIpAddress;
    private static int serverPordId;
    private String moduleId;
    private ClientSocket clientSocket;

    public static void setUpServerSocketInfo(String ipAddress, int pordId){
        serverIpAddress = ipAddress;
        serverPordId = pordId;
    }

    private void initObject(){
    }

    public AIModule (String moduleId){
        initObject();
        this.moduleId = moduleId;
        clientSocket = new ClientSocket(moduleId, serverIpAddress, serverPordId);
    }


}

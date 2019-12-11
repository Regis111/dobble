package pl.dobblepolskab.model.server;

import pl.dobblepolskab.common.sockets.AssociationBasedServerSocket;
import pl.dobblepolskab.common.sockets.ServerSocket;
import pl.dobblepolskab.model.server.serverconfigurator.ServerConfigurator;
import pl.dobblepolskab.model.servergamesession.ServerGameSession;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.aimodules.AIModule;

public class Server {
    private static final Server instance = new Server();
    private boolean initiated;
    private ServerConfigurator configurator;
    private ServerSocket serverSocket;
    private ServerGameSession serverGameSession;

    private void initObject(){
        initiated = false;
    }

    private Server(){
        initObject();
    }

    public static Server getInstance(){
        return instance;
    }

    public void init(ServerConfigurator configurator){
        if(!configurator.isConfigurationCompleted())
            return;
        this.configurator = configurator;
        serverSocket = new AssociationBasedServerSocket("dobbleServer");
        AIModule.setUpServerSocketInfo((AssociationBasedServerSocket)serverSocket);
        initiated = true;
    }


}

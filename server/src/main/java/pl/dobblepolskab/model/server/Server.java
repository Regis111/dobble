package pl.dobblepolskab.model.server;

import pl.dobblepolskab.common.sockets.AssociationBasedServerSocket;
import pl.dobblepolskab.common.sockets.ServerSocket;
import pl.dobblepolskab.model.server.serverconfigurator.ServerConfigurator;
import pl.dobblepolskab.model.servergamesession.ServerGameSession;

public class Server {
    private ServerConfigurator configurator;
    private ServerSocket serverSocket;
    private ServerGameSession serverGameSession;

    private void initObject(){
    }

    public Server(ServerConfigurator configurator){
        initObject();
        this.configurator = configurator;
        serverSocket = new AssociationBasedServerSocket("dobbleServer");
    }
}

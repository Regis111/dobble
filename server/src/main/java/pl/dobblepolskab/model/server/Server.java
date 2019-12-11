package pl.dobblepolskab.model.server;

import pl.dobblepolskab.common.sockets.ServerSocket;
import pl.dobblepolskab.model.server.serverconfigurator.FileBasedServerConfigurator;
import pl.dobblepolskab.model.server.serverconfigurator.ServerConfigurator;
import pl.dobblepolskab.model.servergamesession.ServerGameSession;

public class Server {
    ServerConfigurator configurator;
    ServerSocket serverSocket;
    ServerGameSession serverGameSession;

    private void initObject(){
    }

    public Server(ServerConfigurator configurator){
        initObject();
        this.configurator = configurator;

    }
}

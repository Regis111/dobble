package pl.dobblepolskab.model.server;

import gamecontent.GameContent;
import gamecontent.GameContentBox;
import pl.dobblepolskab.model.server.serverconfigurator.ServerConfigurator;
import pl.dobblepolskab.model.servergamesession.ServerGameSession;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.aimodules.AIModule;

public class Server {
    private static final Server instance = new Server();
    private boolean initiated;
    private ServerConfigurator configurator;
    private ServerGameSession serverGameSession;
    private GameContent gameContent;

    private Server(){
        initObject();
    }

    private void initObject(){
        initiated = false;
    }

    public static Server getInstance(){
        return instance;
    }

    public void init(ServerConfigurator configurator){
        if(!configurator.isConfigurationCompleted())
            return;
        this.configurator = configurator;
        GameContentBox gameContentBox = new GameContentBox(this.configurator.getGameContentBoxPath());
        gameContent = new GameContent();
        gameContent.importGameContent(gameContentBox);
        serverGameSession = new ServerGameSession(gameContent);
        initiated = true;
    }

    public int getServerPortId(){
        if(!initiated)
            return -1;
        return configurator.getServerPortId();
    }
}

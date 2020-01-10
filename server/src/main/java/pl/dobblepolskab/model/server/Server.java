package pl.dobblepolskab.model.server;

import gamecontent.GameContent;
import gamecontent.GameContentBox;
import pl.dobblepolskab.model.server.serverconfigurator.ServerConfigurator;
import pl.dobblepolskab.model.servergamesession.ServerGameSession;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.aimodules.AIModule;

public class Server {
    private ServerConfigurator configurator;
    private GameContent gameContent;

    public void init(ServerConfigurator configurator){
        if(!configurator.isConfigurationCompleted())
            return;
        this.configurator = configurator;
        GameContentBox gameContentBox = new GameContentBox(this.configurator.getGameContentBoxPath());
        gameContent = new GameContent();
        gameContent.importGameContent(gameContentBox);
    }
}

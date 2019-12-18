package pl.dobblepolskab.model.server;

import pl.dobblepolskab.common.gamecontent.GameContent;
import pl.dobblepolskab.common.gamecontent.GameContentBox;
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
        serverSocket = new AssociationBasedServerSocket("dobbleServer");
        GameContentBox gameContentBox = new GameContentBox(this.configurator.getGameContentBoxPath());
        gameContent = new GameContent();
        gameContent.importGameContent(gameContentBox);
        serverGameSession = new ServerGameSession(gameContent, serverSocket);
        initiated = true;
    }

    public int getServerPortId(){
        if(!initiated)
            return -1;
        return configurator.getServerPortId();
    }

    public boolean setNumberOfPlayer(int value){
        return false;
    }

    public boolean setComputerPlayersDifficultyLevel(int value){
        return false;
    }

    public boolean addPlayerToGame(String playerName, String playerClientId){
        return true;
    }

    public boolean deletePlayerFromGame(String playerName){
        return false;
    }

    public boolean startGameSession(){
        return serverGameSession.startGameSession();
    }

    public boolean startNextShout(){
        return serverGameSession.startNextShout();
    }

    public int getCurrentShoutId(){
        return serverGameSession.getShoutId();
    }

    public boolean isWinner(String playerClientId, int requestShoutId){
        return serverGameSession.isWinner(playerClientId, requestShoutId);
    }

    public int[] getNextTurnState(String playerClientId){
        return serverGameSession.getNextTopCardsForPlayer(playerClientId);
    }

    public boolean endGameSession(){
        return serverGameSession.endGameSession();
    }


}

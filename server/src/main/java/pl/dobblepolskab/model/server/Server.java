package pl.dobblepolskab.model.server;

import com.sun.tools.javac.util.Pair;
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
        return true;
    }

    public boolean startNextShout(){
        return true;
    }

    public int getCurrentShoutId(){
        return 1;
    }

    public boolean isWinner(String playerClientId, int requestShoutId){
        return true;
    }

    public Pair<Integer, Integer> getNextTurnState(String playerClientId){
        return new Pair<>(0, 0);
    }

    public boolean endGameSession(){
        return false;
    }


}

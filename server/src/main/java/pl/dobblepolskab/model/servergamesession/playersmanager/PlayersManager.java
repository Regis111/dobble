package pl.dobblepolskab.model.servergamesession.playersmanager;

import pl.dobblepolskab.common.gamecontent.GameCard;
import pl.dobblepolskab.common.gamecontent.GameContent;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.ComputerPlayer;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.HumanPlayer;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PlayersManager {
    private HashMap<String, Player> playersList;
    private int computerPlayersNum;
    private GameContent gameContent;
    public static final int maxPlayersNumber = 8;

    public PlayersManager(GameContent gameContent){
        initObject();
        this.gameContent = gameContent;
    }

    private void initObject(){
        playersList = new HashMap<>();
        computerPlayersNum = 0;
    }

    public boolean checkIfPlayerExists(String name, String clientId){
        Player foundPlayer = playersList.get(clientId);
        return (!(foundPlayer == null || !foundPlayer.getName().equals(name)));
    }

    public boolean addHumanPlayer(String name, String clientId){
        if(playersList.size() == maxPlayersNumber || checkIfPlayerExists(name, clientId))
            return false;
        HumanPlayer newHumanPlayer = new HumanPlayer(gameContent, name, clientId);
        playersList.put(clientId, newHumanPlayer);
        return true;
    }

    public boolean addComputerPlayer(){
        if(playersList.size() == maxPlayersNumber)
            return false;
        String name = "Computer " + (computerPlayersNum+1);
        ComputerPlayer newComputerPlayer = new ComputerPlayer(gameContent, name, name, 1);
        playersList.put(name, newComputerPlayer);
        computerPlayersNum++;
        return true;
    }

    public void preparePlayersToGame(LinkedList<GameCard> cardsToGiveOut){
        playersList.forEach((playerClientId, player) -> {
            GameCard firstCard = cardsToGiveOut.pop();
            player.preparePlayerToGame(firstCard);
        });
    }

    public GameCard getTopCardOfPlayer(String playerClientId){
        return playersList.get(playerClientId).getTopCard();
    }

    public void pushGameCardOnPlayerStack(String playerClientId, GameCard gameCard){
        playersList.get(playerClientId).pushCardOnStack(gameCard);
    }

}

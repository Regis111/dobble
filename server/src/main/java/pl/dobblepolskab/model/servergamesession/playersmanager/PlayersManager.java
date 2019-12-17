package pl.dobblepolskab.model.servergamesession.playersmanager;

import pl.dobblepolskab.common.gamecontent.GameCard;
import pl.dobblepolskab.common.gamecontent.GameContent;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.ComputerPlayer;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.HumanPlayer;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlayersManager {
    private List<Player> playersList;
    private int computerPlayersNum;
    private GameContent gameContent;
    public static final int maxPlayersNumber = 8;

    public PlayersManager(GameContent gameContent){
        initObject();
        this.gameContent = gameContent;
    }

    private void initObject(){
        playersList = new ArrayList<>();
        computerPlayersNum = 0;
    }

    public boolean checkIfPlayerExists(String name, String clientId){
        for(Player player : playersList){
            if(player.getName().equals(name))
                return true;
            if(player.getClientId().equals(clientId))
                return true;
        }
        return false;
    }

    public boolean addHumanPlayer(String name, String clientId){
        if(playersList.size() == maxPlayersNumber || checkIfPlayerExists(name, clientId))
            return false;
        HumanPlayer newHumanPlayer = new HumanPlayer(gameContent, name, clientId);
        playersList.add(newHumanPlayer);
        return true;
    }

    public boolean addComputerPlayer(){
        if(playersList.size() == maxPlayersNumber)
            return false;
        String name = "Computer " + (computerPlayersNum+1);
        ComputerPlayer newComputerPlayer = new ComputerPlayer(gameContent, name, name, 1);
        playersList.add(newComputerPlayer);
        computerPlayersNum++;
        return true;
    }

    public void preparePlayersToGame(LinkedList<GameCard> cardsToGiveOut){
        for(Player currentPlayer : playersList){
            GameCard firstCard = cardsToGiveOut.pop();
            currentPlayer.preparePlayerToGame(firstCard);
        }
    }

}

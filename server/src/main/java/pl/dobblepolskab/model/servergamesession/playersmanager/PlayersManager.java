package pl.dobblepolskab.model.servergamesession.playersmanager;

import pl.dobblepolskab.common.gamecontent.GameCard;
import pl.dobblepolskab.common.gamecontent.GameContent;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.ComputerPlayer;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.HumanPlayer;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.Player;

import java.util.ArrayList;
import java.util.LinkedList;

public class PlayersManager {
    private LinkedList<Player> playersList;
    private int computerPlayersNum;

    private void initObject(){
        playersList = new LinkedList<>();
        computerPlayersNum = 0;
    }

    public PlayersManager(){
        initObject();
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
        if(playersList.size() == 8 || checkIfPlayerExists(name, clientId))
            return false;
        HumanPlayer newHumanPlayer = new HumanPlayer(name, clientId);
        playersList.add(newHumanPlayer);
        return true;
    }

    public boolean addComputerPlayer(){
        if(playersList.size() == 8)
            return false;
        String name = "Computer " + (computerPlayersNum+1);
        ComputerPlayer newComputerPlayer = new ComputerPlayer(name, name, 1);
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

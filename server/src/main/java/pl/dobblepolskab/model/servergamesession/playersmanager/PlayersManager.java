package pl.dobblepolskab.model.servergamesession.playersmanager;

import pl.dobblepolskab.common.gamecontent.GameCard;
import pl.dobblepolskab.common.gamecontent.GameContent;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.HumanPlayer;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.Player;

import java.util.LinkedList;

public class PlayersManager {
    LinkedList<Player> playersList;

    private void initObject(){
        playersList = new LinkedList<>();
    }

    PlayersManager(){
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

    public boolean addHumanPlayer(String name, String clientId, GameCard firstCard){
        if(playersList.size() == 8 || checkIfPlayerExists(name, clientId))
            return false;
        HumanPlayer newHumanPlayer;
        return true;
    }

    public boolean addComputerPlayer(GameCard firstCard){
        return true;
    }


}

package pl.dobblepolskab.model.servergamesession.playersmanager;

import gamecontent.DifficultyLevel;
import gamecontent.GameCard;
import gamecontent.GameContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.ComputerPlayer;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.HumanPlayer;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.Player;
import pl.dobblepolskab.services.AdminPlayerService;
import pl.dobblepolskab.services.PlayerService;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Scope(value = "singleton")
public class PlayersManager implements PlayerService, AdminPlayerService {
    private HashMap<String, Player> playersList;
    private int computerPlayersNum;
    private GameContent gameContent;
    private DifficultyLevel computerIntelligenceLevel;
    public static final int maxPlayersNumber = 8;

    @Autowired
    public PlayersManager(){
        initObject();
        this.gameContent = GameContent.getInstance();
    }

    private void initObject(){
        playersList = new HashMap<>();
        computerIntelligenceLevel = DifficultyLevel.Easy;
        computerPlayersNum = 0;
    }

    public boolean checkIfPlayerExists(String clientId){
        Optional<Player> foundPlayer = getPlayer(clientId);
        return foundPlayer.isPresent();
    }

    public boolean addHumanPlayer(String name, String clientId){
        if(playersList.size() == maxPlayersNumber || checkIfPlayerExists(clientId))
            return false;
        HumanPlayer newHumanPlayer = new HumanPlayer(name, clientId);
        playersList.put(clientId, newHumanPlayer);
        return true;
    }

    public boolean addComputerPlayer(){
        if(playersList.size() == maxPlayersNumber)
            return false;
        String name = "Computer " + (computerPlayersNum+1);
        ComputerPlayer newComputerPlayer = new ComputerPlayer(name, name, computerIntelligenceLevel);
        playersList.put(name, newComputerPlayer);
        computerPlayersNum++;
        return true;
    }

    public boolean deleteHumanPlayer(String playerClientId){
        Optional<Player> foundPlayer = getPlayer(playerClientId);
        if(foundPlayer.isEmpty())
            return false;
        playersList.remove(playerClientId);
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

    public void setComputerPlayersNumber(int computerPlayersNum) {
        if(computerPlayersNum < 0 || computerPlayersNum > 7)
            return;
        this.computerPlayersNum = computerPlayersNum;
    }

    public void setComputerIntelligenceLevel(DifficultyLevel computerIntelligenceLevel) {
        this.computerIntelligenceLevel = computerIntelligenceLevel;
    }

    @Override
    public Map<String, HumanPlayer> getHumanPlayers() {
        return playersList
                .entrySet().stream()
                .filter(playerEntry -> playerEntry.getValue() instanceof HumanPlayer)
                .collect(Collectors.toMap(Map.Entry::getKey, playerEntry -> (HumanPlayer)playerEntry.getValue()));
    }

    @Override
    public Optional<Player> getPlayer(String clientId) {
        Player foundPlayer = playersList.get(clientId);
        if(foundPlayer != null)
            return Optional.of(foundPlayer);
        else
            return Optional.empty();
    }

    @Override
    public Optional<Player> addPlayerToGame(String playerName, String playerClientID) {
        if(addHumanPlayer(playerName, playerClientID))
            return getPlayer(playerClientID);
        return Optional.empty();
    }

    @Override
    public Optional<Player> deletePlayerFromGame(String playerClientID) {
        Optional<Player> foundPlayer = getPlayer(playerClientID);
        if(foundPlayer.isPresent())
            deleteHumanPlayer(playerClientID);
        return foundPlayer;
    }
}

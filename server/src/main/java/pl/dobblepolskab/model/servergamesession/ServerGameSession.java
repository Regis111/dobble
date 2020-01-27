package pl.dobblepolskab.model.servergamesession;

import gamecontent.DifficultyLevel;
import gamecontent.GameCard;
import gamecontent.GameContent;
import messages.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.dobblepolskab.model.servergamesession.gamecardsstack.GameMainStack;
import pl.dobblepolskab.model.servergamesession.playersmanager.PlayersManager;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.Player;
import pl.dobblepolskab.services.AdminPlayerService;
import pl.dobblepolskab.services.GameService;
import pl.dobblepolskab.services.SessionConfigurationService;

import java.util.Collections;
import java.util.LinkedList;

@Component
@Scope(value = "singleton")
public class ServerGameSession implements GameService, SessionConfigurationService {
    private GameContent gameContent;
    private GameMainStack mainStack;
    private PlayersManager playersManager;
    private boolean sessionRunning;
    private int shoutId;
    private int computerPlayersNum;

    @Autowired
    public ServerGameSession(GameMainStack gameMainStack,
                             PlayersManager playersManager){
        this.gameContent = GameContent.getInstance();
        mainStack = gameMainStack;
        this.playersManager = playersManager;
        sessionRunning = false;
        shoutId = 0;
        computerPlayersNum = 0;
    }

    @Override
    public void startGameSession(){
        if(sessionRunning)
            return;
        for(int i = 0; i < computerPlayersNum; i++)
            playersManager.addComputerPlayer();
        LinkedList<GameCard> cardsToGiveOut = new LinkedList<>(gameContent.getCards());
        Collections.shuffle(cardsToGiveOut);
        playersManager.preparePlayersToGame(cardsToGiveOut);
        mainStack.initMainStack(cardsToGiveOut);
        shoutId = 1;
        sessionRunning = true;
    }

    public void endGameSession(){
        if(!sessionRunning)
            return;
        playersManager.reset();
        sessionRunning = false;
        shoutId = 0;
        computerPlayersNum = 0;
    }

    @Override
    public Pair getNextTurnState(String playerClientId){
        return new Pair(gameContent.getCardIdInModel(mainStack.getTopCard()),
                gameContent.getCardIdInModel(playersManager.getTopCardOfPlayer(playerClientId)));
    }

    public int getShoutId() {
        return shoutId;
    }

    @Override
    public boolean isWinner(String playerClientId, int requestShoutId){
        if(requestShoutId != shoutId)
            return false;
        playersManager.pushGameCardOnPlayerStack(playerClientId, mainStack.popCard());
        shoutId++;
        return true;
    }

    @Override
    public void setComputerPlayersNumber(int playersNumber) {
        if(playersNumber < 0 || playersNumber > 7)
            return;
        computerPlayersNum = playersNumber;
    }

    @Override
    public void setComputerDifficulty(DifficultyLevel computerDifficulty) {
        playersManager.setComputerIntelligenceLevel(computerDifficulty);
    }
}

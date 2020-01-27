package pl.dobblepolskab.controller;

import messages.Pair;
import messages.requests.AddPlayerRequest;
import messages.requests.DeletePlayerRequest;
import messages.requests.InitRequest;
import messages.responses.AmIWinnerResponse;
import messages.responses.InitResponse;
import messages.responses.ResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.HumanPlayer;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.Player;
import pl.dobblepolskab.services.*;

import java.util.Map;
import java.util.Optional;

@Service
public class Handler {

    private Logger logger = LoggerFactory.getLogger(Handler.class);

    private final CommunicationService communicationService;

    private final PlayerService playerService;

    private final AdminPlayerService adminPlayerService;

    private final GameService gameService;

    private final SessionConfigurationService configurationService;

    @Autowired
    public Handler(CommunicationService communicationService,
                          PlayerService playerService,
                          GameService gameService,
                          AdminPlayerService adminPlayerService,
                          SessionConfigurationService sessionConfigurationService) {
        this.communicationService = communicationService;
        this.playerService = playerService;
        this.gameService = gameService;
        this.adminPlayerService = adminPlayerService;
        this.configurationService = sessionConfigurationService;
    }

    void amIWinnerHandle(AmIWinnerResponse request) {
        String clientID = request.getClientID();
        int shoutID = request.getShoutID();

        logger.info("In amIWinner method as clientID = {} on shoutID = {}", clientID, shoutID);

        if (!this.gameService.isWinner(clientID, shoutID)) {
            logger.info("Client: {} didn't win shout {}", clientID, shoutID);
            return;
        }

        logger.info("Client {} won shout {}", clientID, shoutID);

        Optional<Player> player = this.playerService.getPlayer(clientID);

        Map<String, HumanPlayer> humanPlayers = this.playerService.getHumanPlayers();

        logger.info("humanPlayers: {}", humanPlayers.toString());

        humanPlayers.remove(request.getClientID());
        humanPlayers.values().forEach(humanPlayer -> {
            Pair pair = this.gameService.getNextTurnState(humanPlayer.getClientId());
            AmIWinnerResponse loss = new AmIWinnerResponse(humanPlayer.getClientId(), shoutID, ResponseType.LOST, pair.getFirst());
            this.communicationService.sendOnTopic("/topic/nextTurnReply-" + humanPlayer.getClientId(), loss);
            logger.info("Sending next turn response to clientID = {} with cards: [ {} , {} ]",
                    humanPlayer.getClientId(),
                    pair.getFirst(),
                    pair.getSecond()
            );
        });

        Pair pair = this.gameService.getNextTurnState(clientID);
        if (player.isPresent()) {
            AmIWinnerResponse win1 = new AmIWinnerResponse(player.get().getClientId(), shoutID, ResponseType.WIN, pair.getFirst());
            this.communicationService.sendOnTopic("/topic/nextTurnReply-" + player.get().getClientId(), win1);
        } else {
            logger.error("Player who won with ID = {} not in Server", clientID);
        }
    }

    void initHandle(InitRequest request) {
        this.configurationService.setComputerPlayersNumber(request.getComputerPlayersNumber());
        logger.info("Number of computer players set to {}", request.getComputerPlayersNumber());
        this.configurationService.setComputerDifficulty(request.getComputerDifficulty());
        logger.info("Computer difficulty set to {}", request.getComputerDifficulty());
        this.configurationService.startGameSession();
        logger.info("Starting game session");

        Map<String, HumanPlayer> humanPlayers = this.playerService.getHumanPlayers();

        humanPlayers.values().forEach(humanPlayer -> {
            Pair pair = this.gameService.getNextTurnState(humanPlayer.getClientId());
            InitResponse loss = new InitResponse(
                    humanPlayer.getClientId(),
                    1,
                    pair.getFirst(),
                    pair.getSecond(),
                    request.getGameTimeInMinutes()
            );
            this.communicationService.sendOnTopic("/topic/initReply-" + humanPlayer.getClientId(), loss);
            logger.info("Sending init response to clientID = {} with cards: [ {} , {} ]; gameTime = {}",
                    humanPlayer.getClientId(),
                    pair.getFirst(),
                    pair.getSecond(),
                    request.getGameTimeInMinutes()
            );
        });
    }

    void deletePlayerHandle(DeletePlayerRequest request) {
        this.adminPlayerService.deletePlayerFromGame(request.getClientID());
        logger.info("Deleting human player, clientID = {} ", request.getClientID());
    }

    void addPlayerHandle(AddPlayerRequest request) {
        this.adminPlayerService.addPlayerToGame(request.getPlayerToAddName(), request.getClientID());
        logger.info("Adding human player, name = {}, clientID = {} ",
                request.getPlayerToAddName(), request.getClientID());
    }

    void endSessionHandle() {
        this.configurationService.endGameSession();
        logger.info("Ending game session");
    }
}

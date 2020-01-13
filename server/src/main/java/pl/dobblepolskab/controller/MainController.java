package pl.dobblepolskab.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import messages.Pair;
import messages.requests.*;
import messages.responses.AmIWinnerResponse;
import messages.responses.InitResponse;
import messages.responses.ResponseType;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.HumanPlayer;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.Player;
import pl.dobblepolskab.services.*;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Controller
public class MainController {

    private Logger logger = LoggerFactory.getLogger(MainController.class);

    private final CommunicationService communicationService;

    private final PlayerService playerService;

    private final AdminPlayerService adminPlayerService;

    private final GameService gameService;

    private final SessionConfigurationService configurationService;

    @Autowired
    public MainController(CommunicationService communicationService,
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

    @SubscribeMapping("/dobblePair")
    public String helloFromDobble() {
        String[] winOrLose = {"WIN", "LOSE"};
        logger.info("Hello From DobbleServer");
        Random r = new Random();
        int ind = r.nextInt(winOrLose.length);
        return "Hello from Dobble! You " + winOrLose[ind];
    }
    /*
     * Send to winner he won and everyone else they lost
     */
    @MessageMapping("/amIWinner")
    public void amIWinner(StompHeaderAccessor headers, String json) {
        logger.info("In amIWinner method");

        AmIWinnerResponse request = null;
        try {
            request = new ObjectMapper().readValue(json, AmIWinnerResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String clientID = request.getClientID();
        int shoutID = request.getShoutID();

        logger.info("clientID {} shoutID {}", clientID, shoutID);

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

    @MessageMapping("/init")
    public void init(String json) {

        InitRequest request = null;
        try {
            request = new ObjectMapper().readValue(json, InitRequest.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

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
                    humanPlayer.getClientId(), 1, pair.getFirst(), pair.getSecond());
            this.communicationService.sendOnTopic("/topic/initReply-" + humanPlayer.getClientId(), loss);
            logger.info("Sending init response to clientID = {} with cards: [ {} , {} ]",
                    humanPlayer.getClientId(),
                    pair.getFirst(),
                    pair.getSecond()
            );
        });
    }

    @MessageMapping("/deletePlayer")
    public void deletePlayer(String json) {
        DeletePlayerRequest request = null;
        try {
            request = new ObjectMapper().readValue(json, DeletePlayerRequest.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        this.adminPlayerService.deletePlayerFromGame(request.getClientID());
        logger.info("Deleting human player, clientID = {} ", request.getClientID());
    }

    @MessageMapping("/addPlayer")
    public void addPlayer(String json) {
        AddPlayerRequest request = null;
        try {
            request = new ObjectMapper().readValue(json, AddPlayerRequest.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        this.adminPlayerService.addPlayerToGame(request.getPlayerToAddName(), request.getClientID());
        logger.info("Adding human player, name = {}, clientID = {} ", request.getPlayerToAddName(), request.getClientID());
    }

    @MessageMapping("/endSession")
    public void endSession(String json) {
        this.configurationService.endGameSession();
        logger.info("Ending game session");
    }

}
